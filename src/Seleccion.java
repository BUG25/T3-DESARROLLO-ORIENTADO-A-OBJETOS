package src;

/**
 * Enumeración de productos disponibles en la máquina expendedora.
 * Cada constante representa un tipo de producto con su precio, ID y nombre.
 */
public enum Seleccion {
    COCA_COLA(1000, 101, "Coca Cola"),
    SPRITE(900, 102, "Sprite"),
    FANTA(900, 201, "Fanta"),
    SNICKERS(600, 202, "Snickers"),
    SUPER8(300, 203, "Super 8");

    private final int precio;
    private final int ID;
    private final String nombre;

    /**
     * Constructor de la enumeración.
     * @param precio precio del producto
     * @param ID identificador del producto
     * @param nombre nombre descriptivo del producto
     */
    Seleccion(int precio, int ID, String nombre) {
        this.precio = precio;
        this.ID = ID;
        this.nombre = nombre;
    }

    /** @return precio del producto */
    public int getPrecio() { return precio; }

    /** @return ID del producto */
    public int getID() { return ID; }

    /** @return nombre del producto */
    public String getNombre() { return nombre; }
}