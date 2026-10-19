package com.maquinaenvios.auth.model;

/** Estructura uniforme de respuesta JSON para el servicio. */
public class ApiResponse {
    private boolean exitoso;
    private String mensaje;

    public ApiResponse() { }

    public ApiResponse(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public boolean isExitoso() { return exitoso; }
    public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
