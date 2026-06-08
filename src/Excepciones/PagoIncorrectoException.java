package src.Excepciones;

/** Excepción lanzada cuando el pago entregado es inválido (nulo). */
public class PagoIncorrectoException extends Exception {
    public PagoIncorrectoException(String mensaje) { super(mensaje); }
}