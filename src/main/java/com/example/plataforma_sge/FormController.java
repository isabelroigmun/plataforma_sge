package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        this.proyecto=proyecto;
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
            //tiene que hacer comprobación de no ser nulo algún dato
            //voy a comprobar solo si el nombre es vacío pero para tener la estructura y luego poder añadir más con un or
            if (tfNombre.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Rellena los campos");
                alert.showAndWait();
                return;
            }

            if (dpEjecucion.getValue()==null){
                mostrarAlerta("Selecciona una fecha de ejecución");
                return;
            }

            if (tfFases.getText().isEmpty() || tfJefe.getText().isEmpty()) {
                mostrarAlerta("Fases y Jefe debe ser un número entero");
                return;
            }

            if (!tfBajadaCalificacion.getText().toLowerCase().trim().equals("true")||!tfBajadaCalificacion.getText().toLowerCase().trim().equals("false")){
                mostrarAlerta("Bajada de calificación debe ser true o false");
                return;
            }

            ArrayList<String> tipos = new ArrayList<>();
            tipos.add("IT");
            tipos.add("I+D");
            tipos.add("I+D+i");
            tipos.add("I+D+IT");
            if (!tipos.contains(tfTipoProyecto.getText())){
                mostrarAlerta("Tipo no válido");
                return;
            }

            if (!tipos.contains(tfCalificacion.getText())){
                mostrarAlerta("Calificación no válida");
                return;
            }

            if (!tfEstado.getText().equals("activo") && !tfEstado.getText().equals("inactivo")){
                mostrarAlerta("Estado debe ser activo o inactivo");
                return;
            }

            if (dpEjecucion.getValue().isBefore(dpCreacion.getValue())){
                mostrarAlerta("La fecha de ejecución debe ser posterior a la fecha de creación");
                return;
            }

            //aquí guardará los datos en variables para luego insertarlos/actualizarlos en el SQL
            String nombre = tfNombre.getText();
            LocalDateTime fechaCreacion = LocalDateTime.now();
            LocalDate fechaEjInicio = dpEjecucion.getValue();
            LocalDate fechaEjFinal = null;
            String tipo = tfTipoProyecto.getText();
            int activo;
            if (tfEstado.getText().equalsIgnoreCase("activo")) {
                activo = 1;
            } else {
                activo = 0;
            }
            String calificacion = tfCalificacion.getText();
            boolean bajadaCalificacion = Boolean.parseBoolean(tfBajadaCalificacion.getText());
            boolean enCooperacion = Boolean.parseBoolean(tfCooperacion.getText());
            int fases = Integer.parseInt(tfFases.getText());
            int jefeId = Integer.parseInt(tfJefe.getText());

            if (proyecto == null) {
                //INSERT en el SQL
                String sql = "INSERT INTO proyectos " +
                        "(nombre, fecha_creacion, fecha_ej_inicio, fecha_ej_final, tipo_proyecto, activo, calificacion, bajada_calificacion, en_cooperacion, fases, usuarios_id) VALUES (" +
                        "'" + nombre + "', " +
                        "'" + fechaCreacion + "', " +
                        "'" + fechaEjInicio + "', " +
                        "NULL, "+
                        "'" + tipo + "', " +
                        activo + ", " +
                        "'" + calificacion + "', " +
                        bajadaCalificacion + ", " +
                        enCooperacion + ", " +
                        fases + ", " +
                        jefeId + ")";

                SQL.vacio(sql);
            } else {
                //UPDATE en el SQL
                int codigo = proyecto.getCodigo();

                String sql = "UPDATE proyectos SET " +
                        "nombre='" + nombre + "', " +
                        "fecha_ej_inicio='" + fechaEjInicio + "', " +
                        "fecha_ej_final=" + fechaEjFinal + ", " +
                        "tipo_proyecto='" + tipo + "', " +
                        "activo=" + activo + ", " +
                        "calificacion='" + calificacion + "', " +
                        "bajada_calificacion=" + bajadaCalificacion + ", " +
                        "en_cooperacion=" + enCooperacion + ", " +
                        "fases=" + fases + ", " +
                        "usuarios_id=" + jefeId + " " +
                        "WHERE id=" + codigo;

                SQL.vacio(sql);
            }
            //cerrar ventana
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Has introducido un tipo de dato incorrecto: " + e.getMessage());
            alert.showAndWait();
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

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }
}
