package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class FormProyectosController {
    @FXML
    Label txtPrincipal;
    @FXML
    TextField tfCodigo; //no se puede editar
    @FXML
    TextField tfNombre;
    @FXML
    DatePicker dpEjInicio;
    @FXML
    DatePicker dpEjFinal;
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
    ComboBox<UsuarioOB> cbJefe; //un choice/combo entre la lista de usuarios que hay
    @FXML
    TextField tfPalabrasClave;

    ProyectoOB proyecto;

    //inicializa cargando datos en los combobox
    @FXML
    public void initialize() {
        cbTipoProyecto.getItems().addAll("IT", "I+D", "I+D+i", "I+D+IT");
        cbCalificacion.getItems().addAll("IT", "I+D", "I+D+i", "I+D+IT");
        cbEstado.getItems().addAll("activo", "inactivo");
        cbBajadaCalificacion.getItems().addAll(true, false);
        cbCooperacion.getItems().addAll(true, false);

        cargarUsuarios();
    }

    //comprueba si el proyecto no es nulo, entonces se cargan los datos (editar proyecto) y sino se dejan los campos en blanco (crear proyecto)
    public void setProyecto(ProyectoOB proyecto) {
        this.proyecto=proyecto;
        if (proyecto != null) {
            txtPrincipal.setText("Editar proyecto");

            tfCodigo.setText(String.valueOf(proyecto.getCodigo()));
            tfCodigo.setDisable(true);
            tfNombre.setText(proyecto.getNombre());
            dpEjInicio.setValue(proyecto.getFechaEjInicio());
            dpEjFinal.setValue(proyecto.getFechaEjFinal());
            cbTipoProyecto.setValue(proyecto.getTipo());
            cbEstado.setValue(proyecto.getEstado());
            cbCalificacion.setValue(proyecto.getCalificacion());
            cbBajadaCalificacion.setValue(proyecto.isBajadaCalificacion());
            cbCooperacion.setValue(proyecto.isEnCooperacion());
            tfFases.setText(String.valueOf(proyecto.getFases()));
            for (UsuarioOB u : cbJefe.getItems()) {
                if (u.getId() == proyecto.getJefeId()) {
                    cbJefe.setValue(u);
                    break;
                }
            }
            tfPalabrasClave.setText(proyecto.getPalabrasClave());
        } else {
            txtPrincipal.setText("Crear proyecto");

            tfCodigo.setText("");
            tfCodigo.setDisable(true);
            tfNombre.setText("");
            dpEjInicio.setValue(LocalDate.now());
            dpEjFinal.setValue(LocalDate.now());
            cbTipoProyecto.setValue(null);
            cbEstado.setValue(null);
            cbCalificacion.setValue(null);
            cbBajadaCalificacion.setValue(false);
            cbCooperacion.setValue(false);
            tfFases.setText("");
            cbJefe.setValue(null);
            tfPalabrasClave.setText("");
        }
    }

    //funcionalidad del botón guardar
    @FXML
    public void guardar() {
        try {
            //tiene que hacer comprobación de no ser nulo algún dato
            if (tfNombre.getText().isEmpty() || tfPalabrasClave.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Rellena los campos");
                alert.showAndWait();
                return;
            }

            if (dpEjFinal.getValue()==null){
                mostrarAlerta("Selecciona una fecha de ejecución");
                return;
            }

            if (tfFases.getText().isEmpty()) {
                mostrarAlerta("Fases debe ser un número entero");
                return;
            }

            UsuarioOB jefeSeleccionado = cbJefe.getValue();
            if (jefeSeleccionado == null) {
                mostrarAlerta("Selecciona un jefe de proyecto");
                return;
            }

            if (cbTipoProyecto.getValue()==null || cbEstado.getValue()==null || cbCalificacion.getValue()==null || cbBajadaCalificacion.getValue()==null || cbCooperacion.getValue()==null){
                mostrarAlerta("Selecciona valores en todos los combobox");
                return;
            }

            if (comprobarFechaValida(dpEjInicio.getValue(), dpEjFinal.getValue())){
                mostrarAlerta("La fecha de ejecución inicio debe ser anterior a la fecha de ejecución final");
                return;
            }

            //aquí guardará los datos en variables para luego insertarlos/actualizarlos en el SQL
            String nombre = tfNombre.getText();
            LocalDateTime fechaCreacion;
            if (proyecto == null) {
                fechaCreacion = LocalDateTime.now();
            } else {
                fechaCreacion = proyecto.getFechaCreacion();
            }
            LocalDate fechaEjInicio = dpEjInicio.getValue();
            LocalDate fechaEjFinal = dpEjFinal.getValue();
            String tipo = cbTipoProyecto.getValue();
            String calificacion = cbCalificacion.getValue();
            int activo = convertirEstado(cbEstado.getValue());
            boolean bajadaCalificacion = cbBajadaCalificacion.getValue();
            boolean enCooperacion = cbCooperacion.getValue();
            int fases = Integer.parseInt(tfFases.getText());
            int jefeId = jefeSeleccionado.getId();
            String palabrasClave = tfPalabrasClave.getText();

            if (proyecto == null) {
                //INSERT en el SQL
                String sql = "INSERT INTO proyectos " +
                        "(nombre, fecha_creacion, fecha_ej_inicio, fecha_ej_final, tipo_proyecto, activo, calificacion, bajada_calificacion, en_cooperacion, fases, usuarios_id, key_words) VALUES (" +
                        "'" + nombre + "', " +
                        "'" + fechaCreacion + "', " +
                        "'" + fechaEjInicio + "', " +
                        "'" + fechaEjFinal + "', " +
                        "'" + tipo + "', " +
                        activo + ", " +
                        "'" + calificacion + "', " +
                        bajadaCalificacion + ", " +
                        enCooperacion + ", " +
                        fases + ", " +
                        jefeId + ", " +
                        "'" + palabrasClave + "'" + ")";
                AuditoriaOB.pasarAuditoriaAMongo("Crear proyecto");
                SQL.vacio(sql);
            } else {
                //UPDATE en el SQL
                int codigo = proyecto.getCodigo();String sql = "UPDATE proyectos SET " +
                        "nombre='" + nombre + "', " +
                        "fecha_ej_inicio='" + fechaEjInicio + "', " +
                        "fecha_ej_final='" + fechaEjFinal + "', " +
                        "tipo_proyecto='" + tipo + "', " +
                        "activo=" + activo + ", " +
                        "calificacion='" + calificacion + "', " +
                        "bajada_calificacion=" + bajadaCalificacion + ", " +
                        "en_cooperacion=" + enCooperacion + ", " +
                        "fases=" + fases + ", " +
                        "usuarios_id=" + jefeId + ", " +
                        "key_words='" + palabrasClave + "' " +
                        "WHERE id=" + codigo;
                AuditoriaOB.pasarAuditoriaAMongo("Editar proyecto");
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



    //funcionalidad para el botón de cancelar
    @FXML
    public void cancelar() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

    //muestra alerta con el mensaje que se introduce
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }

    //carga el nombre de los usuarios que hay en la base de datos para el combobox de cefe
    public void cargarUsuarios() {
        cbJefe.getItems().clear();
        cbJefe.getItems().addAll(SQL.getUsuarios());
    }

    //sirve para convertir el combo de estado de activo/inactivo a un entero para su inserción posterior en la bdd
    public int convertirEstado(String estado){
        if (estado.equalsIgnoreCase("activo")){
            return 1;
        }else {
            return 0;
        }
    }

    //comprueba que la fecha de ejecucion inicio es anterior a la de ejecución final
    public boolean comprobarFechaValida(LocalDate inicio, LocalDate fin) {
        return fin.isBefore(inicio);
    }
}
