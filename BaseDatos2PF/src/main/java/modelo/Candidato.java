package modelo;

public class Candidato {
    private int idCandidato;
    private int idEleccion;
    private Integer idLista; // Puede ser nulo para candidatos independientes
    private String nombreCompleto;

    public Candidato() {
    }

    public Candidato(int idCandidato, int idEleccion, Integer idLista, String nombreCompleto) {
        this.idCandidato = idCandidato;
        this.idEleccion = idEleccion;
        this.idLista = idLista;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public int getIdEleccion() {
        return idEleccion;
    }

    public void setIdEleccion(int idEleccion) {
        this.idEleccion = idEleccion;
    }

    public Integer getIdLista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return nombreCompleto + (idLista != null ? " (Lista ID: " + idLista + ")" : " (Independiente)");
    }
}
