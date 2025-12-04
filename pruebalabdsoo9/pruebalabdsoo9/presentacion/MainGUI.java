package presentacion;

import gestor.GestorBanco;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación GUI del Sistema Bancario.
 * Implementa el patrón Singleton para el GestorBanco.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class MainGUI {
    
    // Instancia única del gestor (patrón Singleton)
    private static GestorBanco gestorBanco;
    
    /**
     * Obtiene la instancia única del GestorBanco.
     * Si no existe, la crea y la inicializa.
     * 
     * @return Instancia del GestorBanco
     */
    public static GestorBanco getGestorBanco() {
        if (gestorBanco == null) {
            gestorBanco = new GestorBanco();
            System.out.println("✅ GestorBanco inicializado correctamente");
        }
        return gestorBanco;
    }
    
    /**
     * Método main que inicia la interfaz gráfica.
     * Usa SwingUtilities para garantizar que la GUI se ejecute en el EDT.
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Establecer Look and Feel según el sistema operativo
                    javax.swing.UIManager.setLookAndFeel(
                        javax.swing.UIManager.getSystemLookAndFeelClassName()
                    );
                } catch (Exception e) {
                    System.err.println("⚠️ No se pudo aplicar el tema del sistema: " + e.getMessage());
                }
                
                // Mostrar ventana de login
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
                login.setLocationRelativeTo(null); // Centrar en pantalla
                
                System.out.println("🚀 Aplicación GUI iniciada correctamente");
            }
        });
    }
}