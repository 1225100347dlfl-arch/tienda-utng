package mx.utng.tienda.dao;

import mx.utng.tienda.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Doble de prueba (stub) de {@link ProductoDAO} que almacena los
 * productos en memoria usando un {@link ArrayList}, sin depender de
 * ninguna base de datos.
 * <p>
 * Se usa exclusivamente en la suite de pruebas para poder probar
 * {@code ProductoService} de forma rapida, aislada y repetible (patron
 * AAA), sin necesidad de levantar MySQL.
 * </p>
 *
 * @author Estudiante UTNG
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private final List<Producto> almacen = new ArrayList<>();
    private int siguienteId = 1;

    @Override
    public int insert(Producto producto) {
        producto.setId(siguienteId);
        almacen.add(producto);
        siguienteId++;
        return producto.getId();
    }

    @Override
    public List<Producto> findAll() {
        // Copia defensiva: quien reciba la lista no puede alterar el almacen interno.
        return new ArrayList<>(almacen);
    }

    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        return almacen.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public void updateStock(String codigo, int nuevoStock) {
        almacen.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .ifPresent(p -> p.setStock(nuevoStock));
    }

    @Override
    public void delete(String codigo) {
        almacen.removeIf(p -> p.getCodigo().equals(codigo));
    }
}
