package vista;

import javax.swing.*;
import java.awt.*;

public class FrmResultados extends JFrame {

    private JTextField txtIdEleccion;
    public JButton btnConsultar;
    public JTextArea txtAreaResultados;

    public FrmResultados() {
        setTitle("Sistema de Votación - Resultados en Vivo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        // Panel superior para ingresar el ID y el botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("ID Elección:"));
        
        txtIdEleccion = new JTextField(10);
        panelSuperior.add(txtIdEleccion);
        
        btnConsultar = new JButton("Consultar");
        panelSuperior.add(btnConsultar);

        // Área de texto en el centro para mostrar los resultados
        txtAreaResultados = new JTextArea();
        txtAreaResultados.setEditable(false); // Para que el usuario no pueda escribir aquí
        txtAreaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente monoespaciada para que se vea alineado
        txtAreaResultados.setMargin(new Insets(10, 10, 10, 10));
        
        // Le agregamos un scroll por si hay muchos candidatos
        JScrollPane scroll = new JScrollPane(txtAreaResultados);

        // Añadimos todo a la ventana principal
        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // Getter para que el controlador lea el ID
    public String getTxtIdEleccion() {
        return txtIdEleccion.getText().trim();
    }
}