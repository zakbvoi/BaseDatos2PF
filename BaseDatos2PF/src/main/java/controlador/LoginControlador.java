package controlador;

import dao.SistemaDAOImpl;
import vista.FrmLogin;
import vista.FrmAdmin;
import vista.FrmVotacion;
import vista.FrmRegistro;
import vista.FrmResultados; // Importamos la nueva vista
import javax.swing.JOptionPane;

public class LoginControlador {

    private FrmLogin vista;
    private SistemaDAOImpl dao;

    public LoginControlador(FrmLogin vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // Conectamos los 3 botones
        this.vista.btnIngresar.addActionListener(e -> procesarLogin());
        this.vista.btnRegistrar.addActionListener(e -> abrirRegistro());
        this.vista.btnVerResultados.addActionListener(e -> abrirResultados());
    }

    private void procesarLogin() {
        String username = vista.getTxtUsername();
        String password = vista.getTxtPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Llene todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean esValido = dao.validarLogin(username, password);

        if (esValido) {
            vista.dispose();

            if (username.equals("admin")) {
                FrmAdmin vistaAdmin = new FrmAdmin();
                new AdminControlador(vistaAdmin);
                vistaAdmin.setVisible(true);
            } else {
                FrmVotacion vistaVotacion = new FrmVotacion();
                new VotacionControlador(vistaVotacion); 
                vistaVotacion.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        vista.dispose(); 
        FrmRegistro vistaRegistro = new FrmRegistro();
        new RegistroControlador(vistaRegistro); 
        vistaRegistro.setVisible(true);
    }
    
    // Método para abrir la ventana de resultados
    private void abrirResultados() {
        FrmResultados vistaResultados = new FrmResultados();
        new ResultadosControlador(vistaResultados);
        vistaResultados.setVisible(true);
    }
}