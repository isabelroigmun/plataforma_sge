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



    public void tabla(){
        SQL.sacar_proyectos("SELECT * FROM PROYECTOS");
        final ObservableList<ProyectoOB> datos = FXCollections.observableArrayList(); //poder sacar los movimientos ordenados
        if (SQL.lista != null) {
            datos.setAll(SQL.lista);
        }

        code.setCellValueFactory(new PropertyValueFactory<ProyectoOB, Integer>("codigo"));
        name.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("nombre"));
        type.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("tipo"));
        state.setCellValueFactory(new PropertyValueFactory<ProyectoOB, String>("estado"));
        boss.setCellValueFactory(new PropertyValueFactory<ProyectoOB, Integer>("jefeId"));
        tableProyectos.setItems(datos);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form-proyecto.fxml"));
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
