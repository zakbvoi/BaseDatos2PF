package controlador;

import dao.SistemaDAOImpl;
import vista.FrmResultados;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ResultadosControlador {
    
    private FrmResultados vista;
    private SistemaDAOImpl dao;

    public ResultadosControlador(FrmResultados vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();
        this.vista.btnConsultar.addActionListener(e -> mostrarResultados());
    }

    private void mostrarResultados() {
        String idTexto = vista.getTxtIdEleccion();
        if (idTexto.isEmpty()) return;
        
        int idEleccion = Integer.parseInt(idTexto);
        
        // 1. Mostrar texto
        vista.txtAreaResultados.setText(dao.obtenerResultados(idEleccion));
        
        // 2. Generar gráfico dinámico
        generarGrafico(idEleccion);
    }

    private void generarGrafico(int idEleccion) {
        java.util.Map<Integer, Integer> datos = dao.obtenerDatosVotos(idEleccion);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (java.util.Map.Entry<Integer, Integer> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), "Votos", "Cand. " + entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Resultados Elección " + idEleccion, "Candidatos", "Votos", dataset);

        JFrame ventanaGrafico = new JFrame("Gráfico de Resultados");
        ventanaGrafico.add(new ChartPanel(chart));
        ventanaGrafico.setSize(500, 400);
        ventanaGrafico.setLocationRelativeTo(null);
        ventanaGrafico.setVisible(true);
    }
}