package mx.utng.tienda.servicio;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.dao.ProductoDAOMemoria;
import mx.utng.tienda.modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de {@link ProductoService}, usando
 * {@link ProductoDAOMemoria} como doble de prueba para no depender de
 * una base de datos real.
 * <p>
 * Cada prueba sigue el patron AAA: Arrange, Act, Assert.
 * </p>
 *
 * @author Estudiante UTNG
 */
class ProductoServiceTest {

    private ProductoDAO productoDAO;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoDAO = new ProductoDAOMemoria();
        productoService = new ProductoService(productoDAO);
    }

    // ---------- registrar ----------

    @Test
    @DisplayName("registrar_productoValido_retornaIdMayorACero")
    void registrar_productoValido_retornaIdMayorACero() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 249.90, 30);

        // Act
        int idGenerado = productoService.registrar(producto);

        // Assert
        assertEquals(1, idGenerado, "El primer producto registrado debe obtener el id 1");
    }

    @Test
    @DisplayName("registrar_productoNulo_lanzaIllegalArgumentException")
    void registrar_productoNulo_lanzaIllegalArgumentException() {
        // Arrange
        Producto productoNulo = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.registrar(productoNulo),
                "Registrar un producto nulo debe lanzar IllegalArgumentException");
    }

    // ---------- vender ----------

    @Test
    @DisplayName("vender_conStockSuficiente_reduceElStockCorrectamente")
    void vender_conStockSuficiente_reduceElStockCorrectamente() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 249.90, 30);
        productoService.registrar(producto);

        // Act
        productoService.vender("MOU-001", 5);

        // Assert
        int stockRestante = productoService.obtenerPorCodigo("MOU-001")
                .map(Producto::getStock)
                .orElseThrow();
        assertEquals(25, stockRestante, "El stock debe reducirse en la cantidad vendida");
    }

    @Test
    @DisplayName("vender_conStockInsuficiente_lanzaIllegalStateException")
    void vender_conStockInsuficiente_lanzaIllegalStateException() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 249.90, 3);
        productoService.registrar(producto);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> productoService.vender("MOU-001", 10),
                "Vender mas unidades de las disponibles debe lanzar IllegalStateException");
    }

    // ---------- calcularTotalConDescuento (TDD) ----------

    @Test
    @DisplayName("calcularTotalConDescuento_precioYDescuentoValidos_calculaElTotalCorrecto")
    void calcularTotalConDescuento_precioYDescuentoValidos_calculaElTotalCorrecto() {
        // Arrange (RED: este fue el primer test escrito, antes de implementar el metodo)
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 100.0, 30);
        double descuento = 0.10;

        // Act
        double total = productoService.calcularTotalConDescuento(producto, descuento);

        // Assert
        assertEquals(90.0, total, 0.001, "Un precio de 100 con 10% de descuento debe resultar en 90.0");
    }

    @Test
    @DisplayName("calcularTotalConDescuento_productoNulo_lanzaIllegalArgumentException")
    void calcularTotalConDescuento_productoNulo_lanzaIllegalArgumentException() {
        // Arrange
        Producto productoNulo = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.calcularTotalConDescuento(productoNulo, 0.10),
                "Calcular el descuento de un producto nulo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("calcularTotalConDescuento_descuentoNegativo_lanzaIllegalArgumentException")
    void calcularTotalConDescuento_descuentoNegativo_lanzaIllegalArgumentException() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 100.0, 30);
        double descuentoInvalido = -0.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.calcularTotalConDescuento(producto, descuentoInvalido),
                "Un descuento negativo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("calcularTotalConDescuento_descuentoMayorAUno_lanzaIllegalArgumentException")
    void calcularTotalConDescuento_descuentoMayorAUno_lanzaIllegalArgumentException() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 100.0, 30);
        double descuentoInvalido = 1.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.calcularTotalConDescuento(producto, descuentoInvalido),
                "Un descuento mayor a 1 (100%) debe lanzar IllegalArgumentException");
    }
}
