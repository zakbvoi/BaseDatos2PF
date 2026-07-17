package controlador;

import dao.SistemaDAOImpl;
import vista.FrmRegistro;
import javax.swing.JOptionPane;
import vista.FrmLogin;

public class RegistroControlador {
    
    private FrmRegistro vista;
    private SistemaDAOImpl dao;

    public RegistroControlador(FrmRegistro vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // 1. Escuchar el botón de registrar
        this.vista.btnRegistrar.addActionListener(e -> procesarRegistro());
        
        // 2. Escuchar el botón de volver
        this.vista.btnVolverLogin.addActionListener(e -> volverAlLogin());
    }

    private void procesarRegistro() {
        String username = vista.getTxtUsername();
        String password = vista.getTxtPassword();

        // Validamos que no dejen campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Llamamos al DAO
        boolean exito = dao.registrarUsuario(username, password);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "¡Cuenta creada con éxito! Ya puedes iniciar sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            volverAlLogin(); // Lo regresamos al login automáticamente
        } else {
            JOptionPane.showMessageDialog(vista, "Error al crear la cuenta. Es posible que el usuario ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAlLogin() {
        vista.dispose(); // Cerramos registro
        FrmLogin vistaLogin = new FrmLogin();
        new LoginControlador(vistaLogin); // Volvemos a levantar el hub principal
        vistaLogin.setVisible(true);
    }
}