package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import modelo_conexion.ConexionDB;

public class SistemaDAOImpl {

    /**
     * 1. Valida las credenciales del usuario en la base de datos.
     */
    public boolean validarLogin(String username, String password) {
        boolean esValido = false;
        Connection con = ConexionDB.getConexion();
        
        String sql = "SELECT account_id FROM tb_user_account WHERE username = ? AND password_hash = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    esValido = true;
                    System.out.println("¡Login exitoso para el usuario: " + username + "!");
                } else {
                    System.out.println("Credenciales incorrectas.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el login: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        
        return esValido;
    }

    /**
     * 2. Llama al procedimiento almacenado para registrar un voto.
     */
    public String registrarVoto(int idEleccion, int idVotante, int idCandidato) {
        String comprobante = null;
        Connection con = ConexionDB.getConexion();
        
        String sql = "{CALL PKG_ELECTORAL_BUSINESS_LOGIC.sp_cast_vote(?, ?, ?, ?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setInt(1, idEleccion);
            cs.setInt(2, idVotante);
            cs.setInt(3, idCandidato);
            
            cs.registerOutParameter(4, Types.VARCHAR);
            
            cs.execute();
            
            comprobante = cs.getString(4);
            System.out.println("Voto registrado con éxito. Comprobante: " + comprobante);
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el voto: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        
        return comprobante;
    }

    /**
     * 3. Llama al procedimiento almacenado para publicar una elección.
     */
    public boolean publicarEleccion(int idEleccion) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        
        String sql = "{CALL PKG_ELECTORAL_BUSINESS_LOGIC.sp_publish_election(?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setInt(1, idEleccion);
            cs.execute();
            
            exito = true;
            System.out.println("Elección " + idEleccion + " publicada con éxito (Estado: Activa).");
            
        } catch (SQLException e) {
            System.err.println("Error al publicar la elección: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        
        return exito;
    }
    
    /**
     * Registra un nuevo elector en el sistema.
     * Retorna true si se guardó con éxito, false si hubo un error (ej. usuario duplicado).
     */
    public boolean registrarUsuario(String username, String password) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        
        // Usamos un INSERT básico para guardar al nuevo elector
        String sql = "INSERT INTO tb_user_account (username, password_hash) VALUES (?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                exito = true;
                System.out.println("Nuevo elector registrado con éxito: " + username);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el elector: " + e.getMessage());
            // Si el error es por usuario duplicado (violación de UNIQUE), caerá aquí
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        
        return exito;
    }
    
    /**
     * 4. Obtiene el conteo de votos de una elección específica.
     */
    public String obtenerResultados(int idEleccion) {
        StringBuilder resultados = new StringBuilder();
        resultados.append("Resultados de la Elección ID: ").append(idEleccion).append("\n");
        resultados.append("------------------------------------------------\n");
        
        java.sql.Connection con = modelo_conexion.ConexionDB.getConexion();
        
        String sql = "SELECT id_candidato, COUNT(id_voto) AS total_votos " +
                     "FROM tb_voto " +
                     "WHERE id_eleccion = ? " +
                     "GROUP BY id_candidato " +
                     "ORDER BY total_votos DESC";
                     
        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEleccion);
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                boolean hayVotos = false;
                while (rs.next()) {
                    hayVotos = true;
                    int candidato = rs.getInt("id_candidato");
                    int votos = rs.getInt("total_votos");
                    resultados.append("Candidato ID [").append(candidato).append("] : ")
                              .append(votos).append(" votos\n");
                }
                if (!hayVotos) {
                    resultados.append("Aún no hay votos registrados para esta elección.");
                }
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al obtener resultados: " + e.getMessage());
            return "Ocurrió un error al calcular los resultados.";
        } finally {
            modelo_conexion.ConexionDB.cerrarConexion(con);
        }
        
        return resultados.toString();
    }
    
    /**
     * MEJORA 2: Verifica si un elector ya emitió su voto en una elección específica.
     * Retorna true si ya votó, false si es su primera vez.
     */
    public boolean yaVoto(int idEleccion, int idVotante) {
        boolean existe = false;
        java.sql.Connection con = modelo_conexion.ConexionDB.getConexion();
        
        // Buscamos si existe al menos un registro con ese ID de elección y votante
        String sql = "SELECT 1 FROM tb_voto WHERE id_eleccion = ? AND id_votante = ?";
        
        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEleccion);
            ps.setInt(2, idVotante);
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true; // Si entra aquí, es porque encontró un voto previo
                }
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al verificar doble voto: " + e.getMessage());
        } finally {
            modelo_conexion.ConexionDB.cerrarConexion(con);
        }
        
        return existe;
    }
    
    public java.util.Map<Integer, Integer> obtenerDatosVotos(int idEleccion) {
        java.util.Map<Integer, Integer> datos = new java.util.HashMap<>();
        java.sql.Connection con = modelo_conexion.ConexionDB.getConexion();
        String sql = "SELECT id_candidato, COUNT(id_voto) as total FROM tb_voto WHERE id_eleccion = ? GROUP BY id_candidato";
        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEleccion);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    datos.put(rs.getInt("id_candidato"), rs.getInt("total"));
            }
        }
        } catch (java.sql.SQLException e) {
        e.printStackTrace();
        } finally {
        modelo_conexion.ConexionDB.cerrarConexion(con);
        }
        return datos;
    }
    

    
}
