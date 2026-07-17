package vista;

import javax.swing.*;
import java.awt.*;

public class FrmAdmin extends JFrame {

    private JTextField txtIdEleccion;
    public JButton btnPublicar;

    public FrmAdmin() {
        setTitle("Panel de Administrador - Gestión Electoral");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(2, 2, 10, 15));

        panelPrincipal.add(new JLabel("ID Elección a Publicar:"));
        txtIdEleccion = new JTextField();
        panelPrincipal.add(txtIdEleccion);

        panelPrincipal.add(new JLabel()); // Espacio para alinear el botón
        btnPublicar = new JButton("Activar Elección");
        panelPrincipal.add(btnPublicar);

        add(panelPrincipal);
    }

    // Getter para que el controlador lea el ID
    public String getTxtIdEleccion() {
        return txtIdEleccion.getText().trim();
    }
}