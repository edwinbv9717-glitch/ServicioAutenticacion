package com.maquinaenvios.auth.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/** Prefijo común de los endpoints REST: /api. */
@ApplicationPath("/api")
public class RestApplication extends Application {
}
