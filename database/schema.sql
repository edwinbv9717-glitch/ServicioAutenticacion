-- Base de datos para la evidencia AA5-EV01.
CREATE DATABASE IF NOT EXISTS servicio_autenticacion
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE servicio_autenticacion;

CREATE TABLE IF NOT EXISTS usuario_auth (
    id_usuario BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(190) NOT NULL UNIQUE,
    contrasena_hash VARCHAR(60) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
