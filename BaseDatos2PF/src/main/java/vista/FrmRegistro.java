package vista;

import javax.swing.*;
import java.awt.*;

public class FrmRegistro extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    
    // Botones públicos para que el controlador los escuche
    public JButton btnRegistrar;
    public JButton btnVolverLogin;

    public FrmRegistro() {
        setTitle("Sistema de Votación - Nuevo Elector");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(3, 2, 10, 15));

        // Usuario
        panelPrincipal.add(new JLabel("Nuevo Usuario:"));
        txtUsername = new JTextField();
        panelPrincipal.add(txtUsername);

        // Contraseña
        panelPrincipal.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelPrincipal.add(txtPassword);

        // Botones
        btnVolverLogin = new JButton("Volver");
        panelPrincipal.add(btnVolverLogin);
        
        btnRegistrar = new JButton("Crear Cuenta");
        panelPrincipal.add(btnRegistrar);

        add(panelPrincipal);
    }

    // ========================================================
    // GETTERS PARA EL CONTROLADOR
    // ========================================================
    
    public String getTxtUsername() {
        return txtUsername.getText().trim();
    }

    public String getTxtPassword() {
        // Obtenemos la contraseña del JPasswordField
        return new String(txtPassword.getPassword());
    }
}