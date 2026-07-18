package vista;

import javax.swing.*;
import java.awt.*;

public class FrmGestionVotantes extends JFrame {
    public JTextField txtDni;
    public JTextField txtNombreCompleto;
    public JTextField txtFechaNacimiento;
    public JTextField txtJurisdiccionId;
    public JTextField txtEleccionId;
    public JButton btnRegistrar;
    public JButton btnCargarCsv;
    public JButton btnVolver;

    public FrmGestionVotantes() {
        setTitle("Gestión de Votantes");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel manualPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        manualPanel.setBorder(BorderFactory.createTitledBorder("Registro Individual / Matriculación"));

        manualPanel.add(new JLabel("Elección ID:"));
        txtEleccionId = new JTextField();
        manualPanel.add(txtEleccionId);

        manualPanel.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        manualPanel.add(txtDni);

        manualPanel.add(new JLabel("Nombre Completo:"));
        txtNombreCompleto = new JTextField();
        manualPanel.add(txtNombreCompleto);

        manualPanel.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):"));
        txtFechaNacimiento = new JTextField();
        manualPanel.add(txtFechaNacimiento);

        manualPanel.add(new JLabel("Jurisdicción ID:"));
        txtJurisdiccionId = new JTextField();
        manualPanel.add(txtJurisdiccionId);

        btnVolver = new JButton("Volver");
        manualPanel.add(btnVolver);

        btnRegistrar = new JButton("Registrar y Matricular");
        manualPanel.add(btnRegistrar);

        JPanel bulkPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        bulkPanel.setBorder(BorderFactory.createTitledBorder("Carga Masiva (CSV)"));
        btnCargarCsv = new JButton("Seleccionar CSV y Cargar Padrón");
        bulkPanel.add(btnCargarCsv);

        mainPanel.add(manualPanel, BorderLayout.CENTER);
        mainPanel.add(bulkPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
