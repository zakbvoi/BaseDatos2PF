package controlador;

import dao.SistemaDAOImpl;
import vista.FrmAdmin;
import javax.swing.JOptionPane;

public class AdminControlador {
    
    private FrmAdmin vista;
    private SistemaDAOImpl dao;

    public AdminControlador(FrmAdmin vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        // Escuchamos el clic del botón
        this.vista.btnPublicar.addActionListener(e -> publicarEleccion());
    }

    private void publicarEleccion() {
        String idStr = vista.getTxtIdEleccion();
        
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un ID de elección.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int idEleccion = Integer.parseInt(idStr);
            
            // Llamamos al método que ya tenías creado en tu DAO
            boolean exito = dao.publicarEleccion(idEleccion);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista, 
                    "¡La elección " + idEleccion + " ha sido publicada y activada con éxito!", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al publicar la elección. Verifique la consola.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}