package controlador;

import dao.SistemaDAOImpl;
import vista.FrmAdmin;
import vista.FrmGestionElecciones;
import vista.FrmGestionVotantes;
import vista.FrmGestionCandidatos;
import vista.FrmAuditoria;
import vista.FrmLogin;
import javax.swing.*;

public class AdminControlador {
    private FrmAdmin vista;
    private SistemaDAOImpl dao;

    public AdminControlador(FrmAdmin vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        this.vista.btnGestionarElecciones.addActionListener(e -> abrirGestionElecciones());
        this.vista.btnGestionarVotantes.addActionListener(e -> abrirGestionVotantes());
        this.vista.btnGestionarCandidatos.addActionListener(e -> abrirGestionCandidatos());
        this.vista.btnVerAuditorias.addActionListener(e -> abrirAuditoria());
        this.vista.btnPublicar.addActionListener(e -> publicarEleccion());
        this.vista.btnCerrarEleccion.addActionListener(e -> cerrarEleccion());
        this.vista.btnLogout.addActionListener(e -> logout());
    }

    private void abrirGestionElecciones() {
        FrmGestionElecciones f = new FrmGestionElecciones();
        new GestionEleccionesControlador(f);
        f.setVisible(true);
    }

    private void abrirGestionVotantes() {
        FrmGestionVotantes f = new FrmGestionVotantes();
        new GestionVotantesControlador(f);
        f.setVisible(true);
    }

    private void abrirGestionCandidatos() {
        FrmGestionCandidatos f = new FrmGestionCandidatos();
        new GestionCandidatosControlador(f);
        f.setVisible(true);
    }

    private void abrirAuditoria() {
        FrmAuditoria f = new FrmAuditoria();
        new AuditoriaControlador(f);
        f.setVisible(true);
    }

    private void publicarEleccion() {
        try {
            int idEleccion = Integer.parseInt(vista.txtIdEleccion.getText().trim());
            boolean exito = dao.publicarEleccion(idEleccion);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "La elección " + idEleccion + " ha sido publicada.");
                vista.txtIdEleccion.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al publicar. Verifique que exista el ID Elección y que no esté ya activa.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un ID de elección numérico válido.");
        }
    }

    private void cerrarEleccion() {
        try {
            int idEleccion = Integer.parseInt(vista.txtIdEleccion.getText().trim());
            boolean exito = dao.cerrarEleccion(idEleccion);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "La elección " + idEleccion + " ha sido cerrada.");
                vista.txtIdEleccion.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cerrar. Verifique que exista el ID y esté activa.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un ID de elección numérico válido.");
        }
    }

    private void logout() {
        vista.dispose();
        FrmLogin fl = new FrmLogin();
        new LoginControlador(fl);
        fl.setVisible(true);
    }
}
