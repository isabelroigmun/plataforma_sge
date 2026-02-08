package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;


import java.awt.*;
import java.io.IOException;

//Clase controladora de la interfaz gráfica del menú


public class MenuController {

    // Se inicializan todos los elementos FXML de la interfaz

    @FXML
    ToolBar TolBar;


    //Se configura el cambio de interfaz a la de auditoria con la condición de que
    //el usuario tenga el rol de admin, si no, saldrá una alerta de error.

    public void change_auditoria() throws IOException {

        SQL.comprobar_permisos("SELECT rol_id from usuarios where usuario= '"+SQL.usuario+"' ");

        int rol= SQL.id_rol;
        System.out.println(rol);

        if (rol==1){
            System.out.println("accedo");
            FXMLLoader loader= new FXMLLoader(getClass().getResource("auditoria.fxml"));
            Parent root= loader.load();

            Stage stage= (Stage) TolBar.getScene().getWindow();
            stage.setScene(new Scene(root));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permisos insuficientes");
            alert.setHeaderText("Permisos insuficientes");
            alert.setContentText("Contacte con su superior. ");

            alert.showAndWait();
        }
    }

    //Se configura el cambio de interfaz a la de proyectos

    public void change_proyectos() throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("proyectos.fxml"));
        Parent root= loader.load();

        Stage stage= (Stage) TolBar.getScene().getWindow();
        stage.setScene(new Scene(root));

    }


    //Se configura el cambio de interfaz a la de buscador

    public void change_buscador() throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("buscador.fxml"));
        Parent root= loader.load();

        Stage stage= (Stage) TolBar.getScene().getWindow();
        stage.setScene(new Scene(root));

    }


    //Se configura el cambio de interfaz a la de usuarios con la condición de que
    //el usuario tenga el rol de admin, si no, saldrá una alerta de error.

    public void change_usuarios() throws IOException {
        SQL.comprobar_permisos("SELECT rol_id from usuarios where usuario= '"+SQL.usuario+"' ");

        int rol= SQL.id_rol;
        System.out.println(rol);

        if (rol==1){
            System.out.println("accedo");
            FXMLLoader loader= new FXMLLoader(getClass().getResource("usuarios.fxml"));
            Parent root= loader.load();

            Stage stage= (Stage) TolBar.getScene().getWindow();
            stage.setScene(new Scene(root));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Permisos insuficientes");
            alert.setHeaderText("Permisos insuficientes");
            alert.setContentText("Contacte con su superior. ");

            alert.showAndWait();
        }
    }

    public void change_inicio() throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("inicio.fxml"));
        Parent root= loader.load();

        Stage stage= (Stage) TolBar.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
