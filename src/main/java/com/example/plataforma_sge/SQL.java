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
        lista=new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            resultado = st.executeQuery(consulta);
            while (resultado.next()) {
                Integer codigo = resultado.getInt("id");
                String nombre= resultado.getString("nombre");
                String tipo= resultado.getString("tipo_proyecto");
                Boolean activo= resultado.getBoolean("activo");
                Integer usuario_id=resultado.getInt("usuarios_id");

                ProyectoOB p= new ProyectoOB(codigo,nombre,tipo,activo,usuario_id);
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
