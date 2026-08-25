package mx.utng.tienda.dao.impl;

import mx.utng.tienda.conexion.ConexionDB;
import mx.utng.tienda.dao.ProductoDAO;
import mx.utng.tienda.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link ProductoDAO} que persiste los productos en
 * MySQL usando JDBC con {@link PreparedStatement}, lo que previene
 * inyección SQL en todas las operaciones.
 * <p>
 * Las {@link SQLException} de JDBC se envuelven en {@link RuntimeException}
 * para mantener la interfaz {@link ProductoDAO} libre de excepciones
 * revisadas, de forma que tanto esta implementación como el doble de
 * prueba en memoria compartan el mismo contrato.
 * </p>
 *
 * @author Estudiante UTNG
 */
public class ProductoDAOImpl implements ProductoDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO producto (codigo, nombre, descripcion, precio, stock, activo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_ACTUALIZAR_STOCK =
            "UPDATE producto SET stock = ? WHERE codigo = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM producto WHERE codigo = ?";
    private static final String SQL_BUSCAR_POR_CODIGO =
            "SELECT id, codigo, nombre, descripcion, precio, stock, activo FROM producto WHERE codigo = ?";
    private static final String SQL_LISTAR_TODOS =
            "SELECT id, codigo, nombre, descripcion, precio, stock, activo FROM producto ORDER BY id";

    @Override
    public int insert(Producto producto) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getStock());
            stmt.setBoolean(6, producto.isActivo());
            stmt.executeUpdate();

            try (ResultSet llaves = stmt.getGeneratedKeys()) {
                if (llaves.next()) {
                    int id = llaves.getInt(1);
                    producto.setId(id);
                    return id;
                }
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el producto", e);
        }
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            return productos;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los productos", e);
        }
    }

    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_BUSCAR_POR_CODIGO)) {

            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearProducto(rs));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto por codigo", e);
        }
    }

    @Override
    public void updateStock(String codigo, int nuevoStock) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_ACTUALIZAR_STOCK)) {

            stmt.setInt(1, nuevoStock);
            stmt.setString(2, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el stock del producto", e);
        }
    }

    @Override
    public void delete(String codigo) {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_ELIMINAR)) {

            stmt.setString(1, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }

    /**
     * Convierte el registro actual de un {@link ResultSet} en un objeto
     * {@link Producto}.
     *
     * @param rs resultado posicionado en la fila a mapear
     * @return el producto mapeado
     * @throws SQLException si ocurre un error al leer las columnas
     */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto(
                rs.getInt("id"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
}