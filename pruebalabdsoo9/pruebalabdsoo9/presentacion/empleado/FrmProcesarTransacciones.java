package presentacion.empleado;

import gestor.GestorBanco;
import modelo.transacciones.Transaccion;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;

public class FrmProcesarTransacciones extends JFrame {
    
    private final GestorBanco gestorBanco;
    private JTabbedPane panelPestanas;
    
    public FrmProcesarTransacciones() {
        this.gestorBanco = MainGUI.getGestorBanco();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Procesar Transacciones - Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 550);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel(" PROCESAR TRANSACCIONES PARA CLIENTES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel con pestañas
        panelPestanas = new JTabbedPane();
        panelPestanas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Pestaña Depósito
        JPanel panelDeposito = crearPanelDeposito();
        panelPestanas.addTab(" Depósito", panelDeposito);
        
        // Pestaña Retiro
        JPanel panelRetiro = crearPanelRetiro();
        panelPestanas.addTab(" Retiro", panelRetiro);
        
        // Pestaña Transferencia
        JPanel panelTransferencia = crearPanelTransferencia();
        panelPestanas.addTab(" Transferencia", panelTransferencia);
        
        // Botón cerrar
        JButton btnCerrar = new JButton(" Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnCerrar);
        
        // Organizar
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelPestanas, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JPanel crearPanelDeposito() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Número de Cuenta:"), gbc);
        
        JTextField txtCuenta = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtCuenta, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Monto:"), gbc);
        
        JTextField txtMonto = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtMonto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Descripción:"), gbc);
        
        JTextField txtDescripcion = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtDescripcion, gbc);
        
        // Botón procesar
        JButton btnProcesar = new JButton(" Procesar Depósito");
        btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProcesar.setBackground(new Color(39, 174, 96));
        btnProcesar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnProcesar, gbc);
        
        btnProcesar.addActionListener(e -> {
            String cuenta = txtCuenta.getText();
            String monto = txtMonto.getText();
            String descripcion = txtDescripcion.getText();
            
            if (cuenta.isEmpty() || monto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double montoD = Double.parseDouble(monto);
                Transaccion transaccion = gestorBanco.realizarDeposito(cuenta, montoD, descripcion);
                
                if (transaccion != null) {
                    JOptionPane.showMessageDialog(this, "Depósito procesado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtCuenta.setText("");
                    txtMonto.setText("");
                    txtDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al procesar depósito", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelRetiro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Número de Cuenta:"), gbc);
        
        JTextField txtCuenta = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtCuenta, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Monto:"), gbc);
        
        JTextField txtMonto = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtMonto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Método:"), gbc);
        
        JComboBox<String> cmbMetodo = new JComboBox<>(new String[]{"CAJERO", "VENTANILLA"});
        gbc.gridx = 1;
        panel.add(cmbMetodo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Ubicación:"), gbc);
        
        JTextField txtUbicacion = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtUbicacion, gbc);
        
        // Botón procesar
        JButton btnProcesar = new JButton(" Procesar Retiro");
        btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProcesar.setBackground(new Color(231, 76, 60));
        btnProcesar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnProcesar, gbc);
        
        btnProcesar.addActionListener(e -> {
            String cuenta = txtCuenta.getText();
            String monto = txtMonto.getText();
            String metodo = cmbMetodo.getSelectedItem().toString();
            String ubicacion = txtUbicacion.getText();
            
            if (cuenta.isEmpty() || monto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double montoD = Double.parseDouble(monto);
                Transaccion transaccion = gestorBanco.realizarRetiro(cuenta, montoD, metodo, ubicacion);
                
                if (transaccion != null) {
                    JOptionPane.showMessageDialog(this, "Retiro procesado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtCuenta.setText("");
                    txtMonto.setText("");
                    txtUbicacion.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al procesar retiro", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private JPanel crearPanelTransferencia() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Cuenta Origen:"), gbc);
        
        JTextField txtOrigen = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtOrigen, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Cuenta Destino:"), gbc);
        
        JTextField txtDestino = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtDestino, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Monto:"), gbc);
        
        JTextField txtMonto = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtMonto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Concepto:"), gbc);
        
        JTextField txtConcepto = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtConcepto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Referencia:"), gbc);
        
        JTextField txtReferencia = new JTextField(18);
        gbc.gridx = 1;
        panel.add(txtReferencia, gbc);
        
        // Botón procesar
        JButton btnProcesar = new JButton(" Procesar Transferencia");
        btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProcesar.setBackground(new Color(52, 152, 219));
        btnProcesar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(btnProcesar, gbc);
        
        btnProcesar.addActionListener(e -> {
            String origen = txtOrigen.getText();
            String destino = txtDestino.getText();
            String monto = txtMonto.getText();
            String concepto = txtConcepto.getText();
            String referencia = txtReferencia.getText();
            
            if (origen.isEmpty() || destino.isEmpty() || monto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                double montoD = Double.parseDouble(monto);
                Transaccion transaccion = gestorBanco.realizarTransferencia(
                    origen, destino, montoD, concepto, referencia
                );
                
                if (transaccion != null) {
                    JOptionPane.showMessageDialog(this, "Transferencia procesada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtOrigen.setText("");
                    txtDestino.setText("");
                    txtMonto.setText("");
                    txtConcepto.setText("");
                    txtReferencia.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al procesar transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
}