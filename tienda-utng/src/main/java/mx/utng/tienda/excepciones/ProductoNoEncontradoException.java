package mx.utng.tienda.excepciones;

/**
 * Excepcion lanzada cuando se busca un producto que no existe en el sistema.
 * @param codigo Codigo del producto que no se encontro
 */
public class ProductoNoEncontradoException extends RuntimeException {
    private final String codigo;

    public ProductoNoEncontradoException(String codigo) {
        super("Producto no encontrado con codigo: " + codigo);
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
}