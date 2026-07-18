package controlador;

import dao.SistemaDAOImpl;
import modelo.Eleccion;
import modelo.Candidato;
import vista.FrmVotacion;
import javax.swing.*;
import java.util.List;

public class VotacionControlador {
    
    private FrmVotacion vista;
    private SistemaDAOImpl dao;

    public VotacionControlador(FrmVotacion vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // Cargar elecciones activas inicialmente
        cargarElecciones();

        // Enlazar cambio de elección para cargar candidatos
        this.vista.cmbEleccion.addActionListener(e -> cargarCandidatos());

        // Enlazar botones
        this.vista.btnVotar.addActionListener(e -> procesarVoto());
        this.vista.btnVolver.addActionListener(e -> this.vista.dispose());
    }

    private void cargarElecciones() {
        vista.cmbEleccion.removeAllItems();
        List<Eleccion> elecciones = dao.listarEleccionesActivas();
        for (Eleccion el : elecciones) {
            vista.cmbEleccion.addItem(el);
        }
        // Disparar carga de candidatos inicial
        cargarCandidatos();
    }

    private void cargarCandidatos() {
        vista.cmbCandidato.removeAllItems();
        Eleccion seleccionada = vista.getSelectedEleccion();
        if (seleccionada != null) {
            List<Candidato> candidatos = dao.listarCandidatosPorEleccion(seleccionada.getIdEleccion());
            for (Candidato cand : candidatos) {
                vista.cmbCandidato.addItem(cand);
            }
        }
    }

    private void procesarVoto() {
        String idVotanteStr = vista.getTxtIdVotante();

        // -------------------------------------------------------------------
        // MEJORA 1: Validación estricta de DNI
        // La expresión "\\d{8}" significa: estrictamente 8 caracteres numéricos
        // -------------------------------------------------------------------
        if (!idVotanteStr.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(vista, 
                "El ID Votante debe ser un DNI válido de exactamente 8 dígitos.", 
                "DNI Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return; // Cortamos la ejecución inmediatamente si no cumple
        }

        try {
            Eleccion eleccion = vista.getSelectedEleccion();
            Candidato candidato = vista.getSelectedCandidato();
            String votanteStr = vista.getTxtIdVotante();

            if (eleccion == null) {
                JOptionPane.showMessageDialog(vista, "No hay elecciones activas seleccionadas.");
                return;
            }
            if (candidato == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione un candidato.");
                return;
            }
            if (votanteStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese su ID de Votante.");
                return;
            }

            int idVotante = Integer.parseInt(votanteStr);

            // Regla R-30: Confirmar o cancelar voto antes del envío
            int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de su voto para el candidato " + candidato.getNombreCompleto() + "?\nEsta acción es irreversible.",
                "Confirmación de Voto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String comprobante = dao.registrarVoto(eleccion.getIdEleccion(), idVotante, candidato.getIdCandidato());

                if (comprobante != null && !comprobante.startsWith("ERROR")) {
                    JOptionPane.showMessageDialog(
                        vista,
                        "¡Voto emitido con éxito!\n\nSu comprobante de votación único (Anónimo) es:\n" + comprobante,
                        "Emisión de Voto Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    vista.btnVotar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(
                        vista, 
                        "Error al registrar el voto. Verifique su elegibilidad o si ya votó previamente.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID de votante debe ser un número entero.");
        }
    }
}