package controlador;

import dao.SistemaDAOImpl;
import vista.FrmGestionCandidatos;
import javax.swing.*;

public class GestionCandidatosControlador {
    private FrmGestionCandidatos vista;
    private SistemaDAOImpl dao;

    public GestionCandidatosControlador(FrmGestionCandidatos vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        this.vista.btnRegistrar.addActionListener(e -> registrarCandidato());
        this.vista.btnVolver.addActionListener(e -> this.vista.dispose());
    }

    private void registrarCandidato() {
        try {
            int electionId = Integer.parseInt(vista.txtEleccionId.getText().trim());
            String listIdStr = vista.txtListaId.getText().trim();
            String name = vista.txtNombreCompleto.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el nombre completo del candidato.");
                return;
            }

            Integer listId = null;
            if (!listIdStr.isEmpty()) {
                listId = Integer.parseInt(listIdStr);
            }

            boolean exito = dao.registrarCandidato(electionId, listId, name);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Candidato registrado exitosamente.");
                vista.txtNombreCompleto.setText("");
                vista.txtListaId.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar candidato. Verifique que exista el ID Elección.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID Elección e ID Lista deben ser números enteros.");
        }
    }
}
