package src;
import src.Depositos.Deposito;
import src.Excepciones.*; /** * es para traer todas las clases publicas dentro de la carpeta (PagoInsufiente, NoHayProducto, PagoIncorrecto) */
import src.Monedas.*; /**  * impotara Moneda100, Moneda1000 y Moneda500 */
import src.Productos.*; /** todos los productos */
import src.Productos.Bebidas.*; /** COCA_COLA, SPRITE,FANTA */
import src.Productos.Dulces.*; /** SUPER8 Y SNICKERS */

public class Maquina {
    private int id;
    /** depositos para cada  producto */
    private Deposito<Bebida> cocacola;
    private Deposito<Bebida> sprite;
    private Deposito<Bebida> fanta;
    private Deposito<Dulce> snickers;
    private Deposito<Dulce> super8;

/** deposito gen para las monedad del vuelto */
private Deposito<Moneda> monVuelto;
    /** crear depositos para cada producto y el vuelto */
public Maquina (int id, int cantidad) {
    /** constructor de la maquina
    * se rellenan los depositos usando los datos de selccion.java
    * @param id unico
    * @param cantidad inicial de los productos
    */
    this.id = id;
    this.cocacola = new Deposito <> ();
    this.sprite = new Deposito <> ();
    this.fanta = new Deposito <> ();
    this.snickers = new Deposito <> ();
    this.super8 = new Deposito <> ();
    this.monVuelto = new Deposito <> ();

    /** rellenamos depositos con la cantidad 
    * corregido orden de parámetros (nombre ID precio cantidad)
    */
    for (int i = 0; i < cantidad; i++) {
        cocacola.add(new CocaCola("Coca Cola", 101, Seleccion.COCA_COLA.getPrecio(), 1));
        sprite.add(new Sprite("Sprite", 102, Seleccion.SPRITE.getPrecio(), 1));
        fanta.add(new Fanta("Fanta", 201, Seleccion.FANTA.getPrecio(), 1));
        snickers.add(new Snickers("Snickers", 202, Seleccion.SNICKERS.getPrecio(), 1));
        super8.add(new Super8("Super 8", 203, Seleccion.SUPER8.getPrecio(), 1));
    }
}
public Producto comprarProducto(Moneda m, Seleccion p)
    /**
    * se realiza la compra de algun producto
    * @param m la moneda que se entrega para hacer la compra
    * @param p tipo de producto seleccionado desde enum
    * @return producto si el pago y stock estan correctos
    * @throws PagoIncorrectoException si la moneda es invalido
    * @throws PagoInsuficienteException si la moneda es menor al precio
    * @throws NoHayProductoException si el deposito esta vacio
    */
        throws PagoIncorrectoException, PagoInsuficienteException, NoHayProductoException {

    /** exception 1 veemos si es valido el pago con PgoIncorrectoException */
    if (m == null) {
        throw new PagoIncorrectoException("No se ha ingresado una moneda.");
    }
     /** exception 2 verificamos que el dinero alcance para comprar con PagoInsuficienteException */
    if (m.getValor() < p.getPrecio()) {
        monVuelto.add(m); // devuelve la moneda al deposito de vuelto
        throw new PagoInsuficienteException("Dinero insuficiente para " + p.getNombre()); 
    }
    /** antes de hacer exception 3, hay que ver si queda del producto, viendo los depositos correspondiente */
    Producto prod = null;
    switch (p) {
        case COCA_COLA:
            prod = cocacola.get();  //corregido error "coca" en lugar de "cocacola"
            break;
        case SPRITE:
            prod = sprite.get();
            break;
        case FANTA:
            prod = fanta.get();
            break;
        case SNICKERS:
            prod = snickers.get();
            break;
        case SUPER8:
            prod = super8.get();
            break;
    }
    /** exception 3, si no quedan del producto, ocupamos NoHayProductoException */
    if (prod == null) {
        monVuelto.add(m); //** se devuelve la moneda */
        throw new NoHayProductoException("No queda stock de " + p.getNombre());
    }
    /** vuelto en moneda100 */
    int vuelto = m.getValor() - p.getPrecio();
    while (vuelto >= 100) {
        monVuelto.add(new Moneda100()); //faltaba ";"
        vuelto -= 100;
    }

    return prod;
}
public Moneda getVuelto(){
    return monVuelto.get();
    /** deja el vuelto en el deposito de una a una
    * @return modenas de $100 para el vuelto
    */
    }
public int getID(){
    return this.id;
    /**
    * obtenemos el id de la maquina
    * @return un int representando el id
    */
    }
}
    
    
  
