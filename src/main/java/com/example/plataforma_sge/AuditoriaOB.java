package com.example.plataforma_sge;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class AuditoriaOB {

    private final String mongoid;
    private final String accion;
    private final Integer id_usuario;
    private final LocalDateTime fecha;

    public AuditoriaOB(String mongoid,String accion, Integer id_usuario,LocalDateTime fecha) {


        this.mongoid=mongoid;
        this.accion=accion;
        this.id_usuario=id_usuario;
        this.fecha=fecha;


    }

    public String getMongoid() {
        return mongoid;
    }

    public String getAccion() {
        return accion;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
