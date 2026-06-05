package src.Productos.Dulces;

public class Snickers extends Dulce{
    public Snickers(String nombre, int ID, int precio, int cantidad){
        /**
         * Constructor de la subclase Snickers
         * Recibe los parametros de la clase padre
         */
        super(nombre, ID, precio, cantidad);
    }

    @Override
    /**
    * Sobreescribe el sabor al del dulce Snickers
    */
    public String getSabor() {
        return "Snickers";
    }
}
