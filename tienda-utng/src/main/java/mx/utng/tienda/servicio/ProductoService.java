package mx.utng.tienda.servicio;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.modelo.Producto;
import mx.utng.tienda.excepciones.*; // IMPORTANTE: Importar las nuevas excepciones

import java.util.List;
import java.util.Optional;

/**
 * Contiene la logica de negocio relacionada con productos: registrar,
 * vender y calcular precios con descuento.
 * <p>
 * La dependencia se recibe por constructor ({@link ProductoDAO}), no se
 * crea internamente, aplicando Inyeccion de Dependencias y el principio
 * Abierto/Cerrado de SOLID: se puede cambiar la implementacion de acceso
 * a datos (JDBC real o un doble de prueba en memoria) sin modificar esta
 * clase.
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
        // Tarea 2: Validar que el DAO no sea nulo
        if (productoDAO == null) {
            throw new IllegalArgumentException("El DAO no puede ser nulo");
        }
        this.productoDAO = productoDAO;
    }

    /**
     * Registra un nuevo producto en el catalogo.
     *
     * @param producto producto a registrar (no puede ser nulo)
     * @return el identificador generado para el producto
     * @throws IllegalArgumentException si el producto es nulo
     */
    public int registrar(Producto producto) {
        // Tarea 2: Extraer validarProducto() privado
        validarProducto(producto);
        return productoDAO.insert(producto);
    }

    // Tarea 2: Método privado para validaciones
    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (producto.getPrecio() < 0) {
            throw new PrecioInvalidoException(producto.getPrecio());
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }

    /**
     * Registra la venta de una cantidad de un producto, descontando del
     * stock disponible.
     *
     * @param codigo   codigo del producto vendido
     * @param cantidad cantidad vendida (debe ser mayor a cero)
     * @throws IllegalArgumentException si el codigo no existe o la cantidad no es valida
     * @throws StockInsuficienteException si el stock disponible es insuficiente
     */
    public void vender(String codigo, int cantidad) {
        // Tarea 2: Guard clauses al inicio
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a vender debe ser mayor a cero");
        }

        // Tarea 2: Usar ProductoNoEncontradoException
        Producto producto = productoDAO.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNoEncontradoException(codigo));

        // Tarea 2: Usar StockInsuficienteException con 3 parámetros
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(codigo, producto.getStock(), cantidad);
        }

        productoDAO.updateStock(codigo, producto.getStock() - cantidad);
    }

    /**
     * Elimina un producto por su codigo.
     *
     * @param codigo codigo del producto
     */
    public void eliminar(String codigo) {
        productoDAO.delete(codigo);
    }

    /**
     * Obtiene un producto por su codigo.
     *
     * @param codigo codigo del producto
     * @return el producto encontrado, envuelto en {@link Optional}
     */
    public Optional<Producto> obtenerPorCodigo(String codigo) {
        return productoDAO.findByCodigo(codigo);
    }

    /**
     * Obtiene el catalogo completo de productos.
     *
     * @return lista de productos registrados
     */
    public List<Producto> obtenerCatalogo() {
        return productoDAO.findAll();
    }

    /**
     * Calcula el precio final de un producto aplicando un descuento.
     * <p>
     * Implementado con TDD: primero se escribio el test que exige que
     * un precio de 100 con un descuento de 0.10 (10%) resulte en 90.0
     * (RED), luego se agrego la formula minima para pasarlo (GREEN), y
     * finalmente se anadieron las validaciones de argumentos invalidos
     * (REFACTOR).
     * </p>
     *
     * @param producto  producto sobre el cual se calcula el descuento (no nulo)
     * @param descuento porcentaje de descuento expresado como fraccion, entre 0 y 1
     * @return el precio final del producto con el descuento aplicado
     * @throws IllegalArgumentException si el producto es nulo o el descuento esta fuera del rango [0, 1]
     */
    public double calcularTotalConDescuento(Producto producto, double descuento) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (descuento < 0 || descuento > 1) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
        }
        return producto.getPrecio() * (1 - descuento);
    }
}