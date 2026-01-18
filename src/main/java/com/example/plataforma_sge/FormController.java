package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FormController {
    @FXML
    TextField tfCodigo; //no se puede editar
    @FXML
    TextField tfNombre;
    @FXML
    DatePicker dpCreacion;
    @FXML
    DatePicker dpEjecucion;
    @FXML
    TextField tfTipoProyecto; //ENUM("IT","I+D","I+D+i","I+D+IT")
    @FXML
    TextField tfEstado; //activo o inactivo igual choice
    @FXML
    TextField tfCalificacion; //ENUM("IT","I+D","I+D+i","I+D+IT")
    @FXML
    TextField tfBajadaCalificacion;
    @FXML
    TextField tfCooperacion;
    @FXML
    TextField tfFases;
    @FXML
    TextField tfJefe; //choice entre los usuarios

    ProyectoOB proyecto;

    public void setProyecto(ProyectoOB proyecto) {
        if (proyecto != null) {
            tfCodigo.setText(String.valueOf(proyecto.getCodigo()));
            tfCodigo.setDisable(true);
            tfNombre.setText(proyecto.getNombre());
            dpCreacion.setValue(proyecto.getFechaCreacion().toLocalDate());
            dpCreacion.setDisable(true);
            dpEjecucion.setValue(proyecto.getFechaEjInicio());
            tfTipoProyecto.setText(proyecto.getTipo());
            tfEstado.setText(proyecto.getEstado());
            tfCalificacion.setText(proyecto.getCalificacion());
            if (proyecto.isBajadaCalificacion()){
                tfBajadaCalificacion.setText("true");
            }else {
                tfBajadaCalificacion.setText("false");
            }
            if (proyecto.isEnCooperacion()){
                tfCooperacion.setText("true");
            }else {
                tfCooperacion.setText("false");
            }
            tfFases.setText(String.valueOf(proyecto.getFases()));
            tfJefe.setText(String.valueOf(proyecto.getJefeId()));
        } else {
            tfCodigo.setText("");
            tfCodigo.setDisable(true);
            tfNombre.setText("");
            dpCreacion.setValue(LocalDate.now());
            dpCreacion.setDisable(true);
            dpEjecucion.setValue(LocalDate.now());
            tfTipoProyecto.setText("");
            tfEstado.setText("");
            tfCalificacion.setText("");
            tfBajadaCalificacion.setText("false");
            tfCooperacion.setText("false");
            tfFases.setText("");
            tfJefe.setText("");
        }
    }

    @FXML
    public void guardar() {
        try {
            //aquí guardará los datos en variables
            //también tiene que hacer comprobación de no ser nulo algún dato

            if (proyecto == null) {
                //INSERT en el SQL
            } else {
                //UPDATE en el SQL
            }
            //cerrar ventana
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar proyecto: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}
