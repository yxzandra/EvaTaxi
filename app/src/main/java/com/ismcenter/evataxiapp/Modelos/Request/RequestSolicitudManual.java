package com.ismcenter.evataxiapp.Modelos.Request;

public class RequestSolicitudManual {

    public String id_pasajero;
    public String coordenada_ori;
    public String ser_origen;
    public String ser_destino;
    public String usu_id_conductor_fav;


    public RequestSolicitudManual(String id_pasajero, String coordenada_ori, String ser_origen, String ser_destino, String usu_id_conductor_fav) {
        this.id_pasajero = id_pasajero;
        this.coordenada_ori = coordenada_ori;
        this.ser_origen = ser_origen;
        this.ser_destino = ser_destino;
        this.usu_id_conductor_fav = usu_id_conductor_fav;
    }

    public String getId_pasajero() {
        return id_pasajero;
    }

    public void setId_pasajero(String id_pasajero) {
        this.id_pasajero = id_pasajero;
    }

    public String getCoordenada_ori() {
        return coordenada_ori;
    }

    public void setCoordenada_ori(String coordenada_ori) {
        this.coordenada_ori = coordenada_ori;
    }

    public String getSer_origen() {
        return ser_origen;
    }

    public void setSer_origen(String ser_origen) {
        this.ser_origen = ser_origen;
    }

    public String getSer_destino() {
        return ser_destino;
    }

    public void setSer_destino(String ser_destino) {
        this.ser_destino = ser_destino;
    }

    public String getUsu_id_conductor_fav() {
        return usu_id_conductor_fav;
    }

    public void setUsu_id_conductor_fav(String usu_id_conductor_fav) {
        this.usu_id_conductor_fav = usu_id_conductor_fav;
    }

    @Override
    public String toString() {
        return "RequestSolicitudManual{" +
                "id_pasajero='" + id_pasajero + '\'' +
                ", coordenada_ori='" + coordenada_ori + '\'' +
                ", ser_origen='" + ser_origen + '\'' +
                ", ser_destino='" + ser_destino + '\'' +
                ", usu_id_conductor_fav='" + usu_id_conductor_fav + '\'' +
                '}';
    }
}
