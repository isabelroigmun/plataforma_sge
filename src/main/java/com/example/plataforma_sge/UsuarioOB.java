package com.example.plataforma_sge;

public class UsuarioOB {
    private int id;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String contra;
    private int rol;

    public UsuarioOB(int id, String nombre, String apellidos, String usuario, String contra, int rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contra = contra;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return getUsuario(); //esto sirve para que en el formulario de crear/editar proyecto se muestre el usuario en el combobox
    }

    public String getNombreRol() {
        //esto sirve para que en la tabla de usuarios aparezca el nombre del rol en vez del número
        switch (rol) {
            case 1:
                return "Administrador";
            case 2:
                return "Lectura";
            case 3:
                return "Escritura";
            default:
                return "Desconocido";
        }
    }

}
