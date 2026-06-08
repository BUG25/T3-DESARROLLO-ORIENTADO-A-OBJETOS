package src.vista;

import src.Maquina;
import src.Seleccion;
import src.Monedas.Moneda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPrincipal extends JPanel {//se ve en el centro de la ventana
    private PanelComprador comprador;
    private PanelExpendedor expendedor;
    private Maquina maquina;
    public PanelPrincipal () {
        this.maquina = new Maquina(1);
        this.expendedor = new PanelExpendedor (50, 50, maquina);
        this.comprador = new PanelComprador(500, 50, maquina);

        comprador.setPanelExpendedor(expendedor);

        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(850, 650));
        agregarMouseListener();
    }
    private void agregarMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (comprador.handleMouseClick(e)) {
                    repaint();
                    return;
                }

                if (expendedor.handleMouseClick(e)) {
                    repaint();
                }
            }
        });
    }
    /**
     * Dibuja todos los componentes del panel.
     * @param g contexto gráfico
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Limpia el fondo

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar línea divisoria entre ambos paneles (opcional)
        g2.setColor(new Color(100, 100, 120));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(480, 30, 480, getHeight() - 30);

        // Dibujar los paneles secundarios
        comprador.paintComponent(g);
        expendedor.paintComponent(g);

        // Título general (opcional)
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("MÁQUINA EXPENDEDORA", getWidth() / 2 - 100, 25);
    }
    /**
     * Método para actualizar la interfaz después de cambios externos.
     */
    public void actualizarVista() {
        repaint();
    }

    public PanelComprador getComprador() { return comprador; }
    public PanelExpendedor getExpendedor() { return expendedor; }
    public Maquina getMaquina() { return maquina; }
}
