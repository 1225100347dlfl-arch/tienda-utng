package mx.utng.tienda.modelo;

/**
 * Representa un producto del catalogo de la Tienda UTNG.
 * <p>
 * Esta clase es un objeto de dominio (POJO) que encapsula los atributos
 * de un producto y valida sus invariantes en el constructor: un producto
 * nunca puede existir con nombre nulo/vacio o con precio negativo. Esto
 * evita que se propaguen datos invalidos hacia las capas de servicio y
 * persistencia (falla rapido / fail-fast).
 * </p>
 *
 * @author Estudiante UTNG
 */
public class Producto {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private boolean activo;

    /**
     * Construye un producto nuevo (sin id todavia, lo asigna el DAO al
     * insertarlo) validando sus invariantes.
     *
     * @param codigo      codigo unico del producto
     * @param nombre      nombre comercial del producto (obligatorio)
     * @param descripcion descripcion breve del producto
     * @param precio      precio unitario del producto (debe ser >= 0)
     * @param stock       cantidad disponible en inventario
     * @throws IllegalArgumentException si el nombre es nulo/vacio o el precio es negativo
     */
    public Producto(String codigo, String nombre, String descripcion, double precio, int stock) {
        validarNombre(nombre);
        validarPrecio(precio);

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.activo = true;
    }

    /**
     * Construye un producto completo, incluyendo su identificador.
     * Util cuando el registro ya existe en la base de datos.
     *
     * @param id          identificador unico del producto
     * @param codigo      codigo unico del producto
     * @param nombre      nombre comercial del producto (obligatorio)
     * @param descripcion descripcion breve del producto
     * @param precio      precio unitario del producto (debe ser >= 0)
     * @param stock       cantidad disponible en inventario
     * @throws IllegalArgumentException si el nombre es nulo/vacio o el precio es negativo
     */
    public Producto(int id, String codigo, String nombre, String descripcion, double precio, int stock) {
        this(codigo, nombre, descripcion, precio, stock);
        this.id = id;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo ni vacio");
        }
    }

    private void validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarNombre(nombre);
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
        validarPrecio(precio);
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Indica si el producto esta activo (visible/vendible) en el catalogo.
     * Todo producto nuevo se crea activo por defecto.
     *
     * @return {@code true} si el producto esta activo
     */
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", activo=" + activo +
                '}';
    }
}
