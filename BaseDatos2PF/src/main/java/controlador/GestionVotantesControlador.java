package controlador;

import dao.SistemaDAOImpl;
import modelo.Votante;
import vista.FrmGestionVotantes;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GestionVotantesControlador {
    private FrmGestionVotantes vista;
    private SistemaDAOImpl dao;

    public GestionVotantesControlador(FrmGestionVotantes vista) {
        this.vista = vista;
        this.dao = new SistemaDAOImpl();

        this.vista.btnRegistrar.addActionListener(e -> registrarIndividual());
        this.vista.btnCargarCsv.addActionListener(e -> cargarCsvMasivo());
        this.vista.btnVolver.addActionListener(e -> this.vista.dispose());
    }

    private void registrarIndividual() {
        try {
            int eleccionId = Integer.parseInt(vista.txtEleccionId.getText().trim());
            String dni = vista.txtDni.getText().trim();
            String name = vista.txtNombreCompleto.getText().trim();
            String dateStr = vista.txtFechaNacimiento.getText().trim();
            int jurId = Integer.parseInt(vista.txtJurisdiccionId.getText().trim());

            if (dni.isEmpty() || name.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Complete todos los campos.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = new Date(sdf.parse(dateStr).getTime());

            Votante v = new Votante(0, dni, name, birthDate, jurId);
            List<Votante> lista = new ArrayList<>();
            lista.add(v);

            boolean exito = dao.registrarVotanteMasivo(lista, eleccionId);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Votante registrado y matriculado con éxito.");
                vista.txtDni.setText("");
                vista.txtNombreCompleto.setText("");
                vista.txtFechaNacimiento.setText("");
                vista.txtJurisdiccionId.setText("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar. Verifique duplicados en DNI o ID Elección.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los IDs deben ser números enteros.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha inválido (debe ser yyyy-MM-dd).");
        }
    }

    private void cargarCsvMasivo() {
        String eleccionIdStr = JOptionPane.showInputDialog(vista, "Ingrese el ID de la Elección para matricular este padrón:");
        if (eleccionIdStr == null || eleccionIdStr.trim().isEmpty()) {
            return;
        }

        int eleccionId;
        try {
            eleccionId = Integer.parseInt(eleccionIdStr.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID de elección inválido.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione archivo CSV de votantes");
        int userSelection = fileChooser.showOpenDialog(vista);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            List<Votante> votantes = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try (BufferedReader br = new BufferedReader(new FileReader(fileToOpen))) {
                String line;
                // Asumimos primera línea opcional cabecera, lo detectamos
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] data = line.split(",");
                    
                    // Si es cabecera (por ejemplo si contiene "dni" o "DNI" en el primer campo)
                    if (isHeader) {
                        isHeader = false;
                        if (data[0].toLowerCase().contains("dni") || data[0].toLowerCase().contains("national_id")) {
                            continue; // Ignorar cabecera
                        }
                    }
                    
                    if (data.length < 4) {
                        System.err.println("Línea mal formateada: " + line);
                        continue;
                    }

                    String dni = data[0].trim();
                    String name = data[1].trim();
                    String dateStr = data[2].trim();
                    int jurId = Integer.parseInt(data[3].trim());

                    Date birthDate = new Date(sdf.parse(dateStr).getTime());
                    votantes.add(new Votante(0, dni, name, birthDate, jurId));
                }

                if (votantes.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "El archivo CSV no contiene registros válidos.");
                    return;
                }

                boolean exito = dao.registrarVotanteMasivo(votantes, eleccionId);

                if (exito) {
                    JOptionPane.showMessageDialog(vista, "¡Carga masiva completada! Registrados " + votantes.size() + " votantes.");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error en la carga masiva. Verifique los logs en la base de datos.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al procesar el archivo CSV: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
