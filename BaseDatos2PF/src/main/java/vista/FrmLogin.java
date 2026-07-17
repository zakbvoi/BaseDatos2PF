package vista;

import javax.swing.*;
import java.awt.*;

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public FrmLogin() {
        setTitle("Sistema de Votación - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(3, 2, 10, 15));

        panelPrincipal.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelPrincipal.add(txtUsuario);
        
        panelPrincipal.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelPrincipal.add(txtPassword);
        
        panelPrincipal.add(new JLabel()); 
        btnIngresar = new JButton("Ingresar");
        panelPrincipal.add(btnIngresar);

        add(panelPrincipal);
    }

    // GETTERS para que el Controlador pueda acceder a los componentes
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }
}