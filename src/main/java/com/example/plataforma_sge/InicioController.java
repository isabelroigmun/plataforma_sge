package com.example.plataforma_sge;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class InicioController {
    @FXML
    private Label txtUsuario; //texto bienvenida al usuario
    @FXML
    private PieChart pieChart; //gráfico circular
    @FXML
    private BarChart<String,Integer> barChart; //gráfico de barras

    //inicializa cargando todos los proyectos de la base de datos, cargando el nombre del usuario y los gráficos con los datos actualizados
    @FXML
    public void initialize() {
        SQL.sacar_proyectos("SELECT * FROM proyectos");
        cargarUsuario();
        cargarPieChart();
        cargarBarChart();
    }

    //muestra mensaje de bienvenida al usuario
    public void cargarUsuario() {
        txtUsuario.setText("Hola " + SQL.usuario);
    }

    //carga el gráfico circular con los proyectos activos e inactivos que hay
    public void cargarPieChart() {
        pieChart.getData().clear();
        pieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Activos", obtenerProyectosActivos()),
                new PieChart.Data("Inactivos", obtenerProyectosInactivos())
        ));
    }

    //carga el gráfico de barras con los proyectos por tipo que hay
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

    //método para obtener el número de proyectos activos
    public int obtenerProyectosActivos(){
        int activos =0;
        for (ProyectoOB p : SQL.listaProyectos){
            if (p.getEstado().equalsIgnoreCase("Activo")){
                activos++;
            }
        }
        return activos;
    }
    //método para obtener el número de proyectos inactivos
    public int obtenerProyectosInactivos(){
        int inactivos =0;
        for (ProyectoOB p : SQL.listaProyectos){
            if (p.getEstado().equalsIgnoreCase("Inactivo")){
                inactivos++;
            }
        }
        return inactivos;
    }

    //cuenta cuántos proyectos por tipo hay
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
