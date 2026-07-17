package vista;

import javax.swing.*;
import java.awt.*;

public class FrmVotacion extends JFrame {

    private JTextField txtIdEleccion;
    private JTextField txtIdVotante;
    private JTextField txtIdCandidato;
    
    // Lo hacemos público para que el Controlador pueda asignarle la acción del clic
    public JButton btnVotar;

    public FrmVotacion() {
        setTitle("Sistema de Votación - Emisión de Voto");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(4, 2, 10, 15));

        // Componentes
        panelPrincipal.add(new JLabel("ID Elección:"));
        txtIdEleccion = new JTextField();
        panelPrincipal.add(txtIdEleccion);

        panelPrincipal.add(new JLabel("ID Votante:"));
        txtIdVotante = new JTextField();
        panelPrincipal.add(txtIdVotante);

        panelPrincipal.add(new JLabel("ID Candidato:"));
        txtIdCandidato = new JTextField();
        panelPrincipal.add(txtIdCandidato);

        panelPrincipal.add(new JLabel()); // Espacio vacío
        btnVotar = new JButton("Emitir Voto");
        panelPrincipal.add(btnVotar);

        add(panelPrincipal);
    }

    // ========================================================
    // GETTERS PARA QUE EL CONTROLADOR PUEDA LEER LOS DATOS
    // ========================================================
    
    public String getTxtIdEleccion() {
        return txtIdEleccion.getText().trim();
    }

    public String getTxtIdVotante() {
        return txtIdVotante.getText().trim();
    }

    public String getTxtIdCandidato() {
        return txtIdCandidato.getText().trim();
    }

    // Main de prueba para ver el diseño (opcional)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmVotacion().setVisible(true);
            }
        });
    }
}