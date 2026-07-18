package vista;

import modelo.Eleccion;
import modelo.Candidato;
import javax.swing.*;
import java.awt.*;

public class FrmVotacion extends JFrame {

    public JComboBox<Eleccion> cmbEleccion;
    public JTextField txtIdVotante;
    public JComboBox<Candidato> cmbCandidato;
    public JButton btnVotar;
    public JButton btnVolver;

    public FrmVotacion() {
        setTitle("Sistema de Votación - Emisión de Voto");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(5, 2, 10, 15));

        panelPrincipal.add(new JLabel("Seleccione Elección:"));
        cmbEleccion = new JComboBox<>();
        panelPrincipal.add(cmbEleccion);

        panelPrincipal.add(new JLabel("Ingrese su ID Votante:"));
        txtIdVotante = new JTextField();
        panelPrincipal.add(txtIdVotante);

        panelPrincipal.add(new JLabel("Seleccione Candidato:"));
        cmbCandidato = new JComboBox<>();
        panelPrincipal.add(cmbCandidato);

        btnVolver = new JButton("Volver");
        panelPrincipal.add(btnVolver);

        btnVotar = new JButton("Emitir Voto");
        panelPrincipal.add(btnVotar);

        add(panelPrincipal);
    }

    // Métodos convenientes para obtener objetos seleccionados
    public Eleccion getSelectedEleccion() {
        return (Eleccion) cmbEleccion.getSelectedItem();
    }

    public Candidato getSelectedCandidato() {
        return (Candidato) cmbCandidato.getSelectedItem();
    }

    public String getTxtIdVotante() {
        return txtIdVotante.getText().trim();
    }
}