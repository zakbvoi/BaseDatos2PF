package modelo;

import java.sql.Timestamp;

public class Eleccion {
    private int idEleccion;
    private int idContrato;
    private int idTipo;
    private int idJurisdiccion;
    private String titulo;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String estado;

    public Eleccion() {
    }

    public Eleccion(int idEleccion, String titulo, Timestamp fechaInicio, Timestamp fechaFin, String estado) {
        this.idEleccion = idEleccion;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Eleccion(int idEleccion, int idContrato, int idTipo, int idJurisdiccion, String titulo, Timestamp fechaInicio, Timestamp fechaFin, String estado) {
        this.idEleccion = idEleccion;
        this.idContrato = idContrato;
        this.idTipo = idTipo;
        this.idJurisdiccion = idJurisdiccion;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdEleccion() {
        return idEleccion;
    }

    public void setIdEleccion(int idEleccion) {
        this.idEleccion = idEleccion;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdJurisdiccion() {
        return idJurisdiccion;
    }

    public void setIdJurisdiccion(int idJurisdiccion) {
        this.idJurisdiccion = idJurisdiccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return titulo + " (" + estado + ")";
    }
}
