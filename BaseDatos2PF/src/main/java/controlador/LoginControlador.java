package controlador;

import dao.SistemaDAOImpl;
import modelo.Usuario;
import vista.FrmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.FrmAdmin; // Para la navegación

public class LoginControlador implements ActionListener {
    
    private Usuario modeloUsuario;
    private FrmLogin vistaLogin;
    private SistemaDAOImpl dao;

    // Constructor: Recibe el modelo, la vista y el DAO para conectarlos
    public LoginControlador(Usuario modeloUsuario, FrmLogin vistaLogin, SistemaDAOImpl dao) {
        this.modeloUsuario = modeloUsuario;
        this.vistaLogin = vistaLogin;
        this.dao = dao;
        
        // Le decimos al botón "Ingresar" que este controlador escuchará sus clics
        this.vistaLogin.getBtnIngresar().addActionListener(this);
    }

    // Este método se ejecuta automáticamente cuando hacen clic en el botón
    @Override
    public void actionPerformed(ActionEvent e) {
        // Si el botón presionado es el de ingresar...
        if (e.getSource() == vistaLogin.getBtnIngresar()) {
            
            // 1. Extraemos los datos de la Vista
            String username = vistaLogin.getTxtUsuario().getText();
            String password = new String(vistaLogin.getTxtPassword().getPassword());
            
            // 2. Validamos que no estén vacíos
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(vistaLogin, "Por favor, complete todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // 3. Guardamos los datos en el Modelo
            modeloUsuario.setUsername(username);
            modeloUsuario.setPassword(password);
            
            // 4. Se los pasamos al DAO para consultar a Oracle
            boolean esValido = dao.validarLogin(modeloUsuario.getUsername(), modeloUsuario.getPassword());
            
            // 5. Respondemos en la Vista
            if (esValido) {
                JOptionPane.showMessageDialog(vistaLogin, "¡Bienvenido al Sistema de Votación!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Navegación: Cerramos el Login y abrimos el FrmAdmin
                vistaLogin.dispose();
                FrmAdmin admin = new FrmAdmin();
                admin.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(vistaLogin, "Usuario o contraseña incorrectos.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}