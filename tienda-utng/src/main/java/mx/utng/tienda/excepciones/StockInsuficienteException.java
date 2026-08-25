package mx.utng.tienda.excepciones;

/**
 * Excepcion lanzada cuando no hay suficiente stock para realizar una venta.
 * @param codigoProducto Codigo del producto
 * @param stockActual Stock disponible actualmente
 * @param cantidadSolicitada Cantidad que se intento vender
 */
public class StockInsuficienteException extends RuntimeException {
    private final String codigoProducto;
    private final int stockActual;
    private final int cantidadSolicitada;

    public StockInsuficienteException(String codigoProducto, int stockActual, int cantidadSolicitada) {
        super(String.format("Stock insuficiente para el producto %s. Actual: %d, Solicitado: %d", codigoProducto, stockActual, cantidadSolicitada));
        this.codigoProducto = codigoProducto;
        this.stockActual = stockActual;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getCodigoProducto() { return codigoProducto; }
    public int getStockActual() { return stockActual; }
    public int getCantidadSolicitada() { return cantidadSolicitada; }
}