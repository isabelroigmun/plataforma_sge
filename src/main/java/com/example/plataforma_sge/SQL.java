package com.example.plataforma_sge;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL {

    static String usuario;
    static String pass;
    static ArrayList<ProyectoOB> listaProyectos;
    static ArrayList<UsuarioOB> listaUsuarios;

    static int id_rol;
    static int id_usuario;

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

    //ejecuta una consulta para obtener proyectos y los guarda en la lista listaProyectos
    public static void sacar_proyectos(String consulta){
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";
        double salida;
        ResultSet resultado;
        Statement st;
        listaProyectos = new ArrayList<>();

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
                String palabrasClave = resultado.getString("key_words");
                ProyectoOB p = new ProyectoOB(codigo,nombre,fechaCreacion,fechaEjInicio,fechaEjFinal,tipo,estado,calificacion,bajadaCalificacion,enCooperacion,fases,jefeId,palabrasClave);
                listaProyectos.add(p);
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

    //ejecuta una consulta para obtener usuarios y los guarda en la lista listaUsuarios
    public static void sacar_usuarios(String consulta){
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";
        double salida;
        ResultSet resultado;
        Statement st;
        listaUsuarios = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            st = conexion.createStatement();
            resultado = st.executeQuery(consulta);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String apellidos = resultado.getString("apellidos");
                String usuario = resultado.getString("usuario");
                String contra = resultado.getString("contraseña");
                int rol = resultado.getInt("rol_id");

                UsuarioOB u = new UsuarioOB(id, nombre, apellidos, usuario, contra, rol);
                listaUsuarios.add(u);
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

    //devuelve una lsita con todos los usuarios
    public static ArrayList<UsuarioOB> getUsuarios() {
        ArrayList<UsuarioOB> listaUsuarios = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                listaUsuarios.add(new UsuarioOB(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("usuario"),
                        rs.getString("contraseña"),
                        rs.getInt("rol_id")
                ));
            }
            st.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    //devuelve una lista con todos los roles
    public static ArrayList<RolOB> getRoles() {
        ArrayList<RolOB> listaRoles = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/trabajo_sge";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM roles");

            while (rs.next()) {
                listaRoles.add(new RolOB(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
            st.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaRoles;
    }


    public static void auditoria(String consulta) {
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
                id_usuario = resultado.getInt("id");
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

    public static void comprobar_permisos(String consulta) {
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

                id_rol = resultado.getInt("rol_id");
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
}
