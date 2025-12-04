package presentacion.empleado;

import gestor.GestorBanco;
import modelo.cuentas.Cuenta;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para consultas del sistema por parte de empleados.
 * Permite consultar saldos y movimientos de cuentas.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmConsultasEmpleado extends JFrame {
    
    private final GestorBanco gestorBanco;
    private JTabbedPane panelPestanas;
    
    public FrmConsultasEmpleado() {
        this.gestorBanco = MainGUI.getGestorBanco();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Consultas del Sistema - Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 550);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("🔍 CONSULTAS DEL SISTEMA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelPestanas = new JTabbedPane();
        panelPestanas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Pestaña Consultar Saldo
        JPanel panelSaldo = crearPanelConsultaSaldo();
        panelPestanas.addTab("💰 Consultar Saldo", panelSaldo);
        
        // Pestaña Consultar Movimientos
        JPanel panelMovimientos = crearPanelConsultaMovimientos();
        panelPestanas.addTab("📊 Consultar Movimientos", panelMovimientos);
        
        // Botón cerrar
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnCerrar);
        
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelPestanas, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JPanel crearPanelConsultaSaldo() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        // Entrada
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelEntrada.setBackground(Color.WHITE);
        
        panelEntrada.add(new JLabel("Número de Cuenta:"));
        JTextField txtCuenta = new JTextField(20);
        panelEntrada.add(txtCuenta);
        
        JButton btnConsultar = new JButton("🔍 Consultar");
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsultar.setBackground(new Color(52, 152, 219));
        btnConsultar.setForeground(Color.WHITE);
        panelEntrada.add(btnConsultar);
        
        // Resultado
        JTextArea txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtResultado.setBackground(new Color(250, 250, 250));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setPreferredSize(new Dimension(600, 300));
        
        btnConsultar.addActionListener(e -> {
            String numeroCuenta = txtCuenta.getText().trim();
            
            if (numeroCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese número de cuenta", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cuenta cuenta = gestorBanco.buscarCuenta(numeroCuenta);
            
            if (cuenta != null) {
                StringBuilder info = new StringBuilder();
                info.append("═══════════════════════════════════════════\n");
                info.append("         INFORMACIÓN DE CUENTA\n");
                info.append("═══════════════════════════════════════════\n\n");
                info.append(String.format("Número: %s\n", cuenta.getNumeroCuenta()));
                info.append(String.format("Tipo:   %s\n", cuenta.getClass().getSimpleName()));
                info.append(String.format("Titular: %s\n", cuenta.getCliente().getNombreCompleto()));
                info.append(String.format("Saldo:  $%,.2f\n", cuenta.getSaldo()));
                info.append(String.format("Estado: %s\n", cuenta.getEstado()));
                info.append("═══════════════════════════════════════════");
                
                txtResultado.setText(info.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                txtResultado.setText("");
            }
        });
        
        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(scrollResultado, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelConsultaMovimientos() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        // Entrada
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelEntrada.setBackground(Color.WHITE);
        
        panelEntrada.add(new JLabel("Número de Cuenta:"));
        JTextField txtCuenta = new JTextField(20);
        panelEntrada.add(txtCuenta);
        
        JButton btnConsultar = new JButton("📊 Consultar Movimientos");
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsultar.setBackground(new Color(52, 152, 219));
        btnConsultar.setForeground(Color.WHITE);
        panelEntrada.add(btnConsultar);
        
        // Resultado
        JTextArea txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultado.setBackground(new Color(250, 250, 250));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setPreferredSize(new Dimension(600, 300));
        
        btnConsultar.addActionListener(e -> {
            String numeroCuenta = txtCuenta.getText().trim();
            
            if (numeroCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese número de cuenta", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cuenta cuenta = gestorBanco.buscarCuenta(numeroCuenta);
            
            if (cuenta != null) {
                if (cuenta.getMovimientos().isEmpty()) {
                    txtResultado.setText("No hay movimientos registrados para esta cuenta.");
                } else {
                    StringBuilder movimientos = new StringBuilder();
                    movimientos.append("═══════════════════════════════════════════════════════\n");
                    movimientos.append("                HISTORIAL DE MOVIMIENTOS\n");
                    movimientos.append("═══════════════════════════════════════════════════════\n\n");
                    
                    for (String mov : cuenta.getMovimientos()) {
                        movimientos.append(mov).append("\n");
                    }
                    
                    movimientos.append("\n═══════════════════════════════════════════════════════");
                    txtResultado.setText(movimientos.toString());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cuenta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                txtResultado.setText("");
            }
        });
        
        panel.add(panelEntrada, BorderLayout.NORTH);
        panel.add(scrollResultado, BorderLayout.CENTER);
        
        return panel;
    }
}