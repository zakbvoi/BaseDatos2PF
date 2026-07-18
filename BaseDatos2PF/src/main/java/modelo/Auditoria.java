package modelo;

import java.sql.Timestamp;

public class Auditoria {
    private String tablaAfectada;
    private String tipoAccion;
    private int totalAcciones;
    private Timestamp ultimaAccion;

    public Auditoria() {
    }

    public Auditoria(String tablaAfectada, String tipoAccion, int totalAcciones, Timestamp ultimaAccion) {
        this.tablaAfectada = tablaAfectada;
        this.tipoAccion = tipoAccion;
        this.totalAcciones = totalAcciones;
        this.ultimaAccion = ultimaAccion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public int getTotalAcciones() {
        return totalAcciones;
    }

    public void setTotalAcciones(int totalAcciones) {
        this.totalAcciones = totalAcciones;
    }

    public Timestamp getUltimaAccion() {
        return ultimaAccion;
    }

    public void setUltimaAccion(Timestamp ultimaAccion) {
        this.ultimaAccion = ultimaAccion;
    }
}
