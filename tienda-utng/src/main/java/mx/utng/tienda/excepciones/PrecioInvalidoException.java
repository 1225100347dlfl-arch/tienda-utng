package mx.utng.tienda.excepciones;

/**
 * Excepcion lanzada cuando el precio de un producto es invalido.
 * @param precio Precio invalido que se intento asignar
 */
public class PrecioInvalidoException extends RuntimeException {
    private final double precio;

    public PrecioInvalidoException(double precio) {
        super(String.format("Precio invalido: %.2f. El precio debe ser mayor o igual a 0.", precio));
        this.precio = precio;
    }

    public double getPrecio() { return precio; }
}