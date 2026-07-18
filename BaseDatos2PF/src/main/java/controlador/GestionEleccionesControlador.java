package controlador;

import dao.SistemaDAOImpl;
import vista.FrmGestionElecciones;
import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class GestionEleccionesControlador {
    private FrmGestionElecciones vista;
    private SistemaDAOImpl dao;

    public GestionEleccionesControlador(FrmGestionElecciones vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        this.vista.btnCrear.addActionListener(e -> crearEleccion());
        this.vista.btnVolver.addActionListener(e -> {
            this.vista.dispose();
        });
    }

    private void crearEleccion() {
        try {
            int contractId = Integer.parseInt(vista.txtContratoId.getText().trim());
            int typeId = Integer.parseInt(vista.txtTipoId.getText().trim());
            int jurisdictionId = Integer.parseInt(vista.txtJurisdiccionId.getText().trim());
            String title = vista.txtTitulo.getText().trim();
            String startStr = vista.txtFechaInicio.getText().trim();
            String endStr = vista.txtFechaFin.getText().trim();

            if (title.isEmpty() || startStr.isEmpty() || endStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Complete todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp start = new Timestamp(sdf.parse(startStr).getTime());
            Timestamp end = new Timestamp(sdf.parse(endStr).getTime());

            boolean exito = dao.crearEleccion(contractId, typeId, jurisdictionId, title, start, end);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Elección creada exitosamente con estado Creada.");
                vista.txtContratoId.setText("");
                vista.txtTipoId.setText("");
                vista.txtJurisdiccionId.setText("");
                vista.txtTitulo.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar la elección. Verifique la base de datos.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los IDs deben ser números enteros.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha inválido. Utilice: yyyy-MM-dd HH:mm:ss");
        }
    }
}
