package vista;

import javax.swing.*;
import java.awt.*;

public class FrmLogin extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    
    // Aquí están declarados los TRES botones como públicos
    public JButton btnIngresar;
    public JButton btnRegistrar;
    public JButton btnVerResultados;

    public FrmLogin() {
        setTitle("Sistema de Votación - Inicio de Sesión");
        setSize(350, 250); // Lo hice un poco más alto para que quepa el nuevo botón
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Cambiamos a 4 filas para hacer espacio
        panelPrincipal.setLayout(new GridLayout(4, 2, 10, 15));

        panelPrincipal.add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        panelPrincipal.add(txtUsername);

        panelPrincipal.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelPrincipal.add(txtPassword);

        btnRegistrar = new JButton("Registrarse");
        panelPrincipal.add(btnRegistrar);

        btnIngresar = new JButton("Ingresar");
        panelPrincipal.add(btnIngresar);
        
        panelPrincipal.add(new JLabel()); // Espacio vacío para alinear
        
        btnVerResultados = new JButton("Ver Resultados");
        panelPrincipal.add(btnVerResultados);

        add(panelPrincipal);
    }

    public String getTxtUsername() {
        return txtUsername.getText().trim();
    }

    public String getTxtPassword() {
        return new String(txtPassword.getPassword());
    }
}