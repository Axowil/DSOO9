package presentacion.cliente;
import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.cuentas.Cuenta;
import modelo.personas.UsuarioCliente;
import presentacion.MainGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menú exclusivo para Clientes del Sistema Bancario.
 * Proporciona acceso a todas las operaciones bancarias disponibles.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmMenuCliente extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    
    public FrmMenuCliente() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Menú Cliente - Sistema Bancario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue
    }
    
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setOpaque(false);
        
        // Título
              JLabel lblTitulo = new JLabel("🏦 MENÚ CLIENTE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(41, 128, 185));
        panel.add(lblTitulo);
        
        // Evitar NullPointerException si no hay usuario actualmente logueado
        String nombreUsuario = "(no conectado)";
        var usrActual = gestorUsuarios.getUsuarioActual();
        if (usrActual instanceof UsuarioCliente) {
            UsuarioCliente uc = (UsuarioCliente) usrActual;
            if (uc.getNombreUsuario() != null && !uc.getNombreUsuario().isBlank()) {
                nombreUsuario = uc.getNombreUsuario();
            }
        } else if (usrActual != null && usrActual.getNombreUsuario() != null) {
            nombreUsuario = usrActual.getNombreUsuario();
        }
        JLabel lblSubtitulo = new JLabel("Usuario: " + nombreUsuario);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setForeground(Color.DARK_GRAY);
        panel.add(lblSubtitulo);
        
        panel.add(Box.createVerticalStrut(30));
        
        // Botones del menú - AQUI ESTÁN LOS CAMBIOS REALES
        agregarBoton(panel, "💰 Consultar Saldo", e -> abrirConsultaSaldo());
        agregarBoton(panel, "💵 Realizar Depósito", e -> abrirDeposito());
        agregarBoton(panel, "💸 Realizar Retiro", e -> abrirRetiro());
        agregarBoton(panel, "📤 Realizar Transferencia", e -> abrirTransferencia());
        agregarBoton(panel, "📊 Ver Movimientos", e -> abrirMovimientos());
        agregarBoton(panel, "👤 Ver Mis Cuentas", e -> mostrarMisCuentas());
        agregarBoton(panel, "👤 Ver Mi Información", e -> mostrarMiInformacion());
        
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
        boton.setToolTipText(texto);
        
        panel.add(boton);
        panel.add(Box.createVerticalStrut(12));
    }
    
    // MÉTODOS CORREGIDOS - ESTOS SON LOS QUE FUNCIONAN
private void abrirConsultaSaldo() {
    FrmConsultaSaldo frm = new FrmConsultaSaldo();
    frm.setVisible(true);
    frm.setLocationRelativeTo(this);
}
    
private void abrirDeposito() {
    FrmDeposito frm = new FrmDeposito();
    frm.setVisible(true);
    frm.setLocationRelativeTo(this);
}
    
private void abrirRetiro() {
    FrmRetiro frm = new FrmRetiro();
    frm.setVisible(true);
    frm.setLocationRelativeTo(this);
}
    
private void abrirTransferencia() {
    FrmTransferenciaCliente frm = new FrmTransferenciaCliente();
    frm.setVisible(true);
    frm.setLocationRelativeTo(this);
}
    
private void abrirMovimientos() {
    FrmMovimientos frm = new FrmMovimientos();
    frm.setVisible(true);
    frm.setLocationRelativeTo(this);
}
    
    private void mostrarMisCuentas() {
        UsuarioCliente usuarioActual = (UsuarioCliente) gestorUsuarios.getUsuarioActual();
        if (usuarioActual != null && usuarioActual.getCliente() != null) {
            StringBuilder info = new StringBuilder();
            info.append("═══════════════════════════════════════════════════════\n");
            info.append("                  MIS CUENTAS BANCARIAS\n");
            info.append("═══════════════════════════════════════════════════════\n\n");
            info.append(String.format("Cliente: %s\n", usuarioActual.getCliente().getNombreCompleto()));
            info.append(String.format("ID: %s\n", usuarioActual.getCliente().getIdCliente()));
            info.append(String.format("Categoría: %s\n\n", usuarioActual.getCliente().getCategoria()));
            
            var cuentas = usuarioActual.getCliente().getCuentasAsociadas();
            if (cuentas.isEmpty()) {
                info.append("No tiene cuentas asociadas.\n");
            } else {
                info.append("Cuentas Asociadas:\n");
                for (String numCuenta : cuentas) {
                    Cuenta cuenta = gestorBanco.buscarCuenta(numCuenta);
                    if (cuenta != null) {
                        info.append(String.format("  • %s [%s] - Saldo: $%,.2f - %s\n", 
                            numCuenta, 
                            cuenta.getClass().getSimpleName(),
                            cuenta.getSaldo(),
                            cuenta.getEstado()
                        ));
                    }
                }
            }
            
            info.append("\n═══════════════════════════════════════════════════════");
            
            JTextArea textArea = new JTextArea(info.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(650, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Mis Cuentas - " + usuarioActual.getCliente().getNombreCompleto(), 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mostrarMiInformacion() {
        UsuarioCliente usuarioActual = (UsuarioCliente) gestorUsuarios.getUsuarioActual();
        if (usuarioActual != null) {
            StringBuilder info = new StringBuilder();
            info.append("═══════════════════════════════════════════════════════\n");
            info.append("              MI INFORMACIÓN PERSONAL\n");
            info.append("═══════════════════════════════════════════════════════\n\n");
            
            info.append("[USUARIO]\n");
            info.append(String.format("Usuario:      %s\n", usuarioActual.getNombreUsuario()));
            info.append(String.format("Tipo:         %s\n", usuarioActual.getClass().getSimpleName()));
            info.append(String.format("Estado:       %s\n\n", usuarioActual.isEstado() ? "ACTIVO" : "INACTIVO"));
            
            if (usuarioActual.getCliente() != null) {
                var cliente = usuarioActual.getCliente();
                info.append("[CLIENTE ASOCIADO]\n");
                info.append(String.format("ID Cliente:   %s\n", cliente.getIdCliente()));
                info.append(String.format("DNI:          %s\n", cliente.getDni()));
                info.append(String.format("Nombre:       %s\n", cliente.getNombreCompleto()));
                info.append(String.format("Email:        %s\n", cliente.getEmail()));
                info.append(String.format("Teléfono:     %s\n", cliente.getTelefono()));
                info.append(String.format("Categoría:    %s\n", cliente.getCategoria()));
                info.append(String.format("Límite:       $%,.2f\n", cliente.getLimiteCredito()));
                info.append(String.format("Cuentas:      %d asociadas\n", cliente.getCuentasAsociadas().size()));
            }
            
            info.append("\n═══════════════════════════════════════════════════════");
            
            JTextArea textArea = new JTextArea(info.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 350));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Mi Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cerrar su sesión?", 
            "Confirmar Cierre de Sesión", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestorUsuarios.cerrarSesion();
            JOptionPane.showMessageDialog(this, 
                "✅ Sesión cerrada exitosamente", 
                "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new presentacion.FrmLogin().setVisible(true);
        }
    }
}