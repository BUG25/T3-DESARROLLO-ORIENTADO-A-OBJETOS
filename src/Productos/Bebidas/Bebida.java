package src.Productos.Bebidas;

import src.Productos.Producto;

public abstract class Bebida extends Producto {
    /**
     * Constructor de la subclase Bebida
     * @param nombre para nombre de la bebida
     * @param ID para identificador de la bebida
     * @param precio para precio de la bebida en multiplos de 100
     * @param cantidad para cantidad disponible de la bebida
     */
    public Bebida (String nombre, int ID, int precio, int cantidad){
        super(nombre, ID, precio, cantidad);
    }
    /**
     * Identifica la bebida consumida
     * @return nombre de la bebida
     */
    public abstract String getSabor();
}
