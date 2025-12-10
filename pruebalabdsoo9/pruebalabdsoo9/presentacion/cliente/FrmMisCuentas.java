package presentacion.cliente;

import gestor.GestorBanco;
import gestor.GestorUsuarios;
import modelo.cuentas.Cuenta;
import modelo.personas.UsuarioCliente;

import javax.swing.*;
import java.awt.*;

public class FrmMisCuentas extends JFrame {

    private GestorUsuarios gestorUsuarios;
    private GestorBanco gestorBanco;

    public FrmMisCuentas(GestorUsuarios gestorUsuarios, GestorBanco gestorBanco) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorBanco = gestorBanco;

        setTitle("Mis Cuentas Bancarias");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        UsuarioCliente usuarioActual = (UsuarioCliente) gestorUsuarios.getUsuarioActual();

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));

        StringBuilder info = new StringBuilder();

        if (usuarioActual != null && usuarioActual.getCliente() != null) {
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
        }

        textArea.setText(info.toString());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}
