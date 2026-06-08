package src.Depositos;

import java.util.ArrayList;

/**
 * Clase genérica que representa un depósito para almacenar productos o monedas.
 * @param <T> tipo de objeto que almacena (Bebida, Dulce, Moneda, etc.)
 */
public class Deposito<T> {
    private ArrayList<T> lista;

    /**
     * Constructor: inicializa el depósito con una lista vacía.
     */
    public Deposito() {
        this.lista = new ArrayList<>();
    }

    /**
     * Agrega un elemento al depósito.
     * @param item objeto a agregar
     */
    public void add(T item) {
        this.lista.add(item);
    }

    /**
     * Extrae y retorna el primer elemento del depósito.
     * @return primer elemento, o null si el depósito está vacío
     */
    public T get() {
        if (lista.isEmpty()) return null;
        return lista.remove(0);
    }

    /**
     * Retorna el número de elementos en el depósito.
     * @return cantidad de elementos
     */
    public int size() {
        return lista.size();
    }

    /**
     * Indica si el depósito está vacío.
     * @return true si no hay elementos
     */
    public boolean isEmpty() {
        return lista.isEmpty();
    }

    /**
     * Retorna una copia de la lista interna para recorrerla sin modificarla.
     * @return copia de la lista
     */
    public ArrayList<T> getLista() {
        return new ArrayList<>(lista);
    }
}