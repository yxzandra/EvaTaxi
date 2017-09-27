package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseDestinoByOrigen {

    public Integer tarifa;
    public String zon_nombre;
    public String tipo_tarifa;

    public ResponseDestinoByOrigen(Integer tarifa, String zon_nombre, String tipo_tarifa) {
        this.tarifa = tarifa;
        this.zon_nombre = zon_nombre;
        this.tipo_tarifa = tipo_tarifa;
    }

    public Integer getTarifa() {
        return tarifa;
    }

    public void setTarifa(Integer tarifa) {
        this.tarifa = tarifa;
    }

    public String getZon_nombre() {
        return zon_nombre;
    }

    public void setZon_nombre(String zon_nombre) {
        this.zon_nombre = zon_nombre;
    }

    public String getTipo_tarifa() {
        return tipo_tarifa;
    }

    public void setTipo_tarifa(String tipo_tarifa) {
        this.tipo_tarifa = tipo_tarifa;
    }
}