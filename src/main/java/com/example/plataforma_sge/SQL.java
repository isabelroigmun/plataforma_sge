package com.example.plataforma_sge;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL {

    static String usuario;
    static String pass;
    static ArrayList<ProyectoOB>lista;

    public static void consulta(String consulta) {
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";
        double salida;
        ResultSet resultado;
        Statement st;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            resultado = st.executeQuery(consulta);
            while (resultado.next()) {
                usuario = resultado.getString("usuario");
                pass= resultado.getString("contraseña");
            }
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public static void sacar_proyectos(String consulta){
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";
        double salida;
        ResultSet resultado;
        Statement st;
        lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            resultado = st.executeQuery(consulta);
            while (resultado.next()) {
                int codigo = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                Timestamp fechaCreacionTS = resultado.getTimestamp("fecha_creacion");
                java.time.LocalDateTime fechaCreacion = fechaCreacionTS.toLocalDateTime();
                java.time.LocalDate fechaEjInicio = resultado.getDate("fecha_ej_inicio").toLocalDate();
                java.time.LocalDate fechaEjFinal = null;
                if (resultado.getDate("fecha_ej_final") != null) {
                    fechaEjFinal = resultado.getDate("fecha_ej_final").toLocalDate();
                }
                String tipo = resultado.getString("tipo_proyecto");
                String estado;
                if (resultado.getBoolean("activo")) {
                    estado = "activo";
                } else {
                    estado = "inactivo";
                }
                String calificacion = resultado.getString("calificacion");
                boolean bajadaCalificacion = resultado.getBoolean("bajada_calificacion");
                boolean enCooperacion = resultado.getBoolean("en_cooperacion");
                int fases = resultado.getInt("fases");
                int jefeId = resultado.getInt("usuarios_id");

                //LA CLASE PROYECTO NO TENÍA TODOS LOS ATRIBUTOS Y LOS HE TENIDO QUE METER PARA HACER
                //EL BOTÓN DE CREAR Y EDITAR (TENGO QUE TENER TODOS LOS DATOS PARA QUE VAYA BIEN)
                //ENTONCES FALTA MODIFICAR ESTO PARA QUE VAYA BIEN, DE MOMENTO LO COMENTO Y
                // LO DEJO PROVISIONALMENTE MAL PERO BUENO YA LO CAMBIAMOS CUANDO SE PUEDA
                ProyectoOB p = new ProyectoOB(codigo,nombre,fechaCreacion,fechaEjInicio,fechaEjFinal,tipo,estado,calificacion,bajadaCalificacion,enCooperacion,fases,jefeId);
                lista.add(p);
            }
            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public static void vacio(String consulta) {
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";
        double salida;
        int resultado;
        Statement st;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            resultado = st.executeUpdate(consulta);

            st.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found.");
        } catch (SQLException e) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
