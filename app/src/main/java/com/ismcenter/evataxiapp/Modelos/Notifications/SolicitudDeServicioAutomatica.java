package com.ismcenter.evataxiapp.Modelos.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hector on 13/12/16.
 */

public class SolicitudDeServicioAutomatica implements Notificacion {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("id_pasajero")
    @Expose
    private String idPasajero;
    @SerializedName("telef")
    @Expose
    private String telef;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("coordDest")
    @Expose
    private String coordDest;
    @SerializedName("coordOrig")
    @Expose
    private String coordOrig;
    @SerializedName("tipo_de_notificacion")
    @Expose
    private String tipo_de_notificacion;
    @SerializedName("id_solicitud")
    @Expose
    private String id_solicitud;
    @SerializedName("is_favorito")
    @Expose
    private String is_favorito;

    @Override
    public String toString() {
        return "SolicitudDeServicioAutomatica{" +
                "nombre='" + nombre + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idPasajero='" + idPasajero + '\'' +
                ", telef='" + telef + '\'' +
                ", title='" + title + '\'' +
                ", coordDest='" + coordDest + '\'' +
                ", coordOrig='" + coordOrig + '\'' +
                ", tipo_de_notificacion='" + tipo_de_notificacion + '\'' +
                ", id_solicitud='" + id_solicitud + '\'' +
                ", is_favorito='" + is_favorito + '\'' +
                '}';
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoordDest() {
        return coordDest;
    }

    public void setCoordDest(String coordDest) {
        this.coordDest = coordDest;
    }

    public String getCoordOrig() {
        return coordOrig;
    }

    public void setCoordOrig(String coordOrig) {
        this.coordOrig = coordOrig;
    }

    @Override
    public String getTipo() {
        return "SolicitudDeServicioAutomatica";
    }

    public String getTipo_de_notificacion() {
        return tipo_de_notificacion;
    }

    public void setTipo_de_notificacion(String tipo_de_notificacion) {
        this.tipo_de_notificacion = tipo_de_notificacion;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getIs_favorito() {
        return is_favorito;
    }

    public void setIs_favorito(String is_favorito) {
        this.is_favorito = is_favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}