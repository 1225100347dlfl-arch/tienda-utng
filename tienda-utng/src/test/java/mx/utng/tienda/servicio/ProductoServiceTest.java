package mx.utng.tienda.servicio;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.dao.ProductoDAOMemoria;
import mx.utng.tienda.modelo.Producto;
import mx.utng.tienda.excepciones.*; // IMPORTANTE: Importar las excepciones del dominio

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Se agrego assertAll para poder verificar multiples condiciones a la vez
import static org.junit.jupiter.api.Assertions.*;

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

    // NUEVO TEST DE LA RÚBRICA
    @Test
    @DisplayName("registrar_precioNegativo_lanzaPrecioInvalidoException")
    void registrar_precioNegativo_lanzaPrecioInvalidoException() {
        // Arrange
        Producto producto = new Producto("MOU-002", "Mouse gamer", "Mouse con luces", -50.0, 10);

        // Act & Assert
        assertThrows(PrecioInvalidoException.class,
                () -> productoService.registrar(producto),
                "Registrar un producto con precio negativo debe lanzar PrecioInvalidoException");
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

    // TEST ACTUALIZADO SEGÚN LA RÚBRICA
    @Test
    @DisplayName("vender_sinStock_verificaDetallesDeExcepcion")
    void vender_sinStock_verificaDetallesDeExcepcion() {
        // Arrange
        Producto producto = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 249.90, 3);
        productoService.registrar(producto);

        // Act
        StockInsuficienteException excepcion = assertThrows(StockInsuficienteException.class,
                () -> productoService.vender("MOU-001", 10),
                "Vender mas unidades de las disponibles debe lanzar StockInsuficienteException");

        // Assert: Verificamos los 3 detalles guardados en la excepción
        assertAll("Verificando los parametros de la excepcion",
                () -> assertEquals("MOU-001", excepcion.getCodigoProducto(), "El codigo debe coincidir"),
                () -> assertEquals(3, excepcion.getStockActual(), "El stock actual era 3"),
                () -> assertEquals(10, excepcion.getCantidadSolicitada(), "Se intentaron vender 10")
        );
    }

    // NUEVO TEST DE LA RÚBRICA
    @Test
    @DisplayName("vender_productoInexistente_lanzaProductoNoEncontradoException")
    void vender_productoInexistente_lanzaProductoNoEncontradoException() {
        // Act & Assert
        assertThrows(ProductoNoEncontradoException.class,
                () -> productoService.vender("INEXISTENTE", 5),
                "Vender un producto que no existe debe lanzar ProductoNoEncontradoException");
    }

    // ---------- calcularTotalConDescuento (TDD) ----------

    @Test
    @DisplayName("calcularTotalConDescuento_precioYDescuentoValidos_calculaElTotalCorrecto")
    void calcularTotalConDescuento_precioYDescuentoValidos_calculaElTotalCorrecto() {
        // Arrange
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