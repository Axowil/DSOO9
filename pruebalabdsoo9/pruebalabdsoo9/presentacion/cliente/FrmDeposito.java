package presentacion.cliente;

import gestor.GestorBanco;
import modelo.transacciones.Transaccion;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para realizar depósitos en una cuenta bancaria.
 * Valida los datos ingresados y procesa la transacción.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmDeposito extends JFrame {
    
    private final GestorBanco gestorBanco;
    private JTextField txtNumeroCuenta;
    private JTextField txtMonto;
    private JTextField txtDescripcion;
    
    public FrmDeposito() {
        this.gestorBanco = MainGUI.getGestorBanco();
        initComponents();
        configurarVentana();
    }
    
    private void configurarVentana() {
        setTitle("Depósito Bancario - Sistema Bancario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelPrincipal.setBackground(new Color(240, 248, 255)); // AliceBlue
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel lblTitulo = new JLabel("💵 REALIZAR DEPÓSITO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(39, 174, 96));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Número de cuenta
        JLabel lblCuenta = new JLabel("Número de Cuenta:");
        lblCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelPrincipal.add(lblCuenta, gbc);
        
        txtNumeroCuenta = new JTextField(20);
        txtNumeroCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNumeroCuenta.setToolTipText("Ingrese el número de cuenta destino");
        gbc.gridx = 1; gbc.gridy = 1;
        panelPrincipal.add(txtNumeroCuenta, gbc);
        
        // Monto
        JLabel lblMonto = new JLabel("Monto a Depositar:");
        lblMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panelPrincipal.add(lblMonto, gbc);
        
        txtMonto = new JTextField(20);
        txtMonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMonto.setToolTipText("Ingrese el monto mayor a cero");
        gbc.gridx = 1; gbc.gridy = 2;
        panelPrincipal.add(txtMonto, gbc);
        
        // Descripción
        JLabel lblDescripcion = new JLabel("Descripción (Opcional):");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        panelPrincipal.add(lblDescripcion, gbc);
        
        txtDescripcion = new JTextField(20);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescripcion.setToolTipText("Descripción del depósito");
        gbc.gridx = 1; gbc.gridy = 3;
        panelPrincipal.add(txtDescripcion, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setOpaque(false);
        
        JButton btnDepositar = new JButton("✅ Depositar");
        btnDepositar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDepositar.setBackground(new Color(39, 174, 96));
        btnDepositar.setForeground(Color.WHITE);
        btnDepositar.addActionListener(e -> realizarDeposito());
        panelBotones.add(btnDepositar);
        
        JButton btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpiar.addActionListener(e -> limpiar());
        panelBotones.add(btnLimpiar);
        
        JButton btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal);
    }
    
    /**
     * Procesa el depósito con validaciones y llamada al gestor.
     */
    private void realizarDeposito() {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        String montoStr = txtMonto.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        
        // Validación de campos obligatorios
        if (numeroCuenta.isEmpty() || montoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Debe ingresar número de cuenta y monto", 
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validación y conversión del monto
        double monto;
        try {
            monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ El monto debe ser mayor a cero", 
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ El monto debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Realizar el depósito
        Transaccion transaccion = gestorBanco.realizarDeposito(numeroCuenta, monto, descripcion);
        
        if (transaccion != null) {
            JOptionPane.showMessageDialog(this, 
                "🎉 Depósito realizado con éxito!\n\n" +
                "ID Transacción: " + transaccion.getIdTransaccion() + "\n" +
                "Cuenta: " + numeroCuenta + "\n" +
                "Monto: $" + String.format("%,.2f", monto) + "\n" +
                "Descripción: " + (descripcion.isEmpty() ? "N/A" : descripcion), 
                "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al realizar el depósito.\n" +
                "Verifique que la cuenta existe, está activa y tiene permisos.", 
                "Error en Operación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiar() {
        txtNumeroCuenta.setText("");
        txtMonto.setText("");
        txtDescripcion.setText("");
        txtNumeroCuenta.requestFocus();
    }
}