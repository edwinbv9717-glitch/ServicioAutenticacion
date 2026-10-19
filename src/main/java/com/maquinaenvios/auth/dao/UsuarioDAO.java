package com.maquinaenvios.auth.dao;

import com.maquinaenvios.auth.config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Acceso a datos de usuarios. Todas las consultas usan parámetros SQL. */
public class UsuarioDAO {

    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT id_usuario FROM usuario_auth WHERE correo = ?";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void registrar(String nombre, String correo, String hash) throws SQLException {
        String sql = "INSERT INTO usuario_auth (nombre, correo, contrasena_hash) VALUES (?, ?, ?)";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, hash);
            ps.executeUpdate();
        }
    }

    public String obtenerHashPorCorreo(String correo) throws SQLException {
        String sql = "SELECT contrasena_hash FROM usuario_auth WHERE correo = ? AND activo = TRUE";
        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("contrasena_hash") : null;
            }
        }
    }
}
