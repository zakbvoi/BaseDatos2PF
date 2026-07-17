package controlador;

import dao.SistemaDAOImpl;
import vista.FrmVotacion;
import javax.swing.JOptionPane;

public class VotacionControlador {
    private FrmVotacion vista;
    private SistemaDAOImpl dao;

    public VotacionControlador(FrmVotacion vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // Escuchamos el botón desde aquí
        this.vista.btnVotar.addActionListener(e -> procesarVoto());
    }

    private void procesarVoto() {
        try {
            int idEleccion = Integer.parseInt(vista.getTxtIdEleccion());
            int idVotante = Integer.parseInt(vista.getTxtIdVotante());
            int idCandidato = Integer.parseInt(vista.getTxtIdCandidato());

            String comprobante = dao.registrarVoto(idEleccion, idVotante, idCandidato);

            if (comprobante != null && !comprobante.startsWith("ERROR")) {
                JOptionPane.showMessageDialog(vista, "¡Voto exitoso!\nComprobante: " + comprobante);
                vista.btnVotar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar el voto.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese solo números.");
        }
    }
}