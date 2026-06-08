package src.vista;

import src.Maquina;
import src.Seleccion;
import src.Excepciones.*;
import src.Monedas.*;
import src.Productos.Producto;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista y controlador del comprador.
 * Se dibuja con zonas clicables para elegir producto y tipo de moneda.
 * Gestiona el saldo, historial de compras y recolección de vuelto/producto.
 */
public class PanelComprador {

    private int x, y;
    private final int ancho = 300;
    private final int alto = 560;

    private final Maquina maquina;

    // Estado del comprador
    private int saldo;
    private final List<Moneda> monedero;
    private final List<Producto> productosObtenidos;
    private final List<String> historial;
    private Seleccion seleccionActual;
    private Moneda monedaSeleccionada;
    private String mensajeEstado;

    // Zonas de selección de producto (relativas al panel)
    private static final int ZONA_PROD_X = 10;
    private static final int ZONA_PROD_Y = 50;
    private static final int ZONA_PROD_ANCHO = 280;
    private static final int ZONA_PROD_ALTO = 30;
    private static final int ZONA_PROD_GAP = 6;

    // Zonas de monedas
    private static final int ZONA_MON_Y = 240;
    private static final int ZONA_MON_ANCHO = 80;
    private static final int ZONA_MON_ALTO = 40;

    // Colores de zonas de productos
    private static final Color[] COLORES_PROD = {
        new Color(180, 30, 30),
        new Color(30, 160, 60),
        new Color(230, 120, 20),
        new Color(120, 80, 30),
        new Color(60, 100, 200)
    };

    /**
     * Constructor del comprador.
     * @param x posición X
     * @param y posición Y
     * @param maquina referencia al modelo
     */
    public PanelComprador(int x, int y, Maquina maquina) {
        this.x = x;
        this.y = y;
        this.maquina = maquina;
        this.saldo = 5000;
        this.monedero = new ArrayList<>();
        this.productosObtenidos = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.mensajeEstado = "Selecciona producto y moneda";
        inicializarMonedero();
    }

    /** Agrega monedas iniciales al monedero. */
    private void inicializarMonedero() {
        for (int i = 0; i < 3; i++) monedero.add(new Moneda1000());
        for (int i = 0; i < 4; i++) monedero.add(new Moneda500());
        for (int i = 0; i < 5; i++) monedero.add(new Moneda100());
    }

    /**
     * Dibuja el comprador: cuerpo, zonas de selección, monedero, historial.
     * @param g contexto gráfico
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cuerpo
        g2.setColor(new Color(40, 50, 70));
        g2.fillRoundRect(x, y, ancho, alto, 18, 18);
        g2.setColor(new Color(80, 100, 140));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, ancho, alto, 18, 18);

        // Título y saldo
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.drawString("COMPRADOR", x + 10, y + 20);
        g2.setColor(new Color(100, 255, 150));
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Saldo: $" + saldo, x + 160, y + 20);

        // SMILEY:)
        int smileX = x + ancho - 55;
        int smileY = y + 10;
        g2.setColor(new Color(255, 220, 0));
        g2.fillOval(smileX, smileY, 40, 40);
        g2.setColor(Color.BLACK);
        g2.drawOval(smileX, smileY, 40, 40);
        g2.fillOval(smileX + 10, smileY + 12, 6, 6);
        g2.fillOval(smileX + 24, smileY + 12, 6, 6);
        g2.drawArc(smileX + 10, smileY + 15, 20, 18, 180, 180);

        // Etiqueta sección productos
        g2.setColor(new Color(200, 200, 255));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("1. ELIGE TU PRODUCTO:", x + 10, y + 42);

        // Zonas de productos
        Seleccion[] sels = Seleccion.values();
        for (int i = 0; i < sels.length; i++) {
            int zy = y + ZONA_PROD_Y + i * (ZONA_PROD_ALTO + ZONA_PROD_GAP);
            boolean seleccionado = (seleccionActual == sels[i]);
            Color base = COLORES_PROD[i];
            g2.setColor(seleccionado ? base.brighter() : base.darker());
            g2.fillRoundRect(x + ZONA_PROD_X, zy, ZONA_PROD_ANCHO, ZONA_PROD_ALTO, 8, 8);
            if (seleccionado) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(x + ZONA_PROD_X, zy, ZONA_PROD_ANCHO, ZONA_PROD_ALTO, 8, 8);
            }
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.drawString(sels[i].getNombre() + "  —  $" + sels[i].getPrecio(), x + ZONA_PROD_X + 10, zy + 20);
        }

        // Sección monedas
        g2.setColor(new Color(200, 200, 255));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("2. ELIGE TU MONEDA:", x + 10, y + ZONA_MON_Y - 8);

        int[] valores = {100, 500, 1000};
        String[] etiquetas = {"$100", "$500", "$1000"};
        Color[] colMon = {new Color(160, 120, 40), new Color(150, 150, 160), new Color(210, 180, 60)};
        for (int i = 0; i < 3; i++) {
            int mx = x + 10 + i * (ZONA_MON_ANCHO + 10);
            int my = y + ZONA_MON_Y;
            boolean sel = (monedaSeleccionada != null && monedaSeleccionada.getValor() == valores[i]);
            g2.setColor(sel ? colMon[i].brighter() : colMon[i].darker());
            g2.fillOval(mx, my, ZONA_MON_ANCHO, ZONA_MON_ALTO);
            if (sel) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(mx, my, ZONA_MON_ANCHO, ZONA_MON_ALTO);
            }
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.drawString(etiquetas[i], mx + (i == 2 ? 8 : 15), my + 24);
        }

        // Botón COMPRAR
        int btnX = x + 10;
        int btnY = y + ZONA_MON_Y + 55;
        g2.setColor(new Color(40, 180, 80));
        g2.fillRoundRect(btnX, btnY, 130, 36, 10, 10);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.drawString("COMPRAR", btnX + 22, btnY + 23);

        // Botón TOMAR VUELTO
        int btnVX = x + 155;
        g2.setColor(new Color(200, 160, 30));
        g2.fillRoundRect(btnVX, btnY, 135, 36, 10, 10);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 11));
        g2.drawString("TOMAR VUELTO", btnVX + 8, btnY + 23);

        // Mensaje de estado
        g2.setColor(new Color(255, 230, 100));
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.drawString(mensajeEstado, x + 10, btnY + 50);

        // Historial
        g2.setColor(new Color(180, 200, 255));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("HISTORIAL:", x + 10, btnY + 70);
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        g2.setColor(new Color(200, 200, 220));
        int maxHistorial = Math.min(historial.size(), 7);
        for (int i = 0; i < maxHistorial; i++) {
            g2.drawString(historial.get(historial.size() - 1 - i), x + 10, btnY + 85 + i * 14);
        }

        // Monedero
        g2.setColor(new Color(180, 200, 255));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("MONEDERO:", x + 10, y + alto - 55);
        int cx2 = x + 10;
        int cy2 = y + alto - 40;
        for (int i = 0; i < Math.min(monedero.size(), 20); i++) {
            Moneda m = monedero.get(i);
            Color cm = colorMoneda(m.getValor());
            g2.setColor(cm);
            g2.fillOval(cx2, cy2, 18, 18);
            g2.setColor(cm.darker());
            g2.drawOval(cx2, cy2, 18, 18);
            cx2 += 20;
            if (cx2 > x + ancho - 25) { cx2 = x + 10; cy2 += 20; }
        }
        if (monedero.size() > 20) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.PLAIN, 8));
            g2.drawString("+" + (monedero.size() - 20), x + ancho - 30, y + alto - 15);
        }
    }

    /**
     * Maneja el click del mouse. Detecta zona de producto, moneda, botón comprar o tomar vuelto.
     * @param e evento de mouse
     * @return true si el click fue procesado
     */
    public boolean handleMouseClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx < x || mx > x + ancho || my < y || my > y + alto) return false;

        // Zonas de productos
        Seleccion[] sels = Seleccion.values();
        for (int i = 0; i < sels.length; i++) {
            int zy = y + ZONA_PROD_Y + i * (ZONA_PROD_ALTO + ZONA_PROD_GAP);
            if (mx >= x + ZONA_PROD_X && mx <= x + ZONA_PROD_X + ZONA_PROD_ANCHO
                    && my >= zy && my <= zy + ZONA_PROD_ALTO) {
                seleccionActual = sels[i];
                mensajeEstado = "Seleccionado: " + sels[i].getNombre();
                return true;
            }
        }

        // Zonas de monedas
        int[] valores = {100, 500, 1000};
        for (int i = 0; i < 3; i++) {
            int zmx = x + 10 + i * (ZONA_MON_ANCHO + 10);
            int zmy = y + ZONA_MON_Y;
            if (mx >= zmx && mx <= zmx + ZONA_MON_ANCHO && my >= zmy && my <= zmy + ZONA_MON_ALTO) {
                monedaSeleccionada = crearMoneda(valores[i]);
                mensajeEstado = "Moneda seleccionada: $" + valores[i];
                return true;
            }
        }

        // Botón COMPRAR
        int btnY = y + ZONA_MON_Y + 55;
        if (mx >= x + 10 && mx <= x + 140 && my >= btnY && my <= btnY + 36) {
            realizarCompra();
            return true;
        }

        // Botón TOMAR VUELTO
        if (mx >= x + 155 && mx <= x + 290 && my >= btnY && my <= btnY + 36) {
            tomarVuelto();
            return true;
        }

        return true;
    }

    /** Ejecuta la lógica de compra usando el modelo. */
    private void realizarCompra() {
        if (seleccionActual == null) { mensajeEstado = "Elige un producto primero"; return; }
        if (monedaSeleccionada == null) { mensajeEstado = "Elige una moneda primero"; return; }
        if (saldo < monedaSeleccionada.getValor()) { mensajeEstado = "Sin saldo suficiente"; return; }

        saldo -= monedaSeleccionada.getValor();
        try {
            Producto p = maquina.comprarProducto(monedaSeleccionada, seleccionActual);
            if (p != null) {
                productosObtenidos.add(p);
                historial.add("✓ " + p.getNombre() + " #" + p.getID());
                mensajeEstado = "¡Disfruta tu " + p.getNombre() + "!";
            }
        } catch (PagoIncorrectoException | PagoInsuficienteException | NoHayProductoException ex) {
            historial.add("✗ " + ex.getMessage());
            mensajeEstado = ex.getMessage();
            // recuperar moneda devuelta al vuelto
            Moneda m = maquina.getVuelto();
            while (m != null) { monedero.add(m); saldo += m.getValor(); m = maquina.getVuelto(); }
        }
        monedaSeleccionada = null;
    }

    /** Recoge todas las monedas de vuelto de la máquina y las agrega al monedero. */
    private void tomarVuelto() {
        Moneda m = maquina.getVuelto();
        int total = 0;
        while (m != null) {
            monedero.add(m);
            saldo += m.getValor();
            total += m.getValor();
            m = maquina.getVuelto();
        }
        if (total > 0) {
            historial.add("← Vuelto: $" + total);
            mensajeEstado = "Vuelto recogido: $" + total;
        } else {
            mensajeEstado = "No hay vuelto pendiente";
        }
    }

    /** Crea una moneda del valor indicado. */
    private Moneda crearMoneda(int valor) {
        if (valor == 100)  return new Moneda100();
        if (valor == 500)  return new Moneda500();
        return new Moneda1000();
    }

    /** @return color según valor de moneda */
    private Color colorMoneda(int valor) {
        if (valor == 100)  return new Color(160, 120, 40);
        if (valor == 500)  return new Color(150, 150, 160);
        return new Color(210, 180, 60);
    }
}