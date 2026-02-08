package com.example.plataforma_sge;


//Objeto que representa los documentos que se suben en cada proyecto,
//con su id de proyecto, nombre del archivo, y fecha de subida


public class DocumentoOB {

    private int id;
    private String nombre;
    private String fecha;

    public DocumentoOB(int id, String nombre, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
