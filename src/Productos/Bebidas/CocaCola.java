package src.Productos.Bebidas;

public class CocaCola extends Bebida {
    public CocaCola (String nombre, int ID, int precio, int cantidad){
        /**
         * Constructor de la subclase Cocacola
         * Recibe los parametros de la clase padre
         */
        super(nombre, ID, precio, cantidad);
    }

    @Override
    /**
    * Sobreescribe el sabor al de bebida Cocacola
    */
    public String getSabor() {
        return "CocaCola";
    }
}
