package src.Productos;

public class Producto {
    /**
     * Clase padre de todo tipo de productos
     * Toma todos los parametros para que sean usados por todas las subclases
     */

    /** Nombre del producto*/
    private String nombre;
    /**Identificador del producto al seleccionar en maquina */
    private int ID;
    /** Valor monetario en multiplos de 100 */
    private int precio;
    /**Cantidad disponible del producto */
    private int cantidad;


    /** Constructor de la clase diseñado para usar por las subclases
     * @param nombre Nombre del producto
     * @param ID  Identificador del tipo de producto en maquina
     * @param precio Valor en monedas
     * @param cantidad Disponibilidad del producto*/
    public Producto(String nombre, int ID, int precio, int cantidad){
        this.nombre = nombre;
        this.ID = ID;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /** Funciones de acceso a parametros */

    /**
     * Obtiene cantidad actual del producto
     * @return cantidad disponible
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene identificador del producto
     * @return identificador del producto
     */
    public int getID() {
        return ID;
    }

    /**
     * obtiene el nombre del producto
     * @return el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto
     * @return el nombre del producto
     */
    public int getPrecio() {
        return precio;
    }
}
