package com.ismcenter.evataxiapp.Modelos.Notifications;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by hector on 13/12/16.
 */
public class CancelacionDeSolicitud implements Notificacion {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("id_solicitud")
    @Expose
    private String id_solicitud;
    @SerializedName("motivo")
    @Expose
    private String motivo;
    @SerializedName("tipo_de_notificacion")
    @Expose
    private String tipo_de_notificacion;
    @Override
    public String getTipo() {
        return "CancelacionDeSolicitud";
    }
    @Override
    public String getSubTitle() {
        return this.subTitle;
    }
    @Override
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId_solicitud() {
        return id_solicitud;
    }
    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public String getTipo_de_notificacion() {
        return tipo_de_notificacion;
    }
    public void setTipo_de_notificacion(String tipo_de_notificacion) {
        this.tipo_de_notificacion = tipo_de_notificacion;
    }
    @Override
    public String toString() {
        return "CancelacionDeSolicitud{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", id_solicitud='" + id_solicitud + '\'' +
                ", motivo='" + motivo + '\'' +
                ", tipo_de_notificacion='" + tipo_de_notificacion + '\'' +
                '}';
    }
}