package com.ismcenter.evataxiapp.Modelos;

/**
 * Created by hector on 25/12/16.
 */

public class Coordenada {
    private Double latitud;
    private Double longitud;

    public Coordenada(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return ""+latitud+";"+longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
