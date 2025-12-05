package presentacion.empleado;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.personas.Cliente;
import presentacion.MainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmGestionClientes extends JFrame {
    
    private final GestorBanco gestorBanco;
    private final GestorUsuarios gestorUsuarios;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscarDNI;
    
    public FrmGestionClientes() {
        this.gestorBanco = MainGUI.getGestorBanco();
        this.gestorUsuarios = gestorBanco.getGestorUsuarios();
        initComponents();
        configurarVentana();
        cargarClientes();
    }
    
    private void configurarVentana() {
        setTitle("Gestión de Clientes - Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior - Búsqueda y botones
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 10, 10));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JLabel lblBuscar = new JLabel("Buscar por DNI:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelBusqueda.add(lblBuscar);
        
        txtBuscarDNI = new JTextField(18);
        txtBuscarDNI.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscarDNI.setToolTipText("Ingrese DNI para buscar cliente");
        panelBusqueda.add(txtBuscarDNI);
        
        JButton btnBuscar = new JButton(" Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBuscar.addActionListener(e -> buscarCliente());
        panelBusqueda.add(btnBuscar);
        
        JButton btnMostrarTodos = new JButton(" Ver Todos");
        btnMostrarTodos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnMostrarTodos.addActionListener(e -> cargarClientes());
        panelBusqueda.add(btnMostrarTodos);
        
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        
        JButton btnAgregar = new JButton(" Agregar Cliente");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(39, 174, 96));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregarCliente());
        panelAcciones.add(btnAgregar);
        
        JButton btnVerDetalles = new JButton(" Ver Detalles");
        btnVerDetalles.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnVerDetalles.addActionListener(e -> verDetallesCliente());
        panelAcciones.add(btnVerDetalles);
        
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAcciones);
        
        // Tabla de clientes
        String[] columnas = {"ID Cliente", "DNI", "Nombre Completo", "Email", "Categoría", "Límite Crédito"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Doble clic para ver detalles
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetallesCliente();
                }
            }
        });
        
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Panel botón cerrar
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnCerrar = new JButton(" Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCerrar.setBackground(new Color(231, 76, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar);
        
        // Organizar panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Carga todos los clientes en la tabla.
     */
    private void cargarClientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        for (Cliente cliente : gestorUsuarios.getClientes()) {
            Object[] fila = {
                cliente.getIdCliente(),
                cliente.getDni(),
                cliente.getNombreCompleto(),
                cliente.getEmail(),
                cliente.getCategoria(),
                String.format("$%,.2f", cliente.getLimiteCredito())
            };
            modeloTabla.addRow(fila);
        }
        
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No hay clientes registrados en el sistema", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Busca un cliente por DNI y muestra solo ese resultado.
     */
    private void buscarCliente() {
        String dni = txtBuscarDNI.getText().trim();
        
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                " Debe ingresar un DNI para buscar", 
                "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Cliente cliente = gestorUsuarios.buscarCliente(dni);
        
        if (cliente != null) {
            modeloTabla.setRowCount(0); // Limpiar tabla
            Object[] fila = {
                cliente.getIdCliente(),
                cliente.getDni(),
                cliente.getNombreCompleto(),
                cliente.getEmail(),
                cliente.getCategoria(),
                String.format("$%,.2f", cliente.getLimiteCredito())
            };
            modeloTabla.addRow(fila);
            
            // Seleccionar la fila encontrada
            if (modeloTabla.getRowCount() > 0) {
                tablaClientes.setRowSelectionInterval(0, 0);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                " Cliente no encontrado\n\n" +
                "No existe un cliente con DNI: " + dni, 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Muestra información detallada del cliente seleccionado.
     */
    private void verDetallesCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                " Debe seleccionar un cliente de la tabla", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String dni = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        Cliente cliente = gestorUsuarios.buscarCliente(dni);
        
        if (cliente != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("═══════════════════════════════════════════════════════\n");
            detalles.append("         INFORMACIÓN DETALLADA DEL CLIENTE\n");
            detalles.append("═══════════════════════════════════════════════════════\n\n");
            detalles.append(String.format("ID Cliente:      %s\n", cliente.getIdCliente()));
            detalles.append(String.format("DNI:             %s\n", cliente.getDni()));
            detalles.append(String.format("Nombre:          %s\n", cliente.getNombreCompleto()));
            detalles.append(String.format("Email:           %s\n", cliente.getEmail()));
            detalles.append(String.format("Teléfono:        %s\n", cliente.getTelefono()));
            detalles.append(String.format("Dirección:       %s\n", cliente.getDireccion()));
            detalles.append(String.format("Categoría:       %s\n", cliente.getCategoria()));
            detalles.append(String.format("Límite Crédito:  $%,.2f\n", cliente.getLimiteCredito()));
            detalles.append(String.format("Fecha Registro:  %s\n", cliente.getFechaRegistro()));
            detalles.append(String.format("Cuentas:         %d cuentas asociadas\n", cliente.getCuentasAsociadas().size()));
            detalles.append("\n═══════════════════════════════════════════════════════");
            
            JTextArea textArea = new JTextArea(detalles.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(250, 250, 250));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 450));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                "Detalles del Cliente", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Abre el formulario para agregar un nuevo cliente.
     */
    private void agregarCliente() {
        // Usar el método interactivo del GestorUsuarios
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Desea registrar un nuevo cliente?\n\n" +
            "Se abrirá un formulario interactivo para ingresar los datos.", 
            "Agregar Cliente", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Usar un diálogo con Scanner simulado
            JOptionPane.showMessageDialog(this, 
                "🚧 Funcionalidad de agregar cliente interactivo\n" +
                "Para implementación completa, se requiere un formulario dedicado.", 
                "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
            
            // En una versión completa, abrirías:
            // new FrmRegistrarCliente().setVisible(true);
        }
    }
}