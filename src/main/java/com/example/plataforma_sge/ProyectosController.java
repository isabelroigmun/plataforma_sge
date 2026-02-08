package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


//Clase controladora de la interfaz gráfica de Proyectos


public class ProyectosController implements Initializable {

    // Se inicializan todos los elementos FXML de la interfaz

    @FXML
    TableView<ProyectoOB> tableProyectos;
    @FXML
    TableColumn<ProyectoOB, Integer> code;
    @FXML
    TableColumn<ProyectoOB, String> name;
    @FXML
    TableColumn<ProyectoOB, String> type;
    @FXML
    TableColumn<ProyectoOB, String> state;
    @FXML
    TableColumn<ProyectoOB, Integer> boss;
    @FXML
    Button editar;
    @FXML
    Button borrar;
    @FXML
    Button crear;


    //Configuración de las columnas de la tabla y sus datos, recorriendo toda la tabla
    // y guardando los objetos en una lista, para finalmente, visualizarlos estos datos en la tabla.
    public void tabla(){
        SQL.sacar_proyectos("SELECT * FROM PROYECTOS");
        final ObservableList<ProyectoOB> datos = FXCollections.observableArrayList(); //poder sacar los movimientos ordenados
        if (SQL.listaProyectos != null) {
            datos.setAll(SQL.listaProyectos);
        }

        code.setCellValueFactory(new PropertyValueFactory<ProyectoOB, Integer>("codigo"));
        name.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("nombre"));
        type.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("tipo"));
        state.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("estado"));
        boss.setCellValueFactory(new PropertyValueFactory<ProyectoOB, Integer>("jefeId"));
        tableProyectos.setItems(datos);

    }


    //Se configura el cambio de interfaz a la de documentos

    public void abrirDocumentos(ProyectoOB p) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("documentos.fxml"));
        Parent root= loader.load();

        DocumentosController form = loader.getController();
        form.setProyecto(p);

        form.setid_proyecto(p.getCodigo());

        Stage stage= (Stage) editar.getScene().getWindow();
        stage.setScene(new Scene(root));


    }

    //Se comprueba que el usuario tenga permisos para ejecutar la acción,
    //en este caso, eliminar proyectos
    public void borrar(){
        ProyectoOB sel= (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();

        if (sel==null){
            mostrarAlerta("Selecciona un proyecto para poder borrarlo");
            return;
        }

        int idSel= sel.getCodigo();
        SQL.vacio("DELETE FROM proyectos where id= "+idSel);
        tabla();
        AuditoriaOB.pasarAuditoriaAMongo("Borrar proyecto");

    }

    //Se comprueba que el usuario tenga permisos para ejecutar la acción,
    //en este caso, crear proyectos, abriendo así un formulario para ello.
    //Por último, vuelve a ejecutar el método tabla para actualizarla.

    public void crear(){
        abrirFormulario(null);
        tabla();
    }


//Se comprueba que el usuario tenga permisos para ejecutar la acción,
    //en este caso, editar proyectos, abriendo así un formulario para ello,
    //con la información de este ya aplicada a cada uno de los apartados del formulario.
    //Por último, vuelve a ejecutar el método tabla para actualizarla.

    public void editar(){
        ProyectoOB seleccionado = (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();
        if (seleccionado==null){
            mostrarAlerta("Selecciona un proyecto para poder editarlo");
            return;
        }
        abrirFormulario(seleccionado);
        tabla();
    }

    //Se configura y se abre una nueva interfaz, en este caso el formulario.

    public void abrirFormulario(ProyectoOB p){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form-proyecto.fxml"));
            Parent root = loader.load();

            FormProyectosController form = loader.getController();
            form.setProyecto(p);

            Stage stage = new Stage();
            if (p == null) {
                stage.setTitle("Nuevo Proyecto");
            } else {
                stage.setTitle("Editar Proyecto");
            }
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //nada más se abra la interfaz gráfica, la tabla ocupará el espacio completo y se
        //ejecutará el método tabla.

        tableProyectos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//columnas del tamaño de la ventana
        tabla();
    }

    //Si se detecta un doble click en uno de los proyectos de la tabla, se manda ese proyecto a abrir el
    //apartado de documentos.
    public void doubleClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            ProyectoOB sel= (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();
            abrirDocumentos(sel);
        }
    }

    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }
}
