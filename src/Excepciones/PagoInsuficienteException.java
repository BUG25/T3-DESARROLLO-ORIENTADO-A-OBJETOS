package modelo.excepciones;

/** Excepción lanzada cuando el valor de la moneda no alcanza para el producto. */
public class PagoInsuficienteException extends Exception {
    public PagoInsuficienteException(String mensaje) { super(mensaje); }
}