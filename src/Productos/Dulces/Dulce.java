package src.Productos.Dulces;

import src.Productos.Producto;

/**
 * Subclase para tipos de dulces
 */
public abstract class Dulce extends Producto {
    /**
     * Constructor de la subclase Dulces
     * @param nombre para nombre del dulce
     * @param ID para identificador del dulce
     * @param precio para precio del dulce en multiplos de 100
     * @param cantidad para cantidad disponible del dulce
     */
    public Dulce (String nombre, int ID, int precio, int cantidad){
        super(nombre, ID, precio, cantidad);
    }

    /**
     * Identifica el dulce consumido
     * @return nombre del dulce
     */
    public abstract String getSabor();
}
