package modelo;

public class Voto {
    
    private int idEleccion;
    private int idVotante;
    private int idCandidato;
    private String comprobante;

    public Voto() {
    }

    public Voto(int idEleccion, int idVotante, int idCandidato) {
        this.idEleccion = idEleccion;
        this.idVotante = idVotante;
        this.idCandidato = idCandidato;
    }

    // Getters y Setters
    public int getIdEleccion() {
        return idEleccion;
    }

    public void setIdEleccion(int idEleccion) {
        this.idEleccion = idEleccion;
    }

    public int getIdVotante() {
        return idVotante;
    }

    public void setIdVotante(int idVotante) {
        this.idVotante = idVotante;
    }

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
}