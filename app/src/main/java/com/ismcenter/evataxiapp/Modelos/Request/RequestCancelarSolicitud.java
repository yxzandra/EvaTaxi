package com.ismcenter.evataxiapp.Modelos.Request;

public class RequestCancelarSolicitud {

    private String id_solicitud;

    public RequestCancelarSolicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }
}
