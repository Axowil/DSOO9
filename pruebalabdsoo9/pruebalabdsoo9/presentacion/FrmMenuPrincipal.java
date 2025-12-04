package presentacion;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.cuentas.Cuenta;
import modelo.personas.Usuario;
import modelo.personas.UsuarioCliente;
import presentacion.cliente.FrmConsultaSaldo;
import presentacion.cliente.FrmDeposito;
import presentacion.cliente.FrmMovimientos;
import presentacion.cliente.FrmRetiro;
import presentacion.cliente.FrmTransferenciaCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Menú principal del sistema bancario.
 * Muestra opciones dinámicamente según el rol del usuario autenticado.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {
    
    private String tipoUsuario;
    private GestorBanco gestorBanco;
    private GestorUsuarios gestorUsuarios;
    
    /**
     * Constructor que inicializa el menú según el rol del usuario.
     * 
     * @param tipoUsuario Rol del usuario: CLIENTE, EMPLEADO o ADMINISTRADOR
     */
    public FrmMenuPrincipal(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
        configurarMenuPorRol();
    }
    
    /**
     * Configura propiedades básicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Sistema Bancario - Menú " + tipoUsuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Ajusta el título y otros elementos según el rol.
     */
    private void configurarMenuPorRol() {
        String titulo = "MENÚ PRINCIPAL - ";
        switch (tipoUsuario) {
            case "CLIENTE":
                titulo += "CLIENTE";
                break;
            case "EMPLEADO":
                titulo += "EMPLEADO";
                break;
            case "ADMINISTRADOR":
                titulo += "ADMINISTRADOR";
                break;
            default:
                titulo += "USUARIO";
        }
        setTitle(titulo);
    }
    
    /**
     * Inicializa y organiza los componentes gráficos.
     * Usa BoxLayout para organización vertical de botones.
     */
    private void initComponents() {
        // Panel principal con padding
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelPrincipal.setBackground(new Color(240, 248, 255)); // AliceBlue
        
        // Título del menú
        JLabel lblTitulo = new JLabel("🏦 " + tipoUsuario);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Sistema Bancario - Laboratorio 09");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblSubtitulo);
        
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Botones según el rol
        switch (tipoUsuario) {
            case "CLIENTE":
                agregarBoton(panelPrincipal, "💰 Consultar Saldo", e -> abrirConsultaSaldo());
                agregarBoton(panelPrincipal, "💵 Realizar Depósito", e -> abrirDeposito());
                agregarBoton(panelPrincipal, "💸 Realizar Retiro", e -> abrirRetiro());
                agregarBoton(panelPrincipal, "📤 Realizar Transferencia", e -> abrirTransferencia());
                agregarBoton(panelPrincipal, "📊 Ver Movimientos", e -> abrirMovimientos());
                agregarBoton(panelPrincipal, "👤 Ver Mis Cuentas", e -> mostrarMisCuentas());
                agregarBoton(panelPrincipal, "👤 Ver Mi Información", e -> mostrarMiInformacion());
                break;
                
            case "EMPLEADO":
                agregarBoton(panelPrincipal, "👥 Gestionar Clientes", e -> abrirGestionClientes());
                agregarBoton(panelPrincipal, "🏦 Gestionar Cuentas", e -> abrirGestionCuentas());
                agregarBoton(panelPrincipal, "💳 Procesar Transacciones", e -> abrirTransacciones());
                agregarBoton(panelPrincipal, "🔍 Consultas del Sistema", e -> abrirConsultas());
                agregarBoton(panelPrincipal, "📈 Reportes y Estadísticas", e -> abrirReportes());
                agregarBoton(panelPrincipal, "👤 Ver Mi Información", e -> mostrarMiInformacion());
                break;
                
            case "ADMINISTRADOR":
                agregarBoton(panelPrincipal, "👤 Gestionar Usuarios", e -> abrirGestionUsuarios());
                agregarBoton(panelPrincipal, "👔 Gestionar Empleados", e -> abrirGestionEmpleados());
                agregarBoton(panelPrincipal, "⚙️ Gestión Completa del Sistema", e -> abrirGestionCompleta());
                agregarBoton(panelPrincipal, "📊 Reportes y Estadísticas", e -> abrirReportesAdmin());
                agregarBoton(panelPrincipal, "🛡️ Auditoría del Sistema", e -> abrirAuditoria());
                agregarBoton(panelPrincipal, "🔐 Ver Todos los Permisos", e -> mostrarTodosPermisos());
                break;
        }
        
        panelPrincipal.add(Box.createVerticalGlue());
        
        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("🔒 Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setMaximumSize(new Dimension(400, 50));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelPrincipal.add(btnCerrarSesion);
        
        setContentPane(panelPrincipal);
    }
    
    /**
     * Agrega un botón al panel con estilo consistente.
     */
    private void agregarBoton(JPanel panel, String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(500, 60));
        boton.addActionListener(accion);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano
        
        // Color alternado para botones
        boton.setBackground(new Color(52, 152, 219));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        
        panel.add(boton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    // =============== MÉTODOS PARA CLIENTE ===============
    
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
    
    // =============== MÉTODOS PARA EMPLEADO ===============
    
private void abrirGestionClientes() {
    // Si ya tenés un JFrame con tabla/busqueda usalo; si no, este rápido:
    String[] opciones = {"Ver todos", "Buscar por DNI", "Agregar cliente"};
    int op = JOptionPane.showOptionDialog(this, "¿Qué desea hacer?", "Gestionar Clientes",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

    switch (op) {
        case 0 -> gestorBanco.getGestorUsuarios().mostrarTodosLosClientes(); // consola por ahora
        case 1 -> buscarClientePorDni();
        case 2 -> agregarClienteInteractivo();
    }
}

/* ---- helpers rápidos (si no tenés forms) ---- */
private void buscarClientePorDni() {
    String dni = JOptionPane.showInputDialog(this, "Ingrese DNI:");
    if (dni == null || dni.isBlank()) return;
    var c = gestorBanco.getGestorUsuarios().buscarCliente(dni);
    if (c != null) {
        JTextArea ta = new JTextArea(c.toString());
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Cliente encontrado", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void agregarClienteInteractivo() {
    gestorBanco.getGestorUsuarios().agregarClienteInteractivo(new Scanner(System.in)); // usa consola por ahora
    JOptionPane.showMessageDialog(this, "Cliente agregado vía consola", "Listo", JOptionPane.INFORMATION_MESSAGE);
}
private void abrirGestionCuentas() {
    String[] ops = {"Ver todas", "Crear cuenta de ahorros", "Crear cuenta corriente"};
    int op = JOptionPane.showOptionDialog(this, "Seleccione:", "Gestionar Cuentas",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
    switch (op) {
        case 0 -> gestorBanco.mostrarTodasLasCuentas(); // consola
        case 1 -> crearCuentaAhorros();
        case 2 -> crearCuentaCorriente();
    }
}

private void crearCuentaAhorros() {
    String num   = JOptionPane.showInputDialog("Número de cuenta:");
    String dni   = JOptionPane.showInputDialog("DNI del cliente:");
    String saldoS= JOptionPane.showInputDialog("Saldo inicial:");
    if (num == null || dni == null || saldoS == null) return;
    try {
        double saldo = Double.parseDouble(saldoS);
        var cliente  = gestorBanco.getGestorUsuarios().buscarCliente(dni);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean ok = gestorBanco.crearCuentaAhorros(num, saldo, cliente);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cuenta de ahorros creada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Saldo inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void crearCuentaCorriente() {
    // idem ahorros, pero llamando a crearCuentaCorriente(...)
    String num   = JOptionPane.showInputDialog("Número de cuenta:");
    String dni   = JOptionPane.showInputDialog("DNI del cliente:");
    String saldoS= JOptionPane.showInputDialog("Saldo inicial:");
    if (num == null || dni == null || saldoS == null) return;
    try {
        double saldo = Double.parseDouble(saldoS);
        var cliente  = gestorBanco.getGestorUsuarios().buscarCliente(dni);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean ok = gestorBanco.crearCuentaCorriente(num, saldo, cliente);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cuenta corriente creada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Saldo inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void abrirTransacciones() {
    String[] ops = {"Depósito", "Retiro", "Transferencia"};
    int op = JOptionPane.showOptionDialog(this, "¿Transacción?", "Procesar Transacciones",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
    switch (op) {
        case 0 -> new presentacion.cliente.FrmDeposito().setVisible(true);
        case 1 -> new presentacion.cliente.FrmRetiro().setVisible(true);
        case 2 -> new presentacion.cliente.FrmTransferenciaCliente().setVisible(true);
    }
}
    
   private void abrirConsultas() {
    String[] ops = {"Consultar saldo", "Consultar movimientos", "Transacciones recientes"};
    int op = JOptionPane.showOptionDialog(this, "¿Consulta?", "Consultas",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
    switch (op) {
        case 0 -> {
            String num = JOptionPane.showInputDialog("Número de cuenta:");
            if (num != null) gestorBanco.consultarSaldo(num); // usa el existente
        }
        case 1 -> {
            String num = JOptionPane.showInputDialog("Número de cuenta:");
            if (num != null) {
                var cuenta = gestorBanco.buscarCuenta(num);
                if (cuenta != null) {
                    JTextArea ta = new JTextArea(String.join("\n", cuenta.getMovimientos()));
                    ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Movimientos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cuenta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        case 2 -> {
            int cant = 5;
            try {
                String c = JOptionPane.showInputDialog("¿Cuántas transacciones?", "5");
                if (c != null) cant = Integer.parseInt(c);
            } catch (NumberFormatException ignore) {}
            var lista = gestorBanco.getTransaccionesRecientes(cant);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay transacciones", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                lista.forEach(t -> sb.append(t.toStringCorto()).append('\n'));
                JTextArea ta = new JTextArea(sb.toString());
                ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Recientes", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
    
    private void abrirReportes() {
        // Mostrar estadísticas en un área de texto
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTES EMPLEADO ===\n\n");
        reporte.append("Total de cuentas: ").append(gestorBanco.getCuentas().size()).append("\n");
        reporte.append("Total de transacciones: ").append(gestorBanco.getTransacciones().size()).append("\n");
        reporte.append("Total de titularidades: ").append(gestorBanco.getTitularidades().size()).append("\n");
        
        textArea.setText(reporte.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Reportes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // =============== MÉTODOS PARA ADMINISTRADOR ===============
    
    private void abrirGestionUsuarios() {
    new presentacion.administrador.FrmGestionUsuarios().setVisible(true);
}

    
private void abrirGestionEmpleados() {
    new presentacion.administrador.FrmGestionEmpleados().setVisible(true);
}
    
    private void abrirGestionCompleta() {
    new presentacion.administrador.FrmGestionCompletaAdmin().setVisible(true);
}
    
   private void abrirReportesAdmin() {
    new presentacion.administrador.FrmGestionCompletaAdmin().setVisible(true);   // re-usamos el dashboard
}
    
    private void abrirAuditoria() {
        // Auditoría en un área de texto
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        StringBuilder auditoria = new StringBuilder();
        auditoria.append("🔍 AUDITORÍA DEL SISTEMA\n\n");
        auditoria.append("Fecha: ").append(new java.util.Date()).append("\n");
        auditoria.append("Usuario actual: ").append(gestorUsuarios.getUsuarioActual().getNombreUsuario()).append("\n\n");
        
        auditoria.append("USUARIOS REGISTRADOS:\n");
        gestorUsuarios.getUsuarios().forEach(u -> 
            auditoria.append("- ").append(u.getNombreUsuario()).append(" (")
                     .append(u.getClass().getSimpleName()).append(")\n")
        );
        
        textArea.setText(auditoria.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Auditoría", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Cierra la sesión actual y vuelve al login.
     */
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cerrar sesión?", 
            "Confirmar Cierre de Sesión", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            gestorUsuarios.cerrarSesion();
            JOptionPane.showMessageDialog(this, 
                "✅ Sesión cerrada exitosamente", 
                "Cierre de Sesión", 
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); // Cerrar menú
            
            // Volver al login
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
            login.setLocationRelativeTo(null);
        }
    }
     private void mostrarTodosPermisos() {
    new presentacion.administrador.FrmPermisosAdmin().setVisible(true);
}
}