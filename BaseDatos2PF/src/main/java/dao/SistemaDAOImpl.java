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
}