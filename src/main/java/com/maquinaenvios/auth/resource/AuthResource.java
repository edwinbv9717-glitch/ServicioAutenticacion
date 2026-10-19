package com.maquinaenvios.auth.resource;

import com.maquinaenvios.auth.dao.UsuarioDAO;
import com.maquinaenvios.auth.model.ApiResponse;
import com.maquinaenvios.auth.model.AuthRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/** Endpoints REST para registrar usuarios e iniciar sesión. */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /** Registra una cuenta nueva y almacena solamente el hash BCrypt. */
    @POST
    @Path("/registro")
    public Response registrar(AuthRequest solicitud) {
        if (solicitud == null
                || vacio(solicitud.getNombre())
                || vacio(solicitud.getCorreo())
                || vacio(solicitud.getContrasena())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Nombre, correo y contraseña son obligatorios."))
                    .build();
        }

        String nombre = solicitud.getNombre().trim();
        String correo = solicitud.getCorreo().trim().toLowerCase();

        // Validación sencilla de formato; la verificación completa debe hacerse también en producción.
        if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "El correo electrónico no tiene un formato válido."))
                    .build();
        }
        if (solicitud.getContrasena().length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "La contraseña debe tener al menos 8 caracteres."))
                    .build();
        }

        try {
            if (usuarioDAO.existeCorreo(correo)) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ApiResponse(false, "Ya existe una cuenta con ese correo."))
                        .build();
            }

            // Nunca se almacena la contraseña original.
            String hash = BCrypt.hashpw(solicitud.getContrasena(), BCrypt.gensalt(12));
            usuarioDAO.registrar(nombre, correo, hash);

            return Response.status(Response.Status.CREATED)
                    .entity(new ApiResponse(true, "Usuario registrado satisfactoriamente."))
                    .build();
        } catch (SQLException e) {
            // El detalle técnico se registra en el servidor, no se expone al cliente.
            e.printStackTrace();
            return Response.serverError()
                    .entity(new ApiResponse(false, "No fue posible registrar el usuario."))
                    .build();
        }
    }

    /** Comprueba correo y contraseña y responde con éxito o error de autenticación. */
    @POST
    @Path("/login")
    public Response iniciarSesion(AuthRequest solicitud) {
        if (solicitud == null || vacio(solicitud.getCorreo()) || vacio(solicitud.getContrasena())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Correo y contraseña son obligatorios."))
                    .build();
        }

        String correo = solicitud.getCorreo().trim().toLowerCase();
        try {
            String hash = usuarioDAO.obtenerHashPorCorreo(correo);

            // La misma respuesta evita revelar si una cuenta específica existe.
            if (hash == null || !BCrypt.checkpw(solicitud.getContrasena(), hash)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ApiResponse(false, "Error en la autenticación."))
                        .build();
            }

            return Response.ok(new ApiResponse(true, "Autenticación satisfactoria.")).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity(new ApiResponse(false, "No fue posible procesar la autenticación."))
                    .build();
        }
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
