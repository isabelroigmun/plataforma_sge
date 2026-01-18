package com.example.plataforma_sge;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label estado;

    @FXML
    private TextField user;

    @FXML
    private TextField password;

    @FXML
    protected void signin() throws IOException {

        String final_user = user.getText().toString();
        String final_password = password.getText().toString();

        SQL.consulta("SELECT usuario,contraseña from usuarios where usuario='"+final_user+"' ");

        if (final_user.equals(SQL.usuario) && final_password.equals(SQL.pass)){
            estado.setText("Relacion correcta, bienvenido");
            cambiarEscena();
        }else{
            estado.setText("Relación incorrecta, inténtelo de nuevo.");
        }

    }

    private void cambiarEscena() throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("proyectos.fxml"));
        Parent root= loader.load();

    }
}