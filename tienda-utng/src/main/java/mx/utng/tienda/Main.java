package mx.utng.tienda;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.dao.impl.ProductoDAOImpl;
import mx.utng.tienda.modelo.Producto;
import mx.utng.tienda.servicio.ProductoService;

import java.util.List;

/**
 * Punto de entrada de la aplicacion. Ejecuta una demostracion completa
 * del flujo de negocio: registrar un producto, listar el catalogo,
 * vender unidades, calcular un precio con descuento y eliminar el
 * producto de demostracion.
 *
 * @author Estudiante UTNG
 */
public class Main {

    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAOImpl();
        ProductoService productoService = new ProductoService(productoDAO);

        // 1. Registrar un producto nuevo
        Producto nuevo = new Producto("MOU-001", "Mouse inalambrico", "Mouse optico USB", 249.90, 30);
        int id = productoService.registrar(nuevo);
        System.out.println("Producto registrado con id " + id + ": " + nuevo);

        // 2. Listar catalogo completo
        System.out.println("\n--- Catalogo actual ---");
        List<Producto> catalogo = productoService.obtenerCatalogo();
        catalogo.forEach(System.out::println);

        // 3. Vender unidades del producto
        productoService.vender("MOU-001", 5);
        System.out.println("\nStock despues de la venta: "
                + productoService.obtenerPorCodigo("MOU-001").map(Producto::getStock).orElse(-1));

        // 4. Calcular el precio con descuento
        double totalConDescuento = productoService.calcularTotalConDescuento(nuevo, 0.10);
        System.out.println("\nPrecio con 10% de descuento: " + totalConDescuento);

        // 5. Eliminar el producto de demostracion
        productoService.eliminar("MOU-001");
        System.out.println("\nProducto MOU-001 eliminado.");
    }
}
