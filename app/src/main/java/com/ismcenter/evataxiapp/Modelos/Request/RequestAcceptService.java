package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by yxzan on 21/12/2016.
 */
public class RequestAcceptService {
    private String id_solicitud;
    private String id_usuario;

    public RequestAcceptService(String id_solicitud, String id_usuario) {
        this.id_solicitud = id_solicitud;
        this.id_usuario = id_usuario;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "RequestAcceptService{" +
                "id_solicitud='" + id_solicitud + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                '}';
    }
}
