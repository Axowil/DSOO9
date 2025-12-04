package presentacion.administrador;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.transacciones.Transaccion;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formulario de Auditoría del Sistema para Administradores.
 * Muestra logs de acceso, transacciones recientes y estado del sistema.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmAuditoria extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    private JTextArea txtAuditoria;
    
    public FrmAuditoria() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
        generarReporteAuditoria();
    }
    
    private void configurarVentana() {
        setTitle("Auditoría del Sistema - Administrador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("🛡️ AUDITORÍA DEL SISTEMA BANCARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(231, 76, 60));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Área de texto para auditoría
        txtAuditoria = new JTextArea();
        txtAuditoria.setEditable(false);
        txtAuditoria.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAuditoria.setBackground(new Color(250, 250, 250));
        txtAuditoria.setForeground(new Color(44, 62, 80));
        
        JScrollPane scrollAuditoria = new JScrollPane(txtAuditoria);
        scrollAuditoria.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(39, 174, 96));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> generarReporteAuditoria());
        panelBotones.add(btnActualizar);
        
        JButton btnExportar = new JButton("📥 Exportar Log");
        btnExportar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnExportar.addActionListener(e -> exportarLog());
        panelBotones.add(btnExportar);
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        
        // Organizar
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollAuditoria, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Genera el reporte de auditoría completo.
     */
    private void generarReporteAuditoria() {
        StringBuilder auditoria = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaActual = sdf.format(new Date());
        
        // Encabezado
        auditoria.append("╔══════════════════════════════════════════════════════════════════════╗\n");
        auditoria.append("║                      AUDITORÍA DE SISTEMA BANCARIO                    ║\n");
        auditoria.append("╠══════════════════════════════════════════════════════════════════════╣\n");
        auditoria.append(String.format("║ Fecha de Auditoría: %-50s ║\n", fechaActual));
        auditoria.append(String.format("║ Usuario Auditor: %-51s ║\n", gestorUsuarios.getUsuarioActual().getNombreUsuario()));
        auditoria.append("╚══════════════════════════════════════════════════════════════════════╝\n\n");
        
        // 1. ESTADO DEL SISTEMA
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    1. ESTADO DEL SISTEMA\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        Runtime runtime = Runtime.getRuntime();
        long memoriaUsada = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
        long memoriaMaxima = runtime.maxMemory() / 1024 / 1024;
        
        auditoria.append(String.format("• Memoria Java Usada:        %d MB\n", memoriaUsada));
        auditoria.append(String.format("• Memoria Máxima:            %d MB\n", memoriaMaxima));
        auditoria.append(String.format("• Procesadores Disponibles:  %d\n", runtime.availableProcessors()));
        auditoria.append(String.format("• Versión Java:              %s\n", System.getProperty("java.version")));
        auditoria.append(String.format("• Sistema Operativo:         %s\n\n", System.getProperty("os.name")));
        
        // 2. REGISTROS EN BASE DE DATOS (simulado)
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    2. REGISTROS EN EL SISTEMA\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        auditoria.append(String.format("• Total de Usuarios:         %,d registros\n", gestorUsuarios.getUsuarios().size()));
        auditoria.append(String.format("• Total de Clientes:         %,d registros\n", gestorUsuarios.getClientes().size()));
        auditoria.append(String.format("• Total de Empleados:        %,d registros\n", gestorUsuarios.getEmpleados().size()));
        auditoria.append(String.format("• Total de Cuentas:          %,d registros\n", gestorBanco.getCuentas().size()));
        auditoria.append(String.format("• Total de Transacciones:    %,d registros\n", gestorBanco.getTransacciones().size()));
        auditoria.append(String.format("• Total de Titularidades:    %,d registros\n\n", gestorBanco.getTitularidades().size()));
        
        // 3. USUARIOS ACTIVOS E INACTIVOS
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    3. ESTADO DE USUARIOS\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        long activos = gestorUsuarios.getUsuarios().stream().filter(Usuario::isEstado).count();
        long inactivos = gestorUsuarios.getUsuarios().size() - activos;
        
        auditoria.append(String.format("• Usuarios Activos:          %,d (%.1f%%)\n", activos, (activos * 100.0 / gestorUsuarios.getUsuarios().size())));
        auditoria.append(String.format("• Usuarios Inactivos:        %,d (%.1f%%)\n\n", inactivos, (inactivos * 100.0 / gestorUsuarios.getUsuarios().size())));
        
        // 4. TRANSACCIONES RECIENTES (últimas 10)
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    4. TRANSACCIONES RECIENTES\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        var transaccionesRecientes = gestorBanco.getTransaccionesRecientes(10);
        
        if (transaccionesRecientes.isEmpty()) {
            auditoria.append("No se encontraron transacciones recientes.\n\n");
        } else {
            for (Transaccion t : transaccionesRecientes) {
                auditoria.append(String.format("• [%s] %s | %s | $%,.2f | %s\n",
                    t.getFechaHora().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    t.getIdTransaccion(),
                    t.getTipo(),
                    t.getMonto(),
                    t.getEstado()
                ));
            }
            auditoria.append("\n");
        }
        
        // 5. RESUMEN DE CUENTAS
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    5. RESUMEN DE CUENTAS\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        double totalDinero = gestorBanco.getCuentas().stream()
            .mapToDouble(Cuenta::getSaldo).sum();
        long ahorros = gestorBanco.getCuentas().stream()
            .filter(c -> c instanceof CuentaAhorros).count();
        long corriente = gestorBanco.getCuentas().stream()
            .filter(c -> c instanceof CuentaCorriente).count();
        long activas = gestorBanco.getCuentas().stream()
            .filter(Cuenta::estaActiva).count();
        
        auditoria.append(String.format("• Total Dinero en Sistema:   $%,.2f\n", totalDinero));
        auditoria.append(String.format("• Total de Cuentas:          %,d\n", gestorBanco.getCuentas().size()));
        auditoria.append(String.format("• Cuentas Activas:           %,d\n", activas));
        auditoria.append(String.format("• Cuentas de Ahorros:        %,d\n", ahorros));
        auditoria.append(String.format("• Cuentas Corriente:         %,d\n\n", corriente));
        
        // 6. ALERTAS Y ANOMALÍAS
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                    6. ALERTAS Y ANOMALÍAS\n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n\n");
        
        // Verificar cuentas con saldo negativo
        long cuentasNegativas = gestorBanco.getCuentas().stream()
            .filter(c -> c.getSaldo() < 0).count();
        
        if (cuentasNegativas > 0) {
            auditoria.append(String.format("⚠️  ALERTA: Encontradas %,d cuenta(s) con saldo negativo.\n\n", cuentasNegativas));
        }
        
        // Verificar usuarios sin actividad (inactivos)
        if (inactivos > 0) {
            auditoria.append(String.format("⚠️  ADVERTENCIA: Existen %,d usuario(s) inactivos.\n\n", inactivos));
        }
        
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        auditoria.append("                      FIN DEL REPORTE DE AUDITORÍA                      \n");
        auditoria.append("═══════════════════════════════════════════════════════════════════════\n");
        
        txtAuditoria.setText(auditoria.toString());
        txtAuditoria.setCaretPosition(0);
    }
    
    /**
     * Exporta el log de auditoría a un archivo (simulado).
     */
    private void exportarLog() {
        String reporte = txtAuditoria.getText();
        
        JOptionPane.showMessageDialog(this, 
            "🚧 Función de Exportación\n\n" +
            "En una implementación real, esto guardaría el reporte en:\n" +
            "auditoria/logs/auditoria_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt\n\n" +
            "Reporte generado con éxito. (Simulado)", 
            "Exportar Log", JOptionPane.INFORMATION_MESSAGE);
    }
}