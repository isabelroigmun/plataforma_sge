package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FormController {
    @FXML
    Label txtPrincipal;

    @FXML
    TextField tfCodigo; //no se puede editar
    @FXML
    TextField tfNombre;
    @FXML
    DatePicker dpCreacion;
    @FXML
    DatePicker dpEjecucion;
    @FXML
    ComboBox<String> cbTipoProyecto; //ENUM("IT","I+D","I+D+i","I+D+IT")
    @FXML
    ComboBox<String> cbEstado; //activo o inactivo
    @FXML
    ComboBox<String> cbCalificacion; //ENUM("IT","I+D","I+D+i","I+D+IT")
    @FXML
    ComboBox<Boolean> cbBajadaCalificacion; //true o false
    @FXML
    ComboBox<Boolean> cbCooperacion; //true o false
    @FXML
    TextField tfFases;
    @FXML
    TextField tfJefe; //a desarrollar más adelante, un choice/combo entre la lista de usuarios que hay

    ProyectoOB proyecto;

    @FXML
    public void initialize() {
        //he añadido esto
        if (proyecto==null){
            txtPrincipal.setText("Crear proyecto");
        } else {
            txtPrincipal.setText("Editar proyecto");
        }
        //el resto lo he dejado como estaba
        cbTipoProyecto.getItems().addAll("IT", "I+D", "I+D+i", "I+D+IT");
        cbCalificacion.getItems().addAll("IT", "I+D", "I+D+i", "I+D+IT");

        cbEstado.getItems().addAll("activo", "inactivo");

        cbBajadaCalificacion.getItems().addAll(true, false);
        cbCooperacion.getItems().addAll(true, false);
    }

    public void setProyecto(ProyectoOB proyecto) {
        this.proyecto=proyecto;
        if (proyecto != null) {
            tfCodigo.setText(String.valueOf(proyecto.getCodigo()));
            tfCodigo.setDisable(true);
            tfNombre.setText(proyecto.getNombre());
            dpCreacion.setValue(proyecto.getFechaCreacion().toLocalDate());
            dpCreacion.setDisable(true);
            dpEjecucion.setValue(proyecto.getFechaEjInicio());
            cbTipoProyecto.setValue(proyecto.getTipo());
            cbEstado.setValue(proyecto.getEstado());
            cbCalificacion.setValue(proyecto.getCalificacion());
            cbBajadaCalificacion.setValue(proyecto.isBajadaCalificacion());
            cbCooperacion.setValue(proyecto.isEnCooperacion());
            tfFases.setText(String.valueOf(proyecto.getFases()));
            tfJefe.setText(String.valueOf(proyecto.getJefeId()));
        } else {
            tfCodigo.setText("");
            tfCodigo.setDisable(true);
            tfNombre.setText("");
            dpCreacion.setValue(LocalDate.now());
            dpCreacion.setDisable(true);
            dpEjecucion.setValue(LocalDate.now());
            cbTipoProyecto.setValue(null);
            cbEstado.setValue(null);
            cbCalificacion.setValue(null);
            cbBajadaCalificacion.setValue(false);
            cbCooperacion.setValue(false);
            tfFases.setText("");
            tfJefe.setText("");
        }
    }

    @FXML
    public void guardar() {
        try {
            //tiene que hacer comprobación de no ser nulo algún dato
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

            if (cbTipoProyecto.getValue()==null || cbEstado.getValue()==null || cbCalificacion.getValue()==null || cbBajadaCalificacion.getValue()==null || cbCooperacion.getValue()==null){
                mostrarAlerta("Selecciona valores en todos los combobox");
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
            String tipo = cbTipoProyecto.getValue();
            String calificacion = cbCalificacion.getValue();
            int activo;
            if (cbEstado.getValue().equals("activo")) {
                activo = 1;
            } else {
                activo = 0;
            }
            boolean bajadaCalificacion = cbBajadaCalificacion.getValue();
            boolean enCooperacion = cbCooperacion.getValue();

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
