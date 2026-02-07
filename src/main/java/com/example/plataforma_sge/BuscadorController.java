package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BuscadorController implements Initializable {

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
    TextField tfBuscador;

    //este método detecta si se hace doble click en un elemento de la tabla y abre la ventana de documentos
    public void doubleClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            ProyectoOB sel= (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();
            abrirDocumentos(sel);
        }
    }

    //abre documentos.fxml y le pasa el proyecto seleccionado para que sea capaz de cargar los datos, también se cambia la escena
    public void abrirDocumentos(ProyectoOB p) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("documentos.fxml"));
        Parent root= loader.load();

        DocumentosController form = loader.getController();
        form.setProyecto(p);

        form.setid_proyecto(p.getCodigo());

        Stage stage= (Stage) tableProyectos.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    //este método obtiene el texto que hay en el textfield del buscador y, si está vacío muestra una alerta y sino busca por palabras clave, después actualilza la lista con los elementos que cumplan la condición
    public void buscar(){
        String texto = tfBuscador.getText().trim();

        if (texto.isEmpty()){
            mostrarAlerta("Introduce algo en el campo de búsqueda");
            return;
        } else {
            SQL.sacar_proyectos("SELECT * FROM proyectos WHERE key_words LIKE '%" + texto + "%'");
        }

        ObservableList<ProyectoOB> datos = FXCollections.observableArrayList();
        if (SQL.listaProyectos != null) {
            datos.setAll(SQL.listaProyectos);
        }
        tableProyectos.setItems(datos);
    }

    //inicializa la tabla dándole el tamaño total de la ventana
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableProyectos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        code.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        name.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        type.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        state.setCellValueFactory(new PropertyValueFactory<>("estado"));
        boss.setCellValueFactory(new PropertyValueFactory<>("jefeId"));
    }

    //método para mostrar alerta
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }
}
