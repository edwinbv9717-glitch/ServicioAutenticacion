# Servicio de Autenticación — AA5-EV01

Proyecto Java 21 + Maven + Jakarta REST (Jersey) + MySQL para registrar usuarios e iniciar sesión.

## Requisitos
- JDK 21
- Maven 3.9 o Maven integrado en NetBeans
- MySQL 8
- Apache Tomcat 10.1
- Git

## 1. Crear la base de datos
Ejecute `database/schema.sql` en MySQL Workbench.

## 2. Configurar la conexión
Por defecto, el proyecto usa:
- URL: `jdbc:mysql://localhost:3306/servicio_autenticacion?useSSL=false&serverTimezone=America/Bogota`
- Usuario: `root`
- Contraseña: vacía

Configure las variables de entorno `AUTH_DB_URL`, `AUTH_DB_USER` y `AUTH_DB_PASSWORD` según su equipo. No publique contraseñas reales en GitHub.

## 3. Construir el proyecto
Desde la carpeta donde está `pom.xml`:
```bash
mvn clean package
```
Se debe generar `target/ServicioAutenticacion.war`.

## 4. Desplegar en Tomcat
Copie el WAR a la carpeta `webapps` de Tomcat 10.1 e inicie Tomcat.
Base URL:
`http://localhost:8080/ServicioAutenticacion/api/auth`

## 5. Probar el registro
POST `http://localhost:8080/ServicioAutenticacion/api/auth/registro`
Header: `Content-Type: application/json`
Body:
```json
{
  "nombre": "Usuario de prueba",
  "correo": "prueba@example.com",
  "contrasena": "ClaveSegura123"
}
```

## 6. Probar el inicio de sesión
POST `http://localhost:8080/ServicioAutenticacion/api/auth/login`
Header: `Content-Type: application/json`
Body:
```json
{
  "correo": "prueba@example.com",
  "contrasena": "ClaveSegura123"
}
```
Respuesta esperada HTTP 200:
```json
{
  "exitoso": true,
  "mensaje": "Autenticación satisfactoria."
}
```
Si la contraseña es incorrecta, devuelve HTTP 401 y `Error en la autenticación.`

## 7. Endpoints
- `POST /api/auth/registro`
- `POST /api/auth/login`

## 8. Versionamiento Git
Desde la carpeta del proyecto:
```bash
git init
git add .
git commit -m "Crear servicio REST de registro y autenticacion"
git branch -M main
git remote add origin URL_DE_TU_REPOSITORIO
git push -u origin main
```
Reemplace `URL_DE_TU_REPOSITORIO` por la URL real del repositorio creado en GitHub.

## Seguridad y alcance
- Las contraseñas se almacenan como hash BCrypt, no en texto plano.
- Las consultas SQL usan `PreparedStatement`.
- Este ejemplo responde a la actividad formativa. Para un sistema de producción se requiere HTTPS, limitación de intentos, gestión de secretos, política de recuperación de contraseña y tokens/sesiones según el caso.
