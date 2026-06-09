package src.Monedas;

public abstract class Moneda implements Comparable<Moneda> {  
    /** clase que represneta una moneda en el sistema
    *se implementa comparable, así permitimos orednamiento según valor
    */
    private int valor;
    /** valor nominal de la moneda */
    private int serie;

    private static int contadorSeries = 1;
    public Moneda() {
        this.valor = 0;
        this.serie = contadorSeries++;
    }
    public Moneda(int valor){
        /** contructor para moneda class
        * @param valor int de la moneda (100,500,1000)
        */
        this.valor = valor;
        this.serie = contadorSeries++;
    }

    public int getValor() { return valor; }
    /** obtenemos el valor de la moneda
    * @return valor numerico de la moneda */
    public int getSerie() {
        return serie;
    }

    @Override
    public int compareTo(Moneda otra){ return Integer.compare(this.valor, otra.valor);}
    /** compra esta moneda con otra segun su valor
    * requisito para poder usar Collections.sort()
    * @return un num negativo, cero o int positivo si la moneda es <, = o > que la moneda especificada */

    @Override
    public String toString(){
        return "Moneda{valor=" + valor + ", serie=" + serie + "}";
        /** @return string con el valor y el hashcode como número de serie */
    }
}
