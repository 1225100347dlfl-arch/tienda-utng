package mx.utng.tienda.servicio;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.modelo.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * Contiene la logica de negocio relacionada con productos: valida los
 * datos antes de delegar la persistencia al DAO correspondiente.
 * <p>
 * La dependencia se recibe por constructor ({@link ProductoDAO}), no se
 * crea internamente, aplicando Inyeccion de Dependencias y el principio
 * Abierto/Cerrado de SOLID: se puede cambiar la implementacion de acceso
 * a datos sin modificar esta clase.
 * </p>
 *
 * @author Estudiante UTNG
 */
public class ProductoService {

    private final ProductoDAO productoDAO;

    /**
     * Crea el servicio recibiendo el DAO a utilizar.
     *
     * @param productoDAO implementacion de acceso a datos de productos
     */
    public ProductoService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * Registra un nuevo producto, validando previamente sus datos.
     *
     * @param producto producto a registrar
     * @throws SQLException             si ocurre un error de acceso a datos
     * @throws IllegalArgumentException si los datos del producto son invalidos
     */
    public void registrarProducto(Producto producto) throws SQLException {
        validarProducto(producto);
        productoDAO.insertar(producto);
    }

    /**
     * Actualiza un producto existente, validando previamente sus datos.
     *
     * @param producto producto con los nuevos valores
     * @throws SQLException             si ocurre un error de acceso a datos
     * @throws IllegalArgumentException si los datos del producto son invalidos
     */
    public void actualizarProducto(Producto producto) throws SQLException {
        validarProducto(producto);
        productoDAO.actualizar(producto);
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto
     * @throws SQLException si ocurre un error de acceso a datos
     */
    public void eliminarProducto(int id) throws SQLException {
        productoDAO.eliminar(id);
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param id identificador del producto
     * @return el producto encontrado, o {@code null} si no existe
     * @throws SQLException si ocurre un error de acceso a datos
     */
    public Producto obtenerProducto(int id) throws SQLException {
        return productoDAO.buscarPorId(id);
    }

    /**
     * Obtiene el catalogo completo de productos.
     *
     * @return lista de productos registrados
     * @throws SQLException si ocurre un error de acceso a datos
     */
    public List<Producto> obtenerCatalogo() throws SQLException {
        return productoDAO.listarTodos();
    }

    /**
     * Valida las reglas de negocio minimas de un producto antes de
     * persistirlo: nombre obligatorio, precio positivo y stock no
     * negativo.
     *
     * @param producto producto a validar
     * @throws IllegalArgumentException si alguna regla no se cumple
     */
    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
