package controlador;

import dao.SistemaDAOImpl;
import vista.FrmResultados;
import javax.swing.JOptionPane;

public class ResultadosControlador {
    
    private FrmResultados vista;
    private SistemaDAOImpl dao;

    public ResultadosControlador(FrmResultados vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // Escuchamos el clic del botón consultar
        this.vista.btnConsultar.addActionListener(e -> mostrarResultados());
    }

    private void mostrarResultados() {
        try {
            String idTexto = vista.getTxtIdEleccion();
            
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un ID de elección.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idEleccion = Integer.parseInt(idTexto);
            
            // Llamamos al DAO que hicimos antes
            String resultados = dao.obtenerResultados(idEleccion);
            
            // Plasmamos el texto en el JTextArea de la vista
            vista.txtAreaResultados.setText(resultados);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}