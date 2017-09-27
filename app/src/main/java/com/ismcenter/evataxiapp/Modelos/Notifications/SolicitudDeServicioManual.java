package com.ismcenter.evataxiapp.Modelos.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hector on 13/12/16.
 */

public class SolicitudDeServicioManual implements Notificacion {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("coordOrig")
    @Expose
    private String coordOrig;
    @SerializedName("ser_origen")
    @Expose
    private String ser_origen;
    @SerializedName("ser_destino")
    @Expose
    private String ser_destino;
    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("id_pasajero")
    @Expose
    private String idPasajero;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("telef")
    @Expose
    private String telef;
    @SerializedName("coordDest")
    @Expose
    private String coordDest;
    @SerializedName("id_solicitud")
    @Expose
    private String id_solicitud;
    @SerializedName("tipo_de_notificacion")
    @Expose
    private String tipo_de_notificacion;

    public String getIs_favorito() {
        return is_favorito;
    }

    public void setIs_favorito(String is_favorito) {
        this.is_favorito = is_favorito;
    }

    @SerializedName("is_favorito")
    @Expose
    private String is_favorito;

    @Override
    public String toString() {
        return "SolicitudDeServicioManual{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", coordOrig='" + coordOrig + '\'' +
                ", ser_origen='" + ser_origen + '\'' +
                ", ser_destino='" + ser_destino + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idPasajero='" + idPasajero + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telef='" + telef + '\'' +
                ", coordDest='" + coordDest + '\'' +
                ", id_solicitud='" + id_solicitud + '\'' +
                ", tipo_de_notificacion='" + tipo_de_notificacion + '\'' +
                ", is_favorito='" + is_favorito + '\'' +
                '}';
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTipo() {
        return "SolicitudDeServicioManual";
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCoordOrig() {
        return coordOrig;
    }

    public void setCoordOrig(String coordOrig) {
        this.coordOrig = coordOrig;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public String getCoordDest() {
        return coordDest;
    }

    public void setCoordDest(String coordDest) {
        this.coordDest = coordDest;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getTipo_de_notificacion() {
        return tipo_de_notificacion;
    }

    public void setTipo_de_notificacion(String tipo_de_notificacion) {
        this.tipo_de_notificacion = tipo_de_notificacion;
    }
}