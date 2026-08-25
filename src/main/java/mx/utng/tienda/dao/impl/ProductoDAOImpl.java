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

/**
 * Implementacion de {@link ProductoDAO} que persiste los productos en
 * MySQL usando JDBC con {@link PreparedStatement}, lo que previene
 * inyeccion SQL en todas las operaciones.
 *
 * @author Estudiante UTNG
 */
public class ProductoDAOImpl implements ProductoDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
    private static final String SQL_ACTUALIZAR =
            "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM producto WHERE id = ?";
    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id, nombre, descripcion, precio, stock FROM producto WHERE id = ?";
    private static final String SQL_LISTAR_TODOS =
            "SELECT id, nombre, descripcion, precio, stock FROM producto ORDER BY id";

    @Override
    public void insertar(Producto producto) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.executeUpdate();

            try (ResultSet llaves = stmt.getGeneratedKeys()) {
                if (llaves.next()) {
                    producto.setId(llaves.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_ELIMINAR)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Producto buscarPorId(int id) throws SQLException {
        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_BUSCAR_POR_ID)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conexion.prepareStatement(SQL_LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        }
        return productos;
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
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }
}
