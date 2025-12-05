package presentacion.administrador;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import presentacion.MainGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class FrmMenuAdministrador extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    
    public FrmMenuAdministrador() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Menú Administrador - Sistema Bancario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));
    }
    
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setOpaque(false);
        
        // Título
        JLabel lblTitulo = new JLabel(" MENÚ ADMINISTRADOR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(41, 128, 185));
        panel.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Usuario: " + gestorUsuarios.getUsuarioActual().getNombreUsuario());
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        panel.add(lblSubtitulo);
        
        panel.add(Box.createVerticalStrut(30));
        
        // Botones del menú
        agregarBoton(panel, " Gestionar Usuarios", e -> abrirGestionUsuarios());
        agregarBoton(panel, " Gestionar Empleados", e -> abrirGestionEmpleados());
        agregarBoton(panel, " Gestión Completa del Sistema", e -> abrirGestionCompleta());
        agregarBoton(panel, " Reportes y Estadísticas", e -> abrirReportesAdmin());
        agregarBoton(panel, " Auditoría del Sistema", e -> abrirAuditoria());
        agregarBoton(panel, " Ver Todos los Permisos", e -> mostrarTodosPermisos());
        
        panel.add(Box.createVerticalGlue());
        
        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton(" Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.BLACK);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setMaximumSize(new Dimension(400, 50));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnCerrarSesion);
        
        add(panel);
    }
    
    private void agregarBoton(JPanel panel, String texto, ActionListener accion) {
        JButton btnCerrarSesion = new JButton(" Cerrar Sesión");
    btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnCerrarSesion.setBackground(new Color(231, 76, 60));
    btnCerrarSesion.setForeground(Color.DARK_GRAY); // Cambiar a blanco para mejor contraste
    btnCerrarSesion.setOpaque(true);
    btnCerrarSesion.setBorderPainted(false); // Agregar esta línea
    btnCerrarSesion.setFocusPainted(false); // Agregar esta línea
    btnCerrarSesion.setContentAreaFilled(true); // Agregar esta línea
    btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnCerrarSesion.setMaximumSize(new Dimension(400, 50));
    btnCerrarSesion.addActionListener(e -> cerrarSesion());
    btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
    panel.add(btnCerrarSesion);
    }
    
    private void abrirGestionUsuarios() {
       new FrmGestionUsuarios().setVisible(true);
    }
    
    private void abrirGestionEmpleados() {
  new FrmGestionEmpleados().setVisible(true);
    }
    
    private void abrirGestionCompleta() {
            new FrmGestionCompletaAdmin().setVisible(true);

    }
    
    private void abrirReportesAdmin() {
        new FrmReportesAdministrador().setVisible(true);
    }
    
    private void abrirAuditoria() {
           new FrmAuditoria().setVisible(true);

    }
    
    private void mostrarTodosPermisos() {
        StringBuilder permisos = new StringBuilder();
        permisos.append("═══════════════════════════════════════════════════════\n");
        permisos.append("         PERMISOS POR TIPO DE USUARIO\n");
        permisos.append("═══════════════════════════════════════════════════════\n\n");
        
        permisos.append(" ADMINISTRADOR:\n");
        permisos.append("• Acceso total al sistema\n");
        permisos.append("• Gestionar usuarios, empleados y clientes\n");
        permisos.append("• Auditoría y reportes completos\n");
        permisos.append("• Configuración del sistema\n\n");
        
        permisos.append(" EMPLEADO:\n");
        permisos.append("• Gestionar clientes y cuentas\n");
        permisos.append("• Procesar transacciones\n");
        permisos.append("• Consultar información del sistema\n");
        permisos.append("• Generar reportes básicos\n\n");
        
        permisos.append(" CLIENTE:\n");
        permisos.append("• Consultar saldo y movimientos\n");
        permisos.append("• Realizar depósitos, retiros y transferencias\n");
        permisos.append("• Ver información personal\n\n");
        
        permisos.append("═══════════════════════════════════════════════════════");
        
        JTextArea textArea = new JTextArea(permisos.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Permisos del Sistema", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Cierra sesión del administrador y vuelve al login.
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cerrar su sesión de administrador?\n\n" +
            "Se requerirá autenticación nuevamente para acceder al sistema.", 
            "Confirmar Cierre de Sesión", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorUsuarios.cerrarSesion();
            JOptionPane.showMessageDialog(this, 
                " Sesión de administrador cerrada exitosamente", 
                "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new presentacion.FrmLogin().setVisible(true);
        }
    }
}