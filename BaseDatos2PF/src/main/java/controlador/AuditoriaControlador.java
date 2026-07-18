package controlador;

import dao.SistemaDAOImpl;
import modelo.Auditoria;
import vista.FrmAuditoria;
import java.util.List;

public class AuditoriaControlador {
    private FrmAuditoria vista;
    private SistemaDAOImpl dao;

    public AuditoriaControlador(FrmAuditoria vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        this.vista.btnRefrescar.addActionListener(e -> cargarDatos());
        this.vista.btnVolver.addActionListener(e -> this.vista.dispose());

        cargarDatos();
    }

    private void cargarDatos() {
        // Limpiar tabla
        vista.modeloTabla.setRowCount(0);

        List<Auditoria> auditorias = dao.obtenerReporteAuditoria();
        for (Auditoria aud : auditorias) {
            vista.modeloTabla.addRow(new Object[]{
                aud.getTablaAfectada(),
                aud.getTipoAccion(),
                aud.getTotalAcciones(),
                aud.getUltimaAccion() != null ? aud.getUltimaAccion().toString() : "N/A"
            });
        }
    }
}
