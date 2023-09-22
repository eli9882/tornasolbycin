package edu.ulatina.servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Servicio {

    protected Connection conexion = null;
    private String host = "proyecto2db.ckagnhfndbpq.us-east-1.rds.amazonaws.com";
    private String puerto = "3306";
    private String sid = "proyecto2";
    private String usuario = "admin";
    private String clave = "adminproyecto2";

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + puerto + "/" + sid + /*"?autoReconnect=true"*/ "?serverTimezone=UTC",
                    usuario, clave);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConexion() {
        this.conectar();
        return conexion;
    }

}
