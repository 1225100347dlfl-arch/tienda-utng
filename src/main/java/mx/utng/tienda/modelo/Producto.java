package mx.utng.tienda.modelo;

/**
 * Representa un producto del catalogo de la Tienda UTNG.
 * <p>
 * Esta clase es un objeto de dominio simple (POJO) que encapsula los
 * atributos de un producto y expone accesores (getters/setters) siguiendo
 * el principio de encapsulamiento de la Programacion Orientada a Objetos.
 * </p>
 *
 * @author Estudiante UTNG
 */
public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    /**
     * Constructor vacio requerido para mapear resultados de la base
     * de datos (patron JavaBean).
     */
    public Producto() {
    }

    /**
     * Construye un producto completo, incluyendo su identificador.
     * Util cuando el registro ya existe en la base de datos.
     *
     * @param id          identificador unico del producto
     * @param nombre      nombre comercial del producto
     * @param descripcion descripcion breve del producto
     * @param precio      precio unitario del producto
     * @param stock       cantidad disponible en inventario
     */
    public Producto(int id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Construye un producto nuevo, sin identificador todavia (lo asigna
     * la base de datos al insertarlo).
     *
     * @param nombre      nombre comercial del producto
     * @param descripcion descripcion breve del producto
     * @param precio      precio unitario del producto
     * @param stock       cantidad disponible en inventario
     */
    public Producto(String nombre, String descripcion, double precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
