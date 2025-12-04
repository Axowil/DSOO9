package presentacion;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario de inicio de sesión para el Sistema Bancario.
 * Permite autenticar usuarios según su rol: Cliente, Empleado o Administrador.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class FrmLogin extends javax.swing.JFrame {
    
    private GestorBanco gestorBanco;
    private GestorUsuarios gestorUsuarios;
    
    /**
     * Constructor que inicializa los componentes y configura la ventana.
     */
    public FrmLogin() {
        initComponents();
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        configurarVentana();
    }
    
    /**
     * Configura propiedades de la ventana.
     */
    private void configurarVentana() {
        setTitle("Sistema Bancario - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setResizable(false);
        setLocationRelativeTo(null); // Centrar al iniciar
        
        // Icono de la aplicación (opcional)
        // setIconImage(new ImageIcon(getClass().getResource("/iconos/banco.png")).getImage());
    }
    
    /**
     * Inicializa y organiza los componentes gráficos.
     * Usa GroupLayout para posicionamiento preciso.
     */
    private void initComponents() {
        // Creación de componentes
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel lblTipo = new JLabel("Tipo de Usuario:");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTextField txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setToolTipText("Ingrese su nombre de usuario");
        
        JPasswordField txtContraseña = new JPasswordField(20);
        txtContraseña.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtContraseña.setToolTipText("Ingrese su contraseña");
        
        JComboBox<String> cmbTipoUsuario = new JComboBox<>(new String[]{"CLIENTE", "EMPLEADO", "ADMINISTRADOR"});
        cmbTipoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JButton btnIngresar = new JButton("🔐 Ingresar");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(46, 204, 113));
        btnIngresar.setForeground(Color.WHITE);
        
        JButton btnSalir = new JButton("🚪 Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        
        // Configuración del layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        // Layout Horizontal
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(lblTitulo)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblContraseña)
                    .addComponent(lblTipo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContraseña, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoUsuario, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnIngresar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSalir, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        );
        
        // Layout Vertical
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblTitulo)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 30, 30)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsuario)
                .addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblContraseña)
                .addComponent(txtContraseña, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTipo)
                .addComponent(cmbTipoUsuario, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 30, 30)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnIngresar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSalir, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        );
        
        // Eventos de los botones
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarIngreso(txtUsuario, txtContraseña, cmbTipoUsuario);
            }
        });
        
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    /**
     * Procesa el intento de inicio de sesión.
     * Valida campos, autentica y abre el menú correspondiente.
     */
    private void procesarIngreso(JTextField txtUsuario, JPasswordField txtContraseña, JComboBox<String> cmbTipo) {
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword());
        String tipoSeleccionado = cmbTipo.getSelectedItem().toString();
        
        // Validación de campos vacíos
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Debe completar todos los campos", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Intentar autenticación
            if (gestorUsuarios.autenticarUsuario(usuario, contraseña)) {
                String tipoUsuarioActual = gestorUsuarios.getTipoUsuarioActual();
                
                // Verificar que el tipo coincida con la selección
                if (tipoUsuarioActual.equals(tipoSeleccionado)) {
                    JOptionPane.showMessageDialog(this, 
                        "🎉 ¡Bienvenido " + usuario + "!", 
                        "Autenticación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Abrir menú principal correspondiente
                    abrirMenuPrincipal(tipoUsuarioActual);
                    dispose(); // Cerrar ventana de login
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "❌ Error: No tiene permisos para ingresar como " + tipoSeleccionado + "\n" +
                        "Su tipo de usuario es: " + tipoUsuarioActual, 
                        "Error de Permisos", 
                        JOptionPane.ERROR_MESSAGE);
                    gestorUsuarios.cerrarSesion();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Credenciales incorrectas o usuario inactivo", 
                    "Error de Autenticación", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Error inesperado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Abre el menú principal según el tipo de usuario.
     */
    private void abrirMenuPrincipal(String tipoUsuario) {
        FrmMenuPrincipal menu = new FrmMenuPrincipal(tipoUsuario);
        menu.setVisible(true);
    }
}