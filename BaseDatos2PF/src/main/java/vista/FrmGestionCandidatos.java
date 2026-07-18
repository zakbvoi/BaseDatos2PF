package vista;

import javax.swing.*;
import java.awt.*;

public class FrmGestionCandidatos extends JFrame {
    public JTextField txtEleccionId;
    public JTextField txtListaId;
    public JTextField txtNombreCompleto;
    public JButton btnRegistrar;
    public JButton btnVolver;

    public FrmGestionCandidatos() {
        setTitle("Gestión de Candidatos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Elección ID:"));
        txtEleccionId = new JTextField();
        panel.add(txtEleccionId);

        panel.add(new JLabel("Lista / Partido ID (Opcional):"));
        txtListaId = new JTextField();
        panel.add(txtListaId);

        panel.add(new JLabel("Nombre Completo:"));
        txtNombreCompleto = new JTextField();
        panel.add(txtNombreCompleto);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        btnRegistrar = new JButton("Registrar Candidato");
        panel.add(btnRegistrar);

        add(panel);
    }
}
