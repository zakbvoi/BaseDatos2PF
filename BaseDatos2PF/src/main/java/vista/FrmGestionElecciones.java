package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FrmGestionElecciones extends JFrame {
    public JTextField txtContratoId;
    public JTextField txtTipoId;
    public JTextField txtJurisdiccionId;
    public JTextField txtTitulo;
    public JTextField txtFechaInicio;
    public JTextField txtFechaFin;
    public JButton btnCrear;
    public JButton btnVolver;

    public FrmGestionElecciones() {
        setTitle("Gestión de Elecciones");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Contrato ID:"));
        txtContratoId = new JTextField();
        panel.add(txtContratoId);

        panel.add(new JLabel("Tipo Elección ID:"));
        txtTipoId = new JTextField();
        panel.add(txtTipoId);

        panel.add(new JLabel("Jurisdicción ID:"));
        txtJurisdiccionId = new JTextField();
        panel.add(txtJurisdiccionId);

        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Fecha Inicio (yyyy-MM-dd HH:mm:ss):"));
        txtFechaInicio = new JTextField();
        // Sugerir fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txtFechaInicio.setText(sdf.format(new java.util.Date()));
        panel.add(txtFechaInicio);

        panel.add(new JLabel("Fecha Fin (yyyy-MM-dd HH:mm:ss):"));
        txtFechaFin = new JTextField();
        // Sugerir fecha de mañana
        txtFechaFin.setText(sdf.format(new java.util.Date(System.currentTimeMillis() + 86400000)));
        panel.add(txtFechaFin);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        btnCrear = new JButton("Crear Elección");
        panel.add(btnCrear);

        add(panel);
    }
}
