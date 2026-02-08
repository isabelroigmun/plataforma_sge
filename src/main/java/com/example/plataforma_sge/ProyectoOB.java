package com.example.plataforma_sge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


//Objeto que representa los proyectos creados por los usuarios,
//con sus correspondientes atributos para después poder ser visualizado


public class ProyectoOB {

    private int codigo;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaEjInicio;
    private LocalDate fechaEjFinal;
    private String tipo; //ENUM("IT","I+D","I+D+i","I+D+IT")
    private String estado; //activo o inactivo
    private String calificacion; //ENUM("IT","I+D","I+D+i","I+D+IT")
    private boolean bajadaCalificacion; //falso por defecto
    private boolean enCooperacion;
    private int fases;
    private int jefeId; //PROVISIONAL (?)
    private String palabrasClave;

    public ProyectoOB(int codigo, String nombre, LocalDateTime fechaCreacion, LocalDate fechaEjInicio, LocalDate fechaEjFinal, String tipo, String estado, String calificacion, boolean bajadaCalificacion, boolean enCooperacion, int fases, int jefeId, String palabrasClave) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaEjInicio = fechaEjInicio;
        this.fechaEjFinal = fechaEjFinal;
        this.tipo = tipo;
        this.estado = estado;
        this.calificacion = calificacion;
        this.bajadaCalificacion = bajadaCalificacion;
        this.enCooperacion = enCooperacion;
        this.fases = fases;
        this.jefeId = jefeId;
        this.palabrasClave = palabrasClave;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEjInicio() {
        return fechaEjInicio;
    }

    public void setFechaEjInicio(LocalDate fechaEjInicio) {
        this.fechaEjInicio = fechaEjInicio;
    }

    public LocalDate getFechaEjFinal() {
        return fechaEjFinal;
    }

    public void setFechaEjFinal(LocalDate fechaEjFinal) {
        this.fechaEjFinal = fechaEjFinal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isBajadaCalificacion() {
        return bajadaCalificacion;
    }

    public void setBajadaCalificacion(boolean bajadaCalificacion) {
        this.bajadaCalificacion = bajadaCalificacion;
    }

    public boolean isEnCooperacion() {
        return enCooperacion;
    }

    public void setEnCooperacion(boolean enCooperacion) {
        this.enCooperacion = enCooperacion;
    }

    public int getFases() {
        return fases;
    }

    public void setFases(int fases) {
        this.fases = fases;
    }

    public int getJefeId() {
        return jefeId;
    }

    public void setJefeId(int jefeId) {
        this.jefeId = jefeId;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }
}
