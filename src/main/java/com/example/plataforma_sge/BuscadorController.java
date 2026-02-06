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
import javafx.scene.control.TextField;
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
    @FXML
    Button editar;

    public void abrirDocumentos(ProyectoOB p) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("documentos.fxml"));
        Parent root= loader.load();

        DocumentosController form = loader.getController();
        form.setProyecto(p);

        form.setid_proyecto(p.getCodigo());

        Stage stage= (Stage) editar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void doubleClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            ProyectoOB sel= (ProyectoOB) tableProyectos.getSelectionModel().getSelectedItem();
            abrirDocumentos(sel);
        }
    }

    public void buscar(){
        String texto = tfBuscador.getText().trim();

        if (texto.isEmpty()){
            SQL.sacar_proyectos("SELECT * FROM proyectos");
        } else {
            SQL.sacar_proyectos("SELECT * FROM proyectos WHERE nombre LIKE '%" + texto + "%' OR key_words LIKE '%" + texto + "%'");
        }

        ObservableList<ProyectoOB> datos = FXCollections.observableArrayList();
        if (SQL.listaProyectos != null) {
            datos.setAll(SQL.listaProyectos);
        }
        tableProyectos.setItems(datos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableProyectos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        code.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        name.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        type.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        state.setCellValueFactory(new PropertyValueFactory<>("estado"));
        boss.setCellValueFactory(new PropertyValueFactory<>("jefeId"));
    }
}
