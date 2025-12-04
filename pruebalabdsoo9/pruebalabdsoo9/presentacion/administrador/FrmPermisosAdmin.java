package presentacion.administrador;

import javax.swing.*;
import java.awt.*;

public class FrmPermisosAdmin extends JFrame {

    public FrmPermisosAdmin() {
        super("Permisos - Administrador");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p.setBackground(new Color(240, 248, 255));

        JLabel tit = new JLabel("🔐 Permisos por Tipo de Usuario");
        tit.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tit.setForeground(new Color(41, 128, 185));
        tit.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBackground(new Color(250, 250, 250));
        area.setText(
                "=== PERMISOS DE CLIENTE ===\n" +
                "✔ Consultar saldo propio\n" +
                "✔ Realizar depósitos\n" +
                "✔ Realizar retiros\n" +
                "✔ Ver movimientos\n" +
                "✖ No puede acceder a datos de terceros\n\n" +
                "=== PERMISOS DE EMPLEADO ===\n" +
                "✔ Gestionar clientes\n" +
                "✔ Crear cuentas\n" +
                "✔ Procesar transacciones\n" +
                "✔ Generar reportes básicos\n" +
                "✖ No puede modificar empleados\n\n" +
                "=== PERMISOS DE ADMINISTRADOR ===\n" +
                "✔ Acceso total al sistema\n" +
                "✔ Gestionar usuarios y empleados\n" +
                "✔ Auditoría y backups\n" +
                "✔ Configuración del sistema\n"
        );
        area.setCaretPosition(0);

        JScrollPane sp = new JScrollPane(area);

        p.add(tit, BorderLayout.NORTH);
        p.add(sp, BorderLayout.CENTER);

        add(p);
    }
}