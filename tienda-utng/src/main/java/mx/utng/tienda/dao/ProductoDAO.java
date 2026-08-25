package mx.utng.tienda.dao;

import mx.utng.tienda.modelo.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de acceso a datos para la entidad {@link Producto}.
 * <p>
 * Definir esta interface separa la logica de negocio de los detalles de
 * persistencia (principio de Inversion de Dependencias de SOLID). Gracias
 * a esto, {@code ProductoService} puede probarse con un doble de prueba
 * en memoria ({@code ProductoDAOMemoria}) sin necesidad de una base de
 * datos real.
 * </p>
 *
 * @author Estudiante UTNG
 */
public interface ProductoDAO {

    /**
     * Inserta un nuevo producto y le asigna un identificador
     * autoincremental.
     *
     * @param producto producto a insertar (sin id)
     * @return el identificador generado para el producto
     */
    int insert(Producto producto);

    /**
     * Obtiene todos los productos registrados.
     *
     * @return copia defensiva de la lista de productos (vacia si no hay registros)
     */
    List<Producto> findAll();

    /**
     * Busca un producto por su codigo unico.
     *
     * @param codigo codigo del producto
     * @return un {@link Optional} con el producto si existe, vacio si no
     */
    Optional<Producto> findByCodigo(String codigo);

    /**
     * Actualiza el stock disponible de un producto identificado por su
     * codigo.
     *
     * @param codigo     codigo del producto
     * @param nuevoStock nueva cantidad en inventario
     */
    void updateStock(String codigo, int nuevoStock);

    /**
     * Elimina un producto por su codigo.
     *
     * @param codigo codigo del producto a eliminar
     */
    void delete(String codigo);
}
