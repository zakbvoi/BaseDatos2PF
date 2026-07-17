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

        this.vista.btnVotar.addActionListener(e -> procesarVoto());
    }

    private void procesarVoto() {
        String idEleccionStr = vista.getTxtIdEleccion();
        String idVotanteStr = vista.getTxtIdVotante();
        String idCandidatoStr = vista.getTxtIdCandidato();

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
            int idEleccion = Integer.parseInt(idEleccionStr);
            // El tipo 'int' soporta hasta más de 2 billones (10 dígitos), 
            // así que un DNI de 8 dígitos se convierte sin ningún riesgo de desbordamiento.
            int idVotante = Integer.parseInt(idVotanteStr); 
            int idCandidato = Integer.parseInt(idCandidatoStr);
            
            // -------------------------------------------------------------------
            // MEJORA 2: Evitar el doble voto
            // -------------------------------------------------------------------
            if (dao.yaVoto(idEleccion, idVotante)) {
                JOptionPane.showMessageDialog(vista, 
                    "Error: El DNI " + idVotante + " ya ha emitido un voto para esta elección. El doble voto es un delito.", 
                    "Voto Duplicado", 
                    JOptionPane.ERROR_MESSAGE);
                return; // Cortamos la ejecución para que no llegue a guardar
            }

            String comprobante = dao.registrarVoto(idEleccion, idVotante, idCandidato);

            if (comprobante != null && !comprobante.startsWith("ERROR")) {
                JOptionPane.showMessageDialog(vista, "¡Voto exitoso!\nComprobante: " + comprobante, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.btnVotar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar el voto en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los IDs de Elección y Candidato también deben ser números enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}