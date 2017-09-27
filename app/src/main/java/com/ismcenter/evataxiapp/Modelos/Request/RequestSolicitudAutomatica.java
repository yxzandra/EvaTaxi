package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * El modelo de registro de usuario que requiere la api con el enlace http://api.evataxi.com.ve/api/users
 */
public class RequestSolicitudAutomatica {

    private String coordenada_ori;
            String coordenada_des;
            String id_pasajero;
            String usu_id_conductor_fav;
            String id_solicitud;

    public RequestSolicitudAutomatica(String coordenada_ori, String coordenada_des, String id_pasajero, String usu_id_conductor_fav, String id_solicitud) {
        this.coordenada_ori = coordenada_ori;
        this.coordenada_des = coordenada_des;
        this.id_pasajero = id_pasajero;
        this.usu_id_conductor_fav = usu_id_conductor_fav;
        this.id_solicitud = id_solicitud;
    }

    public String getCoordenada_ori() {
        return coordenada_ori;
    }

    public void setCoordenada_ori(String coordenada_ori) {
        this.coordenada_ori = coordenada_ori;
    }

    public String getCoordenada_des() {
        return coordenada_des;
    }

    public void setCoordenada_des(String coordenada_des) {
        this.coordenada_des = coordenada_des;
    }

    public String getId_pasajero() {
        return id_pasajero;
    }

    public void setId_pasajero(String id_pasajero) {
        this.id_pasajero = id_pasajero;
    }

    public String getUsu_id_conductor_fav() {
        return usu_id_conductor_fav;
    }

    public void setUsu_id_conductor_fav(String usu_id_conductor_fav) {
        this.usu_id_conductor_fav = usu_id_conductor_fav;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }
}
