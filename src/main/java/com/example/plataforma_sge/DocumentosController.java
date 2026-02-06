package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class DocumentosController implements Initializable {

    @FXML
    Label id;
    @FXML
    Label nombre;
    @FXML
    Label fecha_creacion;
    @FXML
    Label fecha_ej_inicio;
    @FXML
    Label fecha_ej_final;
    @FXML
    Label tipo_proyecto;
    @FXML
    Label activo;
    @FXML
    Label calificacion;
    @FXML
    Label bajada_calificacion;
    @FXML
    Label en_cooperacion;
    @FXML
    Label fases;
    @FXML
    Label usuarios_id;
    @FXML
    ListView documentos;

    ProyectoOB proyecto;

    private int id_proyecto;
    private PDFServicio servicio= new PDFServicio();
    private File selectedFile;

    public void setid_proyecto(int id_proyecto){
        this.id_proyecto=id_proyecto;
        cargarPDF();
    }

    private void cargarPDF() {
        documentos.getItems().setAll(servicio.doc_proyecto(id_proyecto));
    }

    public void doubleClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            String sel= (String) documentos.getSelectionModel().getSelectedItem();
            servicio.abrirVentanaDesdeMongo(sel);
        }
    }



    public void updateDocumentos(){
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona el archivo a enviar");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("ARCHIVOS PDF", "pdf");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo_seleccionado = selector.getSelectedFile();
            servicio.addpdf(id_proyecto,archivo_seleccionado);
            cargarPDF();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setProyecto(ProyectoOB proyecto) {
        this.proyecto = proyecto;
        if (proyecto != null) {
            id.setText("ID: "+String.valueOf(proyecto.getCodigo()));
            nombre.setText("NOMBRE: "+proyecto.getNombre());
            fecha_creacion.setText("FECHA DE CREACIÓN: "+proyecto.getFechaCreacion().toLocalDate().toString());
            fecha_ej_inicio.setText("FECHA DE INICIO: "+proyecto.getFechaEjInicio().toString());
            tipo_proyecto.setText("TIPO DE PROYECTO: "+proyecto.getTipo());
            activo.setText("¿ESTA ACTIVO?: "+proyecto.getEstado());
            calificacion.setText("CALIFICACIÓN: "+proyecto.getCalificacion());
            bajada_calificacion.setText("BAJADA DE CALIFICACIÓN: "+String.valueOf(proyecto.isBajadaCalificacion()));
            en_cooperacion.setText("¿EN COOPERACIÓN?: "+String.valueOf(proyecto.isEnCooperacion()));
            fases.setText("FASES: "+String.valueOf(proyecto.getFases()));
            usuarios_id.setText("JEFE DE PROYECTO: "+String.valueOf(proyecto.getJefeId()));
        }
    }

    public void volver() throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("proyectos.fxml"));
        Parent root= loader.load();

        Stage stage= (Stage) id.getScene().getWindow();
        stage.setScene(new Scene(root));
    }




}
