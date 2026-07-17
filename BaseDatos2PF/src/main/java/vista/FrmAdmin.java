package vista;

import dao.SistemaDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAdmin extends JFrame {

    private JTextField txtIdEleccion;
    private JButton btnPublicar;

    public FrmAdmin() {
        setTitle("Sistema de Votación - Panel de Administración");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new GridLayout(2, 2, 10, 15));

        // Componentes
        panelPrincipal.add(new JLabel("ID Elección a publicar:"));
        txtIdEleccion = new JTextField();
        panelPrincipal.add(txtIdEleccion);

        panelPrincipal.add(new JLabel()); // Espacio vacío para alinear el botón
        btnPublicar = new JButton("Publicar Elección");
        panelPrincipal.add(btnPublicar);

        add(panelPrincipal);

        // Acción del botón
        btnPublicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publicarEleccion();
            }
        });
    }

    private void publicarEleccion() {
        try {
            // 1. Recoger el ID ingresado
            int idEleccion = Integer.parseInt(txtIdEleccion.getText());

            // 2. Llamar al DAO
            SistemaDAOImpl dao = new SistemaDAOImpl();
            boolean exito = dao.publicarEleccion(idEleccion);

            // 3. Evaluar respuesta
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "¡La elección " + idEleccion + " ha sido publicada y está lista para recibir votos!", 
                    "Publicación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
                txtIdEleccion.setText(""); // Limpiar el campo
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al publicar. Verifica que la elección exista y cumpla los requisitos.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido.", "Error de formato", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmAdmin().setVisible(true);
            }
        });
    }
}