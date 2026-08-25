package mx.utng.tienda;

import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.dao.impl.ProductoDAOImpl;
import mx.utng.tienda.modelo.Producto;
import mx.utng.tienda.servicio.ProductoService;

import java.sql.SQLException;
import java.util.List;

/**
 * Punto de entrada de la aplicacion. Ejecuta una demostracion completa
 * del CRUD de productos: registrar, listar, actualizar, buscar y
 * eliminar, usando {@link ProductoService} sobre {@link ProductoDAOImpl}.
 *
 * @author Estudiante UTNG
 */
public class Main {

    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAOImpl();
        ProductoService productoService = new ProductoService(productoDAO);

        try {
            // 1. Registrar un producto nuevo
            Producto nuevo = new Producto("Mouse inalambrico", "Mouse optico USB", 249.90, 30);
            productoService.registrarProducto(nuevo);
            System.out.println("Producto registrado: " + nuevo);

            // 2. Listar catalogo completo
            System.out.println("\n--- Catalogo actual ---");
            List<Producto> catalogo = productoService.obtenerCatalogo();
            catalogo.forEach(System.out::println);

            // 3. Actualizar el producto recien creado
            nuevo.setPrecio(219.90);
            nuevo.setStock(45);
            productoService.actualizarProducto(nuevo);
            System.out.println("\nProducto actualizado: " + productoService.obtenerProducto(nuevo.getId()));

            // 4. Eliminar el producto de demostracion
            productoService.eliminarProducto(nuevo.getId());
            System.out.println("\nProducto con id " + nuevo.getId() + " eliminado.");

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Datos invalidos: " + e.getMessage());
        }
    }
}
