package modelo.excepciones;

/** Excepción lanzada cuando el depósito del producto seleccionado está vacío. */
public class NoHayProductoException extends Exception {
    public NoHayProductoException(String mensaje) { super(mensaje); }
}