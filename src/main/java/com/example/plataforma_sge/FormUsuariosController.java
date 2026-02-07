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

    //inicializa cargando los nombres de los roles y añadiendo datos a los combobox
    @FXML
    public void initialize() {
        cargarRoles();
        cbEstado.getItems().addAll("activo", "inactivo");
    }

    //Recibe un objeto UsuarioOB y configura el formulario según si se va a crear un usuario nuevo o editar uno existente.
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

    //funcionalidad botón guardar
    @FXML
    public void guardar() {
        //comprueba que no hayan campos vacíos
        if (tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfUsuario.getText().isEmpty() || tfContra.getText().isEmpty() || cbRol.getValue() == null) {
            mostrarAlerta("Rellena todos los campos");
            return;
        }

        //guarda los datos
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
            // INSERT en el SQL
            String sql = "INSERT INTO usuarios (nombre, apellidos, usuario, contraseña, rol_id) VALUES (" +
                    "'" + nombre + "', " +
                    "'" + apellidos + "', " +
                    "'" + usuarioTxt + "', " +
                    "'" + contra + "', " +
                    rolId + ")";
            AuditoriaOB.pasarAuditoriaAMongo("Crear usuario");
            SQL.vacio(sql);
        } else {
            // UPDATE en el SQL
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

    //funcionalidad botón cancelar
    @FXML
    public void cancelar() {
        cerrar();
    }

    //método que no guarda los datos solo cierra la ventana
    private void cerrar() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

    //carga los nombres de los roles de combobox del rol
    public void cargarRoles() {
        cbRol.getItems().clear();
        cbRol.getItems().addAll(SQL.getRoles());
    }

    //para mostrar alerta con el mensaje seleccionado
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje);
        alert.showAndWait();
    }
}
