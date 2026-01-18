package com.example.plataforma_sge;

import java.time.LocalDate;
import java.util.Date;

public class ProyectoOB {

    private int codigo;
    private String nombre;
    private LocalDate fechaCreacion;
    private LocalDate fechaEjecucion;
    private String palabrasClave;
    private String tipo;
    private String estado;
    private String calificacion;
    private boolean bajadaCalificacion;
    private boolean enCooperacion;
    private int fases;
    private int jefeId;

    public ProyectoOB(int codigo, String nombre, LocalDate fechaCreacion, LocalDate fechaEjecucion, String palabrasClave, String tipo, String estado, String calificacion, boolean bajadaCalificacion, boolean enCooperacion, int fases, int jefeId) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaEjecucion = fechaEjecucion;
        this.palabrasClave = palabrasClave;
        this.tipo = tipo;
        this.estado = estado;
        this.calificacion = calificacion;
        this.bajadaCalificacion = bajadaCalificacion;
        this.enCooperacion = enCooperacion;
        this.fases = fases;
        this.jefeId = jefeId;
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
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
}
