package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class InicioController {
    @FXML
    private Label txtUsuario;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String,Integer> barChart;

    @FXML
    public void initialize() {
        SQL.sacar_proyectos("SELECT * FROM proyectos");
        cargarUsuario();
        cargarPieChart();
        cargarBarChart();
    }

    public void cargarUsuario() {
        txtUsuario.setText("Hola " + SQL.usuario);
    }

    public void cargarPieChart() {
        pieChart.getData().clear();
        pieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Activos", obtenerProyectosActivos()),
                new PieChart.Data("Inactivos", obtenerProyectosInactivos())
        ));
    }

    public void cargarBarChart() {
        barChart.getData().clear();
        XYChart.Series<String, Integer> serie = new XYChart.Series<>();
        serie.setName("Proyectos por tipo");
        serie.getData().add(new XYChart.Data<>("IT", contarTipo("IT")));
        serie.getData().add(new XYChart.Data<>("I+D", contarTipo("I+D")));
        serie.getData().add(new XYChart.Data<>("I+D+i", contarTipo("I+D+i")));
        serie.getData().add(new XYChart.Data<>("I+D+IT", contarTipo("I+D+IT")));

        barChart.getData().add(serie);
    }

    public int obtenerProyectosActivos(){
        int activos =0;
        for (ProyectoOB p : SQL.listaProyectos){
            if (p.getEstado().equalsIgnoreCase("Activo")){
                activos++;
            }
        }
        return activos;
    }

    public int obtenerProyectosInactivos(){
        int inactivos =0;
        for (ProyectoOB p : SQL.listaProyectos){
            if (p.getEstado().equalsIgnoreCase("Inactivo")){
                inactivos++;
            }
        }
        return inactivos;
    }

    public int contarTipo(String tipo){
        int contador=0;
        for (ProyectoOB p : SQL.listaProyectos) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                contador++;
            }
        }
        return contador;
    }
}
