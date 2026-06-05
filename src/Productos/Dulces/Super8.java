package src.Productos.Dulces;


public class Super8 extends Dulce {
    public Super8(String nombre, int ID, int precio, int cantidad) {
        /**
         * Constructor de la subclase Super8
         * Recibe los parametros de la clase padre
         */
        super(nombre, ID, precio, cantidad);
    }

    @Override
        /**
        * Sobreescribe el sabor al del dulce Super8
        */
    public String getSabor() {
        return "Super8";
    }
}