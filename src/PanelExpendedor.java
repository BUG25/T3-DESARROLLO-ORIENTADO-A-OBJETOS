package vista;

import modelo.Maquina;
import modelo.Seleccion;
import modelo.depositos.Deposito;
import modelo.excepciones.*;
import modelo.monedas.Moneda;
import modelo.productos.Producto;
import modelo.productos.bebidas.Bebida;
import modelo.productos.dulces.Dulce;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Vista y controlador del expendedor.
 * Se dibuja como un rectángulo con depósitos visibles y maneja clicks del mouse.
 */
public class PanelExpendedor {

    // Posición y tamaño en el panel principal
    private int x, y;
    private final int ancho = 420;
    private final int alto = 560;

    private final Maquina maquina;

    // Colores por producto
    private static final Map<Seleccion, Color> COLORES = new LinkedHashMap<>();
    static {
        COLORES.put(Seleccion.COCA_COLA, new Color(180, 30, 30));
        COLORES.put(Seleccion.SPRITE,    new Color(30, 160, 60));
        COLORES.put(Seleccion.FANTA,     new Color(230, 120, 20));
        COLORES.put(Seleccion.SNICKERS,  new Color(120, 80, 30));
        COLORES.put(Seleccion.SUPER8,    new Color(60, 100, 200));
    }

    // Columnas de productos: x relativo, seleccion
    private final Seleccion[] orden = {
        Seleccion.COCA_COLA, Seleccion.SPRITE, Seleccion.FANTA,
        Seleccion.SNICKERS, Seleccion.SUPER8
    };

    // Posiciones relativas de las columnas
    private final int[] colX = {15, 95, 175, 255, 335};
    private final int colY = 70;
    private final int colAncho = 65;
    private final int colAlto = 280;

    // Zona de producto entregado
    private final int entregaX = 15;
    private final int entregaY = 390;
    private final int entregaAncho = 80;
    private final int entregaAlto = 60;

    // Zona de vuelto
    private final int vueltoX = 110;
    private final int vueltoY = 390;
    private final int vueltoAncho = 290;
    private final int vueltoAlto = 60;

    // Mensaje de estado
    private String mensajeEstado = "Bienvenido";

    /**
     * Constructor del panel del expendedor.
     * @param x coordenada X en el panel principal
     * @param y coordenada Y en el panel principal
     * @param maquina modelo lógico
     */
    public PanelExpendedor(int x, int y, Maquina maquina) {
        this.x = x;
        this.y = y;
        this.maquina = maquina;
    }

    /**
     * Dibuja el expendedor completo: cuerpo, cristal, depósitos, zonas de entrega y vuelto.
     * @param g contexto gráfico
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cuerpo de la máquina
        g2.setColor(new Color(60, 60, 70));
        g2.fillRoundRect(x, y, ancho, alto, 18, 18);
        g2.setColor(new Color(100, 100, 120));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, ancho, alto, 18, 18);

        // Título
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("EXPENDEDOR  #" + maquina.getId(), x + 100, y + 22);

        // Cristal / ventana interior
        g2.setColor(new Color(200, 230, 255, 120));
        g2.fillRoundRect(x + 10, y + 35, ancho - 20, colAlto + colY - 20, 10, 10);
        g2.setColor(new Color(150, 200, 255, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x + 10, y + 35, ancho - 20, colAlto + colY - 20, 10, 10);

        // Depósitos de productos
        Deposito<?>[] depositos = {
            maquina.getDepositoCocaCola(), maquina.getDepositoSprite(),
            maquina.getDepositoFanta(), maquina.getDepositoSnickers(),
            maquina.getDepositoSuper8()
        };

        for (int i = 0; i < orden.length; i++) {
            dibujarDepositoProducto(g2, colX[i], colY, colAncho, colAlto, orden[i], depositos[i]);
        }

        // Zona de producto entregado
        dibujarZonaEntrega(g2);

        // Zona de vuelto
        dibujarZonaVuelto(g2);

        // Mensaje de estado
        g2.setColor(new Color(255, 230, 100));
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.drawString(mensajeEstado, x + 15, y + alto - 10);
    }

    /**
     * Dibuja un depósito de productos con sus productos visibles.
     */
    private void dibujarDepositoProducto(Graphics2D g2, int rx, int ry, int rw, int rh,
                                          Seleccion sel, Deposito<?> deposito) {
        int ax = x + rx;
        int ay = y + ry;

        // Fondo del depósito
        g2.setColor(new Color(40, 40, 50, 200));
        g2.fillRoundRect(ax, ay, rw, rh, 6, 6);
        g2.setColor(new Color(80, 80, 100));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(ax, ay, rw, rh, 6, 6);

        // Nombre del producto (encabezado)
        g2.setColor(COLORES.get(sel));
        g2.setFont(new Font("Arial", Font.BOLD, 9));
        String[] palabras = sel.getNombre().split(" ");
        g2.drawString(palabras[0], ax + 3, ay + 12);
        if (palabras.length > 1) g2.drawString(palabras[1], ax + 3, ay + 22);

        // Precio
        g2.setColor(new Color(200, 255, 200));
        g2.setFont(new Font("Arial", Font.PLAIN, 8));
        g2.drawString("$" + sel.getPrecio(), ax + 3, ay + 32);

        // Productos como rectángulos verticales
        ArrayList<?> lista = deposito.getLista();
        int itemAlto = 26;
        int itemAncho = rw - 10;
        int startY = ay + rh - 8;
        int maxItems = Math.min(lista.size(), 8);

        for (int i = 0; i < maxItems; i++) {
            int iy = startY - (i + 1) * (itemAlto + 2);
            g2.setColor(COLORES.get(sel));
            g2.fillRoundRect(ax + 5, iy, itemAncho, itemAlto, 4, 4);
            g2.setColor(COLORES.get(sel).brighter());
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(ax + 5, iy, itemAncho, itemAlto, 4, 4);
            // número de serie mini
            Object prod = lista.get(lista.size() - 1 - i);
            if (prod instanceof Producto) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 7));
                g2.drawString("#" + ((Producto) prod).getNumSerie(), ax + 7, iy + 17);
            }
        }

        // Contador de stock
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 9));
        g2.drawString("x" + lista.size(), ax + rw - 18, ay + 12);
    }

    /**
     * Dibuja la zona de producto entregado (depósito de 1 elemento).
     */
    private void dibujarZonaEntrega(Graphics2D g2) {
        int ax = x + entregaX;
        int ay = y + entregaY;

        g2.setColor(new Color(50, 50, 60));
        g2.fillRoundRect(ax, ay, entregaAncho, entregaAlto, 8, 8);
        g2.setColor(new Color(100, 200, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(ax, ay, entregaAncho, entregaAlto, 8, 8);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 9));
        g2.drawString("PRODUCTO", ax + 5, ay + 12);

        Producto p = maquina.getProductoEntregado();
        if (p != null) {
            // buscar color del producto
            Color col = Color.GRAY;
            for (Seleccion s : Seleccion.values()) {
                if (s.getNombre().equals(p.getNombre())) { col = COLORES.get(s); break; }
            }
            g2.setColor(col);
            g2.fillRoundRect(ax + 8, ay + 18, entregaAncho - 16, 32, 6, 6);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 8));
            g2.drawString(p.getNombre(), ax + 10, ay + 32);
            g2.drawString("#" + p.getNumSerie(), ax + 10, ay + 44);
        } else {
            g2.setColor(new Color(80, 80, 90));
            g2.fillRoundRect(ax + 8, ay + 18, entregaAncho - 16, 32, 6, 6);
            g2.setColor(new Color(150, 150, 150));
            g2.setFont(new Font("Arial", Font.ITALIC, 9));
            g2.drawString("vacío", ax + 18, ay + 38);
        }
    }

    /**
     * Dibuja la zona de vuelto mostrando las monedas disponibles.
     */
    private void dibujarZonaVuelto(Graphics2D g2) {
        int ax = x + vueltoX;
        int ay = y + vueltoY;

        g2.setColor(new Color(50, 50, 60));
        g2.fillRoundRect(ax, ay, vueltoAncho, vueltoAlto, 8, 8);
        g2.setColor(new Color(255, 215, 0));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(ax, ay, vueltoAncho, vueltoAlto, 8, 8);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 9));
        g2.drawString("VUELTO", ax + 5, ay + 12);

        // Mostrar monedas como círculos
        ArrayList<Moneda> monedas = maquina.getDepositoVuelto().getLista();
        int cx = ax + 8;
        int cy = ay + 20;
        int radio = 12;
        for (int i = 0; i < Math.min(monedas.size(), 18); i++) {
            Moneda m = monedas.get(i);
            Color cm = colorMoneda(m.getValor());
            g2.setColor(cm);
            g2.fillOval(cx, cy, radio * 2, radio * 2);
            g2.setColor(cm.darker());
            g2.setStroke(new BasicStroke(1));
            g2.drawOval(cx, cy, radio * 2, radio * 2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 7));
            String label = m.getValor() == 100 ? "1c" : (m.getValor() == 500 ? "5c" : "1k");
            g2.drawString(label, cx + 3, cy + 16);
            cx += radio * 2 + 3;
            if (cx > ax + vueltoAncho - 30) { cx = ax + 8; cy += radio * 2 + 3; }
        }
        if (monedas.size() > 18) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.PLAIN, 8));
            g2.drawString("+" + (monedas.size() - 18) + " más", ax + vueltoAncho - 40, ay + vueltoAlto - 5);
        }
    }

    /**
     * Maneja el click del mouse recibido desde PanelPrincipal.
     * Si el click fue dentro del expendedor, rellenará los depósitos vacíos.
     * @param e evento de mouse
     * @return true si el click fue procesado por el expendedor
     */
    public boolean handleMouseClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= x && mx <= x + ancho && my >= y && my <= y + alto) {
            // Rellenar depósitos vacíos
            boolean rellenado = false;
            Deposito<?>[] depositos = {
                maquina.getDepositoCocaCola(), maquina.getDepositoSprite(),
                maquina.getDepositoFanta(), maquina.getDepositoSnickers(),
                maquina.getDepositoSuper8()
            };
            for (Deposito<?> d : depositos) {
                if (d.isEmpty()) { rellenado = true; break; }
            }
            if (rellenado) {
                maquina.rellenarDepositos();
                mensajeEstado = "Depósitos rellenados";
            } else {
                mensajeEstado = "Todos los depósitos tienen stock";
            }
            return true;
        }
        return false;
    }

    /** @return color de una moneda según su valor */
    private Color colorMoneda(int valor) {
        if (valor == 100)  return new Color(180, 140, 60);
        if (valor == 500)  return new Color(160, 160, 170);
        return new Color(220, 190, 80); // 1000
    }

    /** Fija el mensaje de estado visible en la parte inferior. */
    public void setMensajeEstado(String msg) { this.mensajeEstado = msg; }

    /** @return posición X */
    public int getX() { return x; }
    /** @return posición Y */
    public int getY() { return y; }
    /** @return ancho */
    public int getAncho() { return ancho; }
    /** @return alto */
    public int getAlto() { return alto; }
}