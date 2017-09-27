package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseOrigenByZona {

    public Integer tab_id;
    public Integer tab_zonaid;
    public Integer tab_valorMinimo;
    public Integer tab_valorMaximo;
    public Integer tab_estatus;
    public String tab_origen;
    public String tab_destino;
    public String tab_horainicio;
    public String tab_horafin;

    public ResponseOrigenByZona(Integer tab_id, Integer tab_zonaid, Integer tab_valorMinimo, Integer tab_valorMaximo, Integer tab_estatus, String tab_origen, String tab_destino, String tab_horainicio, String tab_horafin) {
        this.tab_id = tab_id;
        this.tab_zonaid = tab_zonaid;
        this.tab_valorMinimo = tab_valorMinimo;
        this.tab_valorMaximo = tab_valorMaximo;
        this.tab_estatus = tab_estatus;
        this.tab_origen = tab_origen;
        this.tab_destino = tab_destino;
        this.tab_horainicio = tab_horainicio;
        this.tab_horafin = tab_horafin;
    }

    public Integer getTab_id() {
        return tab_id;
    }

    public void setTab_id(Integer tab_id) {
        this.tab_id = tab_id;
    }

    public Integer getTab_zonaid() {
        return tab_zonaid;
    }

    public void setTab_zonaid(Integer tab_zonaid) {
        this.tab_zonaid = tab_zonaid;
    }

    public Integer getTab_valorMinimo() {
        return tab_valorMinimo;
    }

    public void setTab_valorMinimo(Integer tab_valorMinimo) {
        this.tab_valorMinimo = tab_valorMinimo;
    }

    public Integer getTab_valorMaximo() {
        return tab_valorMaximo;
    }

    public void setTab_valorMaximo(Integer tab_valorMaximo) {
        this.tab_valorMaximo = tab_valorMaximo;
    }

    public Integer getTab_estatus() {
        return tab_estatus;
    }

    public void setTab_estatus(Integer tab_estatus) {
        this.tab_estatus = tab_estatus;
    }

    public String getTab_origen() {
        return tab_origen;
    }

    public void setTab_origen(String tab_origen) {
        this.tab_origen = tab_origen;
    }

    public String getTab_destino() {
        return tab_destino;
    }

    public void setTab_destino(String tab_destino) {
        this.tab_destino = tab_destino;
    }

    public String getTab_horainicio() {
        return tab_horainicio;
    }

    public void setTab_horainicio(String tab_horainicio) {
        this.tab_horainicio = tab_horainicio;
    }

    public String getTab_horafin() {
        return tab_horafin;
    }

    public void setTab_horafin(String tab_horafin) {
        this.tab_horafin = tab_horafin;
    }

    @Override
    public String toString() {
        return "ResponseOrigenByZona{" +
                "tab_id=" + tab_id +
                ", tab_zonaid=" + tab_zonaid +
                ", tab_valorMinimo=" + tab_valorMinimo +
                ", tab_valorMaximo=" + tab_valorMaximo +
                ", tab_estatus=" + tab_estatus +
                ", tab_origen='" + tab_origen + '\'' +
                ", tab_destino='" + tab_destino + '\'' +
                ", tab_horainicio='" + tab_horainicio + '\'' +
                ", tab_horafin='" + tab_horafin + '\'' +
                '}';
    }
}