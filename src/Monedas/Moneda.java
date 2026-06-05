package src.Monedas;

public abstract class Moneda implements Comparable<Moneda> {  
    /** clase que represneta una moneda en el sistema
    *se implementa comparable, así permitimos orednamiento según valor
    */
    private int valor;
    /** valor nominal de la moneda */
    public Moneda(int valor){
        /** contructor para moneda class
        * @param valor int de la moneda (100,500,1000)
        */
        this.valor = valor;
    }

    public int getValor() { return valor; }
    /** obtenemos el valor de la moneda
    * @return valor numerico de la moneda */
    @Override
    public int compareTo(Moneda otra){ return Integer.compare(this.valor, otra.valor);}
    /** compra esta moneda con otra segun su valor
    * requisito para poder usar Collections.sort()
    * @param la moneda con la que se intenta comprar
    * @return un num negativo, cero o int positivo si la moneda es <, = o > que la moneda especificada */

    @Override
    public String toString(){
        return "Valor moneda =" + valor + ", n°Serie=" + this.hashCode();
        /** @return string con el valor y el hashcode como número de serie */
    }
}
