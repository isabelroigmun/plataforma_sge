package com.example.plataforma_sge;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL {

    static String usuario;
    static String pass;

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
}
