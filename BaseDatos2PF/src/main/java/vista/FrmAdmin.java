package vista;

import javax.swing.*;
import java.awt.*;

public class FrmAdmin extends JFrame {

    public JTextField txtIdEleccion;
    public JButton btnPublicar;
    public JButton btnCerrarEleccion;
    
    // Botones de navegación
    public JButton btnGestionarElecciones;
    public JButton btnGestionarVotantes;
    public JButton btnGestionarCandidatos;
    public JButton btnVerAuditorias;
    public JButton btnLogout;

    public FrmAdmin() {
        setTitle("Sistema de Votación - Panel de Administración");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        // Panel izquierdo: Botones de Gestión
        JPanel panelIzquierdo = new JPanel(new GridLayout(4, 1, 10, 10));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Módulos de Gestión"));

        btnGestionarElecciones = new JButton("Gestionar Elecciones");
        btnGestionarVotantes = new JButton("Gestionar Votantes (Padrón)");
        btnGestionarCandidatos = new JButton("Gestionar Candidatos");
        btnVerAuditorias = new JButton("Ver Panel de Auditoría");

        panelIzquierdo.add(btnGestionarElecciones);
        panelIzquierdo.add(btnGestionarVotantes);
        panelIzquierdo.add(btnGestionarCandidatos);
        panelIzquierdo.add(btnVerAuditorias);

        // Panel derecho: Acciones directas sobre elecciones (Publicar/Cerrar)
        JPanel panelDerecho = new JPanel(new GridLayout(5, 1, 10, 10));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Control del Proceso"));

        panelDerecho.add(new JLabel("ID Elección:"));
        txtIdEleccion = new JTextField();
        panelDerecho.add(txtIdEleccion);

        btnPublicar = new JButton("Publicar Elección");
        btnCerrarEleccion = new JButton("Cerrar Elección");
        btnLogout = new JButton("Cerrar Sesión");

        panelDerecho.add(btnPublicar);
        panelDerecho.add(btnCerrarEleccion);
        panelDerecho.add(btnLogout);

        // Layout principal
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.add(panelIzquierdo);
        panelPrincipal.add(panelDerecho);

        add(panelPrincipal);
    }
}