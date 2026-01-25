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

public class MenuController {

    @FXML
    ToolBar TolBar;

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
            alert.setContentText("Losentimos, no tiene los permisos suficientes para acceder a este contenido, contacte con su jefe. ");

            alert.showAndWait();
        }



    }

    public void change_proyectos() throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("proyectos.fxml"));
        Parent root= loader.load();

        Stage stage= (Stage) TolBar.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

}
