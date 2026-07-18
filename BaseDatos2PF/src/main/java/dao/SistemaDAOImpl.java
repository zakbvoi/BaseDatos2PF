package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import modelo_conexion.ConexionDB;
import java.util.ArrayList;
import java.util.List;
import modelo.Eleccion;
import modelo.Candidato;
import modelo.Votante;
import modelo.Auditoria;


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
     * 4. Obtiene el conteo de votos de una elección específica llamando a fn_count_votes.
     */
    public String obtenerResultados(int idEleccion) {
        StringBuilder resultados = new StringBuilder();
        resultados.append("Resultados de la Elección ID: ").append(idEleccion).append("\n");
        resultados.append("------------------------------------------------\n");
        
        Connection con = ConexionDB.getConexion();
        String sql = "{? = call PKG_ELECTORAL_BUSINESS_LOGIC.fn_count_votes(?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.setInt(2, idEleccion);
            cs.execute();
            
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                boolean hayVotos = false;
                while (rs.next()) {
                    hayVotos = true;
                    String name = rs.getString("full_name");
                    int votos = rs.getInt("total_votes");
                    resultados.append(name).append(" : ").append(votos).append(" votos\n");
                }
                if (!hayVotos) {
                    resultados.append("Aún no hay votos registrados para esta elección.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener resultados: " + e.getMessage());
            if (e.getErrorCode() == 20401 || e.getMessage().contains("only be counted")) {
                return "Los resultados no están disponibles. La elección aún no ha sido cerrada o auditada.";
            }
            return "Ocurrió un error al calcular los resultados:\n" + e.getMessage();
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        
        return resultados.toString();
    }

    /**
     * Obtiene la lista de elecciones activas llamando a la función fn_get_active_elections
     */
    public List<Eleccion> listarEleccionesActivas() {
        List<Eleccion> lista = new ArrayList<>();
        Connection con = ConexionDB.getConexion();
        String sql = "{? = call PKG_ELECTION_MAINTENANCE.fn_get_active_elections}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.execute();
            
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Eleccion el = new Eleccion();
                    el.setIdEleccion(rs.getInt("election_id"));
                    el.setTitulo(rs.getString("title"));
                    el.setFechaInicio(rs.getTimestamp("start_date"));
                    el.setFechaFin(rs.getTimestamp("end_date"));
                    el.setEstado("Activa");
                    lista.add(el);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener elecciones activas: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return lista;
    }

    /**
     * Obtiene la lista de candidatos por elección llamando a fn_list_candidates
     */
    public List<Candidato> listarCandidatosPorEleccion(int idEleccion) {
        List<Candidato> lista = new ArrayList<>();
        Connection con = ConexionDB.getConexion();
        String sql = "{? = call PKG_CANDIDATE_MAINTENANCE.fn_list_candidates(?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cs.setInt(2, idEleccion);
            cs.execute();
            
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Candidato cand = new Candidato();
                    cand.setIdCandidato(rs.getInt("candidate_id"));
                    cand.setNombreCompleto(rs.getString("full_name"));
                    cand.setIdEleccion(idEleccion);
                    
                    int listId = rs.getInt("list_id");
                    if (!rs.wasNull()) {
                        cand.setIdLista(listId);
                    }
                    lista.add(cand);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener candidatos: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return lista;
    }

    /**
     * Crea una nueva elección llamando a PKG_ELECTION_MAINTENANCE.sp_create_election
     */
    public boolean crearEleccion(int contractId, int typeId, int jurisdictionId, String title, java.sql.Timestamp start, java.sql.Timestamp end) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        String sql = "{call PKG_ELECTION_MAINTENANCE.sp_create_election(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, contractId);
            cs.setInt(2, typeId);
            cs.setInt(3, jurisdictionId);
            cs.setString(4, title);
            cs.setTimestamp(5, start);
            cs.setTimestamp(6, end);
            cs.setString(7, "Creada");
            
            cs.registerOutParameter(8, Types.INTEGER);
            
            cs.execute();
            exito = true;
            int generatedId = cs.getInt(8);
            System.out.println("Elección creada con ID: " + generatedId);
        } catch (SQLException e) {
            System.err.println("Error al crear elección: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return exito;
    }

    /**
     * Registra un candidato llamando a PKG_CANDIDATE_MAINTENANCE.sp_register_candidate
     */
    public boolean registrarCandidato(int electionId, Integer listId, String fullName) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        String sql = "{call PKG_CANDIDATE_MAINTENANCE.sp_register_candidate(?, ?, ?, ?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, electionId);
            if (listId != null) {
                cs.setInt(2, listId);
            } else {
                cs.setNull(2, Types.INTEGER);
            }
            cs.setString(3, fullName);
            cs.registerOutParameter(4, Types.INTEGER);
            
            cs.execute();
            exito = true;
            int generatedId = cs.getInt(4);
            System.out.println("Candidato registrado con ID: " + generatedId);
        } catch (SQLException e) {
            System.err.println("Error al registrar candidato: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return exito;
    }

    /**
     * Registra votantes y los inscribe en la elección
     */
    public boolean registrarVotanteMasivo(List<Votante> votantes, int electionId) {
        boolean exito = true;
        Connection con = ConexionDB.getConexion();
        
        String sqlVoter = "{call PKG_VOTER_ROLL_MAINTENANCE.sp_register_voter(?, ?, ?, ?, ?)}";
        String sqlEnroll = "{call PKG_VOTER_ROLL_MAINTENANCE.sp_enroll_voter(?, ?, ?)}";
        
        try {
            con.setAutoCommit(false); // Iniciar transacción
            
            for (Votante v : votantes) {
                int generatedVoterId = 0;
                
                try (CallableStatement cs = con.prepareCall(sqlVoter)) {
                    cs.setInt(1, v.getIdJurisdiccion());
                    cs.setString(2, v.getDni());
                    cs.setString(3, v.getNombreCompleto());
                    cs.setDate(4, v.getFechaNacimiento());
                    cs.registerOutParameter(5, Types.INTEGER);
                    
                    cs.execute();
                    generatedVoterId = cs.getInt(5);
                } catch (SQLException ex) {
                    if (ex.getMessage().contains("already registered") || ex.getErrorCode() == 20011) {
                        String findSql = "SELECT voter_id FROM tb_voter WHERE national_id = ?";
                        try (PreparedStatement ps = con.prepareStatement(findSql)) {
                            ps.setString(1, v.getDni());
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    generatedVoterId = rs.getInt("voter_id");
                                }
                            }
                        }
                    } else {
                        throw ex;
                    }
                }
                
                if (generatedVoterId > 0) {
                    try (CallableStatement csEnroll = con.prepareCall(sqlEnroll)) {
                        csEnroll.setInt(1, electionId);
                        csEnroll.setInt(2, generatedVoterId);
                        csEnroll.setString(3, "Habilitado");
                        csEnroll.execute();
                    } catch (SQLException ex) {
                        if (ex.getMessage().contains("already enrolled") || ex.getErrorCode() == 20012) {
                            // Ignorar si ya está enrolado
                        } else {
                            throw ex;
                        }
                    }
                }
            }
            
            con.commit();
            System.out.println("Carga masiva completada con éxito.");
        } catch (SQLException e) {
            exito = false;
            System.err.println("Error en la carga masiva: " + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {}
            ConexionDB.cerrarConexion(con);
        }
        
        return exito;
    }

    /**
     * Cierra una elección llamando a PKG_ELECTORAL_BUSINESS_LOGIC.sp_close_election
     */
    public boolean cerrarEleccion(int idEleccion) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        String sql = "{call PKG_ELECTORAL_BUSINESS_LOGIC.sp_close_election(?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idEleccion);
            cs.execute();
            exito = true;
            System.out.println("Elección " + idEleccion + " cerrada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al cerrar elección: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return exito;
    }

    /**
     * Obtiene el reporte de auditoría de la vista vw_audit_summary
     */
    public List<Auditoria> obtenerReporteAuditoria() {
        List<Auditoria> lista = new ArrayList<>();
        Connection con = ConexionDB.getConexion();
        String sql = "SELECT affected_table, action_type, total_actions, last_action FROM vw_audit_summary";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Auditoria aud = new Auditoria();
                aud.setTablaAfectada(rs.getString("affected_table"));
                aud.setTipoAccion(rs.getString("action_type"));
                aud.setTotalAcciones(rs.getInt("total_actions"));
                aud.setUltimaAccion(rs.getTimestamp("last_action"));
                lista.add(aud);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener auditorías: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return lista;
    }

    /**
     * Establece el ID de empleado para la sesión de auditoría actual en la base de datos
     */
    public boolean establecerUsuarioAuditor(int employeeId) {
        boolean exito = false;
        Connection con = ConexionDB.getConexion();
        String sql = "{call PKG_AUDIT_CONTEXT.sp_set_current_employee(?)}";
        
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, employeeId);
            cs.execute();
            exito = true;
            System.out.println("Empleado de sesión establecido: ID " + employeeId);
        } catch (SQLException e) {
            System.err.println("Error al establecer contexto de auditoría: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return exito;
    }

    /**
     * Obtiene los datos de los votos para generar un gráfico.
     */
    public java.util.Map<Integer, Integer> obtenerDatosVotos(int idEleccion) {
        java.util.Map<Integer, Integer> datos = new java.util.HashMap<>();
        Connection con = ConexionDB.getConexion();
        
        String sql = "SELECT id_candidato, COUNT(id_voto) AS total_votos " +
                     "FROM tb_voto " +
                     "WHERE id_eleccion = ? " +
                     "GROUP BY id_candidato";
                     
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEleccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    datos.put(rs.getInt("id_candidato"), rs.getInt("total_votos"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener datos de votos para gráfico: " + e.getMessage());
        } finally {
            ConexionDB.cerrarConexion(con);
        }
        return datos;
    }
}