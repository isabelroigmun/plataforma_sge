package com.example.plataforma_sge;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormUsuariosController {

    @FXML
    Label txtPrincipal;

    @FXML
    TextField tfNombre;
    @FXML
    TextField tfApellidos;
    @FXML
    TextField tfUsuario;
    @FXML
    TextField tfContra;
    @FXML
    ComboBox<RolOB> cbRol;
    @FXML
    ComboBox<String> cbEstado;

    private UsuarioOB usuario;

    @FXML
    public void initialize() {
        cargarRoles();
        cbEstado.getItems().addAll("activo", "inactivo");
    }

    public void setUsuario(UsuarioOB usuario) {
        this.usuario = usuario;

        if (usuario != null) {
            txtPrincipal.setText("Editar usuario");

            tfNombre.setText(usuario.getNombre());
            tfApellidos.setText(usuario.getApellidos());
            tfUsuario.setText(usuario.getUsuario());
            tfUsuario.setDisable(true);
            tfContra.setText(usuario.getContra());
            for (RolOB r : cbRol.getItems()) {
                if (r.getId() == usuario.getRol()) {
                    cbRol.setValue(r);
                    break;
                }
            }
            cbEstado.setValue("activo");
        } else {
            txtPrincipal.setText("Crear usuario");
            tfNombre.clear();
            tfApellidos.clear();
            tfUsuario.clear();
            tfContra.clear();
            cbRol.setValue(null);
            cbEstado.setValue(null);
        }
    }

    @FXML
    public void guardar() {
        if (tfNombre.getText().isEmpty() ||
                tfApellidos.getText().isEmpty() ||
                tfUsuario.getText().isEmpty() ||
                tfContra.getText().isEmpty() ||
                cbRol.getValue() == null) {

            mostrarAlerta("Rellena todos los campos");
            return;
        }

        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String usuarioTxt = tfUsuario.getText();
        String contra = tfContra.getText();
        RolOB rolSeleccionado = cbRol.getValue();
        if (rolSeleccionado == null) {
            mostrarAlerta("Selecciona un rol");
            return;
        }

        int rolId = rolSeleccionado.getId();


        if (usuario == null) {
            // INSERT
            String sql = "INSERT INTO usuarios (nombre, apellidos, usuario, contraseña, rol_id) VALUES (" +
                    "'" + nombre + "', " +
                    "'" + apellidos + "', " +
                    "'" + usuarioTxt + "', " +
                    "'" + contra + "', " +
                    rolId + ")";
            AuditoriaOB.pasarAuditoriaAMongo("Crear usuario");
            SQL.vacio(sql);
        } else {
            // UPDATE
            String sql = "UPDATE usuarios SET " +
                    "nombre='" + nombre + "', " +
                    "apellidos='" + apellidos + "', " +
                    "contraseña='" + contra + "', " +
                    "rol_id=" + rolId + " " +
                    "WHERE id=" + usuario.getId();
            AuditoriaOB.pasarAuditoriaAMongo("Editar usuario");
            SQL.vacio(sql);
        }

        cerrar();
    }

    @FXML
    public void cancelar() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

    public void cargarRoles() {
        cbRol.getItems().clear();
        cbRol.getItems().addAll(SQL.getRoles());
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }
}
