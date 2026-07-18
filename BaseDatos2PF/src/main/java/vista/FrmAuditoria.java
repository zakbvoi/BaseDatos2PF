package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmAuditoria extends JFrame {
    public JTable tblAuditoria;
    public DefaultTableModel modeloTabla;
    public JButton btnRefrescar;
    public JButton btnVolver;

    public FrmAuditoria() {
        setTitle("Panel de Auditoría y Transparencia");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Resumen de Auditoría de Base de Datos:"));

        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Tabla Afectada", "Tipo de Acción", "Total de Cambios", "Último Cambio"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar edición
            }
        };

        tblAuditoria = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tblAuditoria);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver");
        btnRefrescar = new JButton("Refrescar");

        panelInferior.add(btnVolver);
        panelInferior.add(btnRefrescar);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
}
