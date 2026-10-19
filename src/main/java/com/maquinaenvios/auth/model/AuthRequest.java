package com.maquinaenvios.auth.model;

/** DTO de entrada para las solicitudes de registro e inicio de sesión. */
public class AuthRequest {
    private String nombre;
    private String correo;
    private String contrasena;

    public AuthRequest() { }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
