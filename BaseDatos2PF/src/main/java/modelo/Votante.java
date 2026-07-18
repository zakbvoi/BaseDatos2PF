package modelo;

import java.sql.Date;

public class Votante {
    private int idVotante;
    private String dni;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private int idJurisdiccion;

    public Votante() {
    }

    public Votante(int idVotante, String dni, String nombreCompleto, Date fechaNacimiento, int idJurisdiccion) {
        this.idVotante = idVotante;
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.idJurisdiccion = idJurisdiccion;
    }

    public int getIdVotante() {
        return idVotante;
    }

    public void setIdVotante(int idVotante) {
        this.idVotante = idVotante;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdJurisdiccion() {
        return idJurisdiccion;
    }

    public void setIdJurisdiccion(int idJurisdiccion) {
        this.idJurisdiccion = idJurisdiccion;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + dni + ")";
    }
}
