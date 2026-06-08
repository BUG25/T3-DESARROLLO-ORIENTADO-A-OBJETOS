package src.vista;
import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Ventana principal
 * configura el marco de la expendedora y añade el panel central
 */

public class Ventana extends JFrame{
    public Ventana() {
        /** Titulo ventana grafica
         * definimos el titulo, cierre de la ventana, añade panel principal
         * y hace visble la interfaz
         */
        super("Expendedor - Tarea 3");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        PanelPrincipal panelCentral = new PanelPrincipal();
        this.add(panelCentral, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * metodo main que inicia el programa
     * @param args argumentos del sistema
     */
    public static void main(String[] args){
        new Ventana();
    }

}
