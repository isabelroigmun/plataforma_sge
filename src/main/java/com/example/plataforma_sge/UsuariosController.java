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

public class UsuariosController implements Initializable {
    //tabla y columnas
    @FXML
    TableView<UsuarioOB> tableUsuarios;
    @FXML
    TableColumn<UsuarioOB, String> nombre;
    @FXML
    TableColumn<UsuarioOB, String> apellidos;
    @FXML
    TableColumn<UsuarioOB, String> usuario;
    @FXML
    TableColumn<UsuarioOB, Integer> rol;

    //botones
    @FXML
    Button editar;
    @FXML
    Button borrar;
    @FXML
    Button crear;

    //carga los usuarios desde la base de datos y los muestra en la tabla
    public void tabla(){
        SQL.sacar_usuarios("SELECT * FROM USUARIOS");
        final ObservableList<UsuarioOB> datos = FXCollections.observableArrayList();
        if (SQL.listaUsuarios != null) {
            datos.setAll(SQL.listaUsuarios);
        }

        nombre.setCellValueFactory(new PropertyValueFactory<UsuarioOB, String>("nombre"));
        apellidos.setCellValueFactory(new PropertyValueFactory<UsuarioOB, String>("apellidos"));
        usuario.setCellValueFactory(new PropertyValueFactory<UsuarioOB, String>("usuario"));
        //rol.setCellValueFactory(new PropertyValueFactory<UsuarioOB, Integer>("rol")); //falta modificar para que no salgan números
        rol.setCellValueFactory(new PropertyValueFactory<>("nombreRol")); //arreglado con esto + método en UsuarioOB

        tableUsuarios.setItems(datos);
    }

    //método para borrar el usuario seleccionado en la tabla
    public void borrar(){
        UsuarioOB sel= (UsuarioOB) tableUsuarios.getSelectionModel().getSelectedItem();

        int idSel= sel.getId();
        SQL.vacio("DELETE FROM usuarios where id= "+idSel);
        tabla();
        AuditoriaOB.pasarAuditoriaAMongo("Borrar usuario");
    }

    //abre el form para crear un nuevo usuario
    public void crear(){
        abrirFormulario(null);
        tabla();
    }

    //abre el form para editar un usuario existente, le pasa sus datos
    public void editar(){
        UsuarioOB seleccionado = (UsuarioOB) tableUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado==null){
            return;
        }
        abrirFormulario(seleccionado);
        tabla();
    }

    //abre el formulario de creación/edición de usuarios en una ventana modal
    public void abrirFormulario(UsuarioOB u){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form-usuario.fxml"));
            Parent root = loader.load();

            FormUsuariosController form = loader.getController();
            form.setUsuario(u);

            Stage stage = new Stage();
            if (u == null) {
                stage.setTitle("Nuevo Usuario");
            } else {
                stage.setTitle("Editar Usuario");
            }
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //inicializa ajustando el tamaño de las ventanas y cargando la tabla con los datos iniciales
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//columnas del tamaño de la ventana
        tabla();
    }
}
