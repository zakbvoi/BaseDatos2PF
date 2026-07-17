package vista;

import dao.SistemaDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmVotacion extends JFrame {

    private JTextField txtIdEleccion;
    private JTextField txtIdVotante;
    private JTextField txtIdCandidato;
    private JButton btnVotar;

    public FrmVotacion() {
        setTitle("Sistema de Votación - Emisión de Voto");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // En un flujo real, sería DISPOSE_ON_CLOSE
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

        // Acción del botón
        btnVotar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emitirVoto();
            }
        });
    }

    private void emitirVoto() {
        try {
            // 1. Recoger datos de la vista
            int idEleccion = Integer.parseInt(txtIdEleccion.getText());
            int idVotante = Integer.parseInt(txtIdVotante.getText());
            int idCandidato = Integer.parseInt(txtIdCandidato.getText());

            // 2. Llamar al DAO
            SistemaDAOImpl dao = new SistemaDAOImpl();
            String comprobante = dao.registrarVoto(idEleccion, idVotante, idCandidato);

            // 3. Evaluar respuesta
            if (comprobante != null) {
                JOptionPane.showMessageDialog(this, 
                    "¡Voto registrado exitosamente!\nSu código de comprobante es:\n" + comprobante, 
                    "Voto Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
                // Bloquear el botón para evitar doble envío
                btnVotar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo registrar el voto. Verifique los datos o consulte la consola.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese solo números en los campos de ID.", "Error de formato", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmVotacion().setVisible(true);
            }
        });
    }
}