package mx.utng.tienda.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias de la entidad {@link Producto}.
 * <p>
 * Cada prueba sigue el patron AAA: Arrange (preparar los datos),
 * Act (ejecutar la accion) y Assert (verificar el resultado).
 * </p>
 *
 * @author Estudiante UTNG
 */
class ProductoTest {

    @Test
    @DisplayName("constructor_datosValidos_creaProductoConTodosSusAtributos")
    void constructor_datosValidos_creaProductoConTodosSusAtributos() {
        // Arrange
        String codigoEsperado = "MOU-001";
        String nombreEsperado = "Mouse inalambrico";
        double precioEsperado = 249.90;
        int stockEsperado = 30;

        // Act
        Producto producto = new Producto(codigoEsperado, nombreEsperado, "Mouse optico USB", precioEsperado, stockEsperado);

        // Assert
        assertAll("El producto debe quedar creado con todos los datos proporcionados",
                () -> assertEquals(codigoEsperado, producto.getCodigo(), "El codigo no coincide"),
                () -> assertEquals(nombreEsperado, producto.getNombre(), "El nombre no coincide"),
                () -> assertEquals(precioEsperado, producto.getPrecio(), 0.001, "El precio no coincide"),
                () -> assertEquals(stockEsperado, producto.getStock(), "El stock no coincide")
        );
    }

    @Test
    @DisplayName("constructor_precioNegativo_lanzaIllegalArgumentException")
    void constructor_precioNegativo_lanzaIllegalArgumentException() {
        // Arrange
        double precioInvalido = -10.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Producto("MOU-002", "Mouse", "Descripcion", precioInvalido, 10),
                "Un precio negativo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("constructor_nombreNulo_lanzaIllegalArgumentException")
    void constructor_nombreNulo_lanzaIllegalArgumentException() {
        // Arrange
        String nombreInvalido = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Producto("MOU-003", nombreInvalido, "Descripcion", 100.0, 10),
                "Un nombre nulo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("isActivo_productoRecienCreado_retornaTrueComoValorPorDefecto")
    void isActivo_productoRecienCreado_retornaTrueComoValorPorDefecto() {
        // Arrange & Act
        Producto producto = new Producto("MOU-004", "Mouse", "Descripcion", 100.0, 10);

        // Assert
        assertTrue(producto.isActivo(), "Todo producto nuevo debe crearse activo por defecto");
    }
}
