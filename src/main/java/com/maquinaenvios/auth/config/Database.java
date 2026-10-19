
package com.maquinaenvios.auth.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL =
            "jdbc:mysql://localhost:3306/servicio_autenticacion"
            + "?useSSL=false&serverTimezone=America/Bogota";

    private static final String USER = "root";

    // Escribe aquí tu contraseña real de MySQL.
    private static final String PASSWORD = "Nicoll17*.17";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encontró el controlador JDBC de MySQL.",
                    e
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
