package mx.utng.tienda.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Administra la conexion JDBC hacia la base de datos {@code utng_tienda}
 * en MySQL.
 * <p>
 * Las credenciales NO se escriben en el codigo fuente: se leen desde el
 * archivo {@code db.properties} (ubicado en {@code src/main/resources}),
 * el cual esta excluido del control de versiones mediante
 * {@code .gitignore} para evitar exponer usuario y contraseña en GitHub.
 * </p>
 *
 * @author Estudiante UTNG
 */
public class ConexionDB {

    private static final String ARCHIVO_CONFIG = "db.properties";

    /**
     * Abre y devuelve una nueva conexion JDBC hacia la base de datos
     * configurada en {@code db.properties}.
     *
     * @return una {@link Connection} activa lista para usarse
     * @throws SQLException si ocurre un error al conectar con MySQL
     * @throws RuntimeException si no se encuentra el archivo de configuracion
     */
    public static Connection obtenerConexion() throws SQLException {
        Properties propiedades = cargarPropiedades();

        String url = propiedades.getProperty("db.url");
        String usuario = propiedades.getProperty("db.usuario");
        String password = propiedades.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, password);
    }

    /**
     * Carga el archivo {@code db.properties} desde el classpath.
     *
     * @return las propiedades de conexion leidas del archivo
     */
    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionDB.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIG)) {

            if (entrada == null) {
                throw new RuntimeException(
                        "No se encontro " + ARCHIVO_CONFIG
                                + ". Copia db.properties.example como db.properties "
                                + "y agrega tus credenciales locales.");
            }
            propiedades.load(entrada);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer " + ARCHIVO_CONFIG, e);
        }
        return propiedades;
    }
}
