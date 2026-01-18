package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProyectosController implements Initializable {


    @FXML
    TableView tableProyectos;
    @FXML
    TableColumn code;
    @FXML
    TableColumn name;
    @FXML
    TableColumn type;
    @FXML
    TableColumn state;
    @FXML
    TableColumn boss;

    @FXML
    Button editar;
    @FXML
    Button borrar;
    @FXML
    Button crear;



    public void tabla(){
        SQL.sacar_proyectos("SELECT * FROM PROYECTOS");
        final ObservableList<ProyectoOB> datos = FXCollections.observableArrayList(); //poder sacar los movimientos ordenados
        tableProyectos.getColumns().clear();

        datos.setAll(SQL.lista);

        code = new TableColumn("Código");
        code.setMinWidth(100);
        code.setCellValueFactory( new PropertyValueFactory<ProyectoOB, String>("codigo"));

        name = new TableColumn("Nombre");
        name.setMinWidth(100);
        name.setCellValueFactory( new PropertyValueFactory<ProyectoOB, String>("nombre"));

        type = new TableColumn("Tipo");
        type.setMinWidth(100);
        type.setCellValueFactory( new PropertyValueFactory<ProyectoOB, String>("tipo"));

        state = new TableColumn("Estado");
        state.setMinWidth(100);
        state.setCellValueFactory( new PropertyValueFactory<ProyectoOB, String>("estado"));

        boss = new TableColumn("Jefe de Proyecto");
        boss.setMinWidth(100);
        boss.setCellValueFactory( new PropertyValueFactory<ProyectoOB, String>("jefe"));

        tableProyectos.setItems(datos);
        tableProyectos.getColumns().addAll(code,name,type,state,boss);
    }

    public void borrar(){
        ProyectoOB sel= (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();

        int idSel= sel.getCodigo();
        SQL.vacio("DELETE FROM proyectos where id= "+idSel);
        tabla();
    }

    public void crear(){
        abrirFormulario(null);
        tabla();
    }

    public void editar(){
        ProyectoOB seleccionado = (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();
        if (seleccionado==null){
            return;
        }
        abrirFormulario(seleccionado);
        tabla();
    }

    public void abrirFormulario(ProyectoOB p){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("formProyecto.fxml"));
            Parent root = loader.load();

            FormController form = loader.getController();
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
        tabla();
    }
}
