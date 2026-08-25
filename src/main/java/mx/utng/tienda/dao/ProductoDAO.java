package mx.utng.tienda.dao;

import mx.utng.tienda.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acceso a datos para la entidad {@link Producto}.
 * <p>
 * Definir esta interface separa la logica de negocio de los detalles de
 * persistencia (principio de Inversion de Dependencias de SOLID): las
 * capas superiores dependen de esta abstraccion y no de una
 * implementacion concreta con JDBC.
 * </p>
 *
 * @author Estudiante UTNG
 */
public interface ProductoDAO {

    /**
     * Inserta un nuevo producto en la base de datos.
     *
     * @param producto producto a insertar (sin id)
     * @throws SQLException si ocurre un error de acceso a datos
     */
    void insertar(Producto producto) throws SQLException;

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto producto con los nuevos valores (debe incluir id)
     * @throws SQLException si ocurre un error de acceso a datos
     */
    void actualizar(Producto producto) throws SQLException;

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     * @throws SQLException si ocurre un error de acceso a datos
     */
    void eliminar(int id) throws SQLException;

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto
     * @return el producto encontrado, o {@code null} si no existe
     * @throws SQLException si ocurre un error de acceso a datos
     */
    Producto buscarPorId(int id) throws SQLException;

    /**
     * Obtiene todos los productos registrados.
     *
     * @return lista de productos (vacia si no hay registros)
     * @throws SQLException si ocurre un error de acceso a datos
     */
    List<Producto> listarTodos() throws SQLException;
}
