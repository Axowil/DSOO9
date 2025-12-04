package presentacion.empleado;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.cuentas.Cuenta;
import modelo.cuentas.CuentaAhorros;
import modelo.cuentas.CuentaCorriente;
import modelo.personas.Cliente;
import presentacion.MainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

/**
 * Formulario para gestión de cuentas bancarias.
 * Permite crear cuentas de ahorros y corrientes, y ver todas las cuentas.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmGestionCuentas extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    private JTable tablaCuentas;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cmbTipoCuenta;
    private JTextField txtNumeroCuenta, txtDNI, txtSaldoInicial;
    
    public FrmGestionCuentas() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
        cargarCuentas();
    }
    
    private void configurarVentana() {
        setTitle("Gestión de Cuentas - Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior - Crear nueva cuenta
        JPanel panelCrear = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCrear.setBorder(BorderFactory.createTitledBorder("Abrir Nueva Cuenta"));
        
        JLabel lblTipo = new JLabel("Tipo de Cuenta:");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(lblTipo);
        
        cmbTipoCuenta = new JComboBox<>(new String[]{"AHORROS", "CORRIENTE"});
        cmbTipoCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(cmbTipoCuenta);
        
        JLabel lblNumero = new JLabel("Número de Cuenta:");
        lblNumero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(lblNumero);
        
        txtNumeroCuenta = new JTextField();
        txtNumeroCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(txtNumeroCuenta);
        
        JLabel lblDNI = new JLabel("DNI del Cliente:");
        lblDNI.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(lblDNI);
        
        txtDNI = new JTextField();
        txtDNI.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(txtDNI);
        
        JLabel lblSaldo = new JLabel("Saldo Inicial:");
        lblSaldo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(lblSaldo);
        
        txtSaldoInicial = new JTextField();
        txtSaldoInicial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelCrear.add(txtSaldoInicial);
        
        // Botón crear cuenta
        JButton btnCrear = new JButton("🏦 Crear Cuenta");
        btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrear.setBackground(new Color(39, 174, 96));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.addActionListener(e -> crearCuenta());
        
        JPanel panelBotonCrear = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonCrear.add(btnCrear);
        
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.add(panelCrear, BorderLayout.CENTER);
        panelNorte.add(panelBotonCrear, BorderLayout.SOUTH);
        panelNorte.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 0, 15, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        ));
        
        // Tabla de cuentas
        String[] columnas = {"Número", "Tipo", "Titular", "Saldo", "Estado", "Fecha Apertura"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCuentas = new JTable(modeloTabla);
        tablaCuentas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaCuentas.setRowHeight(25);
        tablaCuentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTabla = new JScrollPane(tablaCuentas);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Panel inferior - Botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnActualizar = new JButton("🔄 Actualizar Lista");
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnActualizar.addActionListener(e -> cargarCuentas());
        panelInferior.add(btnActualizar);
        
        JButton btnVerDetalles = new JButton("📄 Ver Detalles");
        btnVerDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnVerDetalles.addActionListener(e -> verDetallesCuenta());
        panelInferior.add(btnVerDetalles);
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar);
        
        // Organizar panel principal
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Carga todas las cuentas en la tabla.
     */
    private void cargarCuentas() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        List<Cuenta> cuentas = gestorBanco.getCuentas();
        
        for (Cuenta cuenta : cuentas) {
            Object[] fila = {
                cuenta.getNumeroCuenta(),
                cuenta.getClass().getSimpleName(),
                cuenta.getCliente().getNombreCompleto(),
                String.format("$%,.2f", cuenta.getSaldo()),
                cuenta.getEstado(),
                cuenta.getFechaApertura().toLocalDate()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    /**
     * Crea una nueva cuenta (ahorros o corriente).
     */
    private void crearCuenta() {
        String tipoCuenta = cmbTipoCuenta.getSelectedItem().toString();
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        String dni = txtDNI.getText().trim();
        String saldoStr = txtSaldoInicial.getText().trim();
        
        // Validaciones
        if (numeroCuenta.isEmpty() || dni.isEmpty() || saldoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Debe completar todos los campos", 
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(saldoStr);
            if (saldoInicial < 0) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ El saldo inicial no puede ser negativo", 
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El saldo debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Buscar cliente
        Cliente cliente = gestorUsuarios.buscarCliente(dni);
        
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, 
                "❌ Cliente no encontrado\n\n" +
                "No existe un cliente con DNI: " + dni, 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si la cuenta ya existe
        if (gestorBanco.existeCuenta(numeroCuenta)) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: Ya existe una cuenta con ese número", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear la cuenta
        boolean exito = false;
        if (tipoCuenta.equals("AHORROS")) {
            exito = gestorBanco.crearCuentaAhorros(numeroCuenta, saldoInicial, cliente);
        } else {
            exito = gestorBanco.crearCuentaCorriente(numeroCuenta, saldoInicial, cliente);
        }
        
        if (exito) {
            JOptionPane.showMessageDialog(this, 
                "🎉 Cuenta creada exitosamente!\n\n" +
                "Tipo: " + tipoCuenta + "\n" +
                "Número: " + numeroCuenta + "\n" +
                "Cliente: " + cliente.getNombreCompleto(), 
                "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            limpiarCampos();
            cargarCuentas(); // Actualizar tabla
        }
    }
    
    /**
     * Muestra detalles completos de la cuenta seleccionada.
     */
    private void verDetallesCuenta() {
        int filaSeleccionada = tablaCuentas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Debe seleccionar una cuenta de la tabla", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroCuenta = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        Cuenta cuenta = gestorBanco.buscarCuenta(numeroCuenta);
        
        if (cuenta != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("═══════════════════════════════════════════════════════\n");
            detalles.append("         INFORMACIÓN DETALLADA DE LA CUENTA\n");
            detalles.append("═══════════════════════════════════════════════════════\n\n");
            detalles.append(String.format("Número:        %s\n", cuenta.getNumeroCuenta()));
            detalles.append(String.format("Tipo:          %s\n", cuenta.getClass().getSimpleName()));
            detalles.append(String.format("Titular:       %s\n", cuenta.getCliente().getNombreCompleto()));
            detalles.append(String.format("Saldo:         $%,.2f\n", cuenta.getSaldo()));
            detalles.append(String.format("Estado:        %s\n", cuenta.getEstado()));
            detalles.append(String.format("Fecha Apertura: %s\n", cuenta.getFechaApertura().toLocalDate()));
            detalles.append(String.format("Movimientos:   %d registros\n", cuenta.getMovimientos().size()));
            detalles.append("\n═══════════════════════════════════════════════════════");
            
            JTextArea textArea = new JTextArea(detalles.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Detalles de la Cuenta", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Limpia los campos del formulario de creación.
     */
    private void limpiarCampos() {
        txtNumeroCuenta.setText("");
        txtDNI.setText("");
        txtSaldoInicial.setText("");
        cmbTipoCuenta.setSelectedIndex(0);
    }
}