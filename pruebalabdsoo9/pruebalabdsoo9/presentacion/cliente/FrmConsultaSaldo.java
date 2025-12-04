package presentacion.cliente;

import gestor.GestorBanco;
import modelo.cuentas.Cuenta;
import presentacion.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para consultar el saldo de una cuenta bancaria.
 * Llama directamente a la lógica existente del sistema.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmConsultaSaldo extends JFrame {
    
    private final GestorBanco gestorBanco;
    private JTextField txtNumeroCuenta;
    private JTextArea txtResultado;
    private JButton btnConsultar;
    
    public FrmConsultaSaldo() {
        this.gestorBanco = MainGUI.getGestorBanco();
        initComponents();
        configurarVentana();
    }
    
    /**
     * Configura propiedades de la ventana.
     */
    private void configurarVentana() {
        setTitle("Consulta de Saldo - Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue
    }
    
    /**
     * Inicializa todos los componentes gráficos.
     */
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Título
        JLabel lblTitulo = new JLabel("💰 CONSULTA DE SALDO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de entrada
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelEntrada.setBackground(new Color(240, 248, 255));
        
        JLabel lblCuenta = new JLabel("Número de Cuenta:");
        lblCuenta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelEntrada.add(lblCuenta);
        
        txtNumeroCuenta = new JTextField(20);
        txtNumeroCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNumeroCuenta.setToolTipText("Ingrese el número de cuenta a consultar");
        panelEntrada.add(txtNumeroCuenta);
        
        btnConsultar = new JButton("🔍 Consultar");
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsultar.setBackground(new Color(52, 152, 219));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelEntrada.add(btnConsultar);
        
        // Área de resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtResultado.setBackground(new Color(250, 250, 250));
        txtResultado.setForeground(new Color(44, 62, 80));
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        
        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollResultado.setPreferredSize(new Dimension(550, 280));
        
        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(240, 248, 255));
        
        JButton btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpiar.addActionListener(e -> limpiar());
        panelBotones.add(btnLimpiar);
        
        JButton btnCerrar = new JButton("❌ Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        
        // Agregar evento al botón consultar
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsulta();
            }
        });
        
        // Organizar panel principal
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelEntrada, BorderLayout.CENTER);
        panelPrincipal.add(scrollResultado, BorderLayout.SOUTH);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Ajustar layout
        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.add(panelEntrada, BorderLayout.NORTH);
        panelContenido.add(scrollResultado, BorderLayout.CENTER);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }
    
    /**
     * Realiza la consulta de saldo usando la lógica existente.
     */
    private void realizarConsulta() {
        String numeroCuenta = txtNumeroCuenta.getText().trim();
        
        // Validación básica
        if (numeroCuenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Debe ingresar un número de cuenta", 
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Llamada a la lógica EXISTENTE del sistema
        Cuenta cuenta = gestorBanco.buscarCuenta(numeroCuenta);
        
        if (cuenta != null) {
            // Validar que el cliente sea titular de la cuenta
modelo.personas.Usuario actual = gestorBanco.getGestorUsuarios().getUsuarioActual();            
if (actual instanceof modelo.personas.UsuarioCliente) {
                var cliente = ((modelo.personas.UsuarioCliente)actual).getCliente();
                if (!cliente.getCuentasAsociadas().contains(numeroCuenta)) {
                    JOptionPane.showMessageDialog(this, 
                        "❌ No tiene permiso para consultar esta cuenta", 
                        "Error de Permisos", JOptionPane.ERROR_MESSAGE);
                    txtResultado.setText("");
                    return;
                }
            }
            
            // Usar el método EXISTENTE de la cuenta
            StringBuilder info = new StringBuilder();
            info.append("═══════════════════════════════════════════════════════\n");
            info.append("              INFORMACIÓN DE CUENTA\n");
            info.append("═══════════════════════════════════════════════════════\n\n");
            info.append(String.format("Número: %s\n", cuenta.getNumeroCuenta()));
            info.append(String.format("Tipo:   %s\n", cuenta.getClass().getSimpleName()));
            info.append(String.format("Titular: %s\n", cuenta.getCliente().getNombreCompleto()));
            info.append(String.format("DNI:    %s\n", cuenta.getCliente().getDni()));
            info.append(String.format("Saldo:  $%,.2f\n", cuenta.getSaldo()));
            info.append(String.format("Estado: %s\n", cuenta.getEstado()));
            info.append(String.format("Fecha Apertura: %s\n", cuenta.getFechaApertura().toLocalDate()));
            info.append("\n═══════════════════════════════════════════════════════");
            
            txtResultado.setText(info.toString());
            txtResultado.setCaretPosition(0);
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Cuenta no encontrada\n" +
                "Verifique el número de cuenta ingresado.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            txtResultado.setText("");
        }
    }
    
    /**
     * Limpia los campos de entrada y salida.
     */
    private void limpiar() {
        txtNumeroCuenta.setText("");
        txtResultado.setText("");
        txtNumeroCuenta.requestFocus();
    }
}