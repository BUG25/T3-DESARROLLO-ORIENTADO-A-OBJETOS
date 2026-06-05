package src.Productos.Bebidas;

public class Sprite extends Bebida {
    public Sprite(String nombre, int ID, int precio, int cantidad){
        /**
         * Constructor de la subclase Sprite
         * Recibe los parametros de la clase padre
         */
        super(nombre, ID, precio, cantidad);
    }

    @Override
    /**
    * Sobreescribe el sabor al de bebida Sprite
    */
    public String getSabor() {
        return "Sprite";
    }
}
