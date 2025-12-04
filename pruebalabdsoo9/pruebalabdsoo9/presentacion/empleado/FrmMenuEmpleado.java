package presentacion.empleado;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.personas.UsuarioEmpleado;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menú exclusivo para Empleados del Sistema Bancario.
 * Permite gestionar clientes, cuentas y procesar transacciones.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmMenuEmpleado extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    
    public FrmMenuEmpleado() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Menú Empleado - Sistema Bancario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 550);
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
        JLabel lblTitulo = new JLabel("👔 MENÚ EMPLEADO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
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
        agregarBoton(panel, "👥 Gestionar Clientes", e -> abrirGestionClientes());
        agregarBoton(panel, "🏦 Gestionar Cuentas", e -> abrirGestionCuentas());
        agregarBoton(panel, "💳 Procesar Transacciones", e -> abrirProcesarTransacciones());
        agregarBoton(panel, "🔍 Consultas del Sistema", e -> abrirConsultas());
        agregarBoton(panel, "📈 Reportes", e -> abrirReportes());
        agregarBoton(panel, "👤 Mi Información", e -> mostrarMiInformacion());
        
        panel.add(Box.createVerticalGlue());
        
        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("🔒 Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setMaximumSize(new Dimension(400, 50));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnCerrarSesion);
        
        add(panel);
    }
    
    private void agregarBoton(JPanel panel, String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(450, 55));
        boton.addActionListener(accion);
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panel.add(boton);
        panel.add(Box.createVerticalStrut(12));
    }
    
    private void abrirGestionClientes() {
        FrmGestionClientes frm = new FrmGestionClientes();
        frm.setVisible(true);
        frm.setLocationRelativeTo(this);
    }
    
    private void abrirGestionCuentas() {
        FrmGestionCuentas frm = new FrmGestionCuentas();
        frm.setVisible(true);
        frm.setLocationRelativeTo(this);
    }
    
    private void abrirProcesarTransacciones() {
        FrmProcesarTransacciones frm = new FrmProcesarTransacciones();
        frm.setVisible(true);
        frm.setLocationRelativeTo(this);
    }
    
    private void abrirConsultas() {
        FrmConsultasEmpleado frm = new FrmConsultasEmpleado();
        frm.setVisible(true);
        frm.setLocationRelativeTo(this);
    }
    
    private void abrirReportes() {
        // Reporte en texto
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("═══════════════════════════════════════════════════════\n");
        reporte.append("         REPORTES EMPLEADO\n");
        reporte.append("═══════════════════════════════════════════════════════\n\n");
        reporte.append(String.format("Fecha: %s\n\n", new java.util.Date()));
        reporte.append(String.format("Total de Cuentas:      %d\n", gestorBanco.getCuentas().size()));
        reporte.append(String.format("Total de Transacciones: %d\n", gestorBanco.getTransacciones().size()));
        reporte.append(String.format("Total de Usuarios:      %d\n", gestorUsuarios.getUsuarios().size()));
        
        textArea.setText(reporte.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Reportes Empleado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarMiInformacion() {
        UsuarioEmpleado usuarioActual = (UsuarioEmpleado) gestorUsuarios.getUsuarioActual();
        if (usuarioActual != null && usuarioActual.getEmpleado() != null) {
            StringBuilder info = new StringBuilder();
            info.append("═══════════════════════════════════════════════════════\n");
            info.append("         MI INFORMACIÓN DE EMPLEADO\n");
            info.append("═══════════════════════════════════════════════════════\n\n");
            info.append(String.format("Usuario:     %s\n", usuarioActual.getNombreUsuario()));
            info.append(String.format("Nombre:      %s\n", usuarioActual.getEmpleado().getNombreCompleto()));
            info.append(String.format("DNI:         %s\n", usuarioActual.getEmpleado().getDni()));
            info.append(String.format("Cargo:       %s\n", usuarioActual.getEmpleado().getCargo()));
            info.append(String.format("Departamento: %s\n", usuarioActual.getEmpleado().getDepartamento()));
            info.append(String.format("Salario:     $%,.2f\n", usuarioActual.getEmpleado().getSalario()));
            info.append(String.format("Estado:      %s\n", usuarioActual.getEmpleado().isActivo() ? "Activo" : "Inactivo"));
            
            JTextArea textArea = new JTextArea(info.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 350));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Mi Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cerrar su sesión de empleado?", 
            "Confirmar Cierre de Sesión", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorUsuarios.cerrarSesion();
            JOptionPane.showMessageDialog(this, 
                "✅ Sesión de empleado cerrada exitosamente", 
                "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new presentacion.FrmLogin().setVisible(true);
        }
    }
}