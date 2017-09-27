package com.ismcenter.evataxiapp.Modelos.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hector on 13/12/16.
 */

public class ConductorLlegoAOrigen implements Notificacion {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;

    @SerializedName("hora")
    @Expose
    private String hora;

    @SerializedName("ser_id")
    @Expose
    private String ser_id;

    @SerializedName("tipo_de_notificacion")
    @Expose
    private String tipo_de_notificacion;


    @Override
    public String getTipo() {
        return "ConductorLlegoAOrigen";
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getTipo_de_notificacion() {
        return tipo_de_notificacion;
    }

    public void setTipo_de_notificacion(String tipo_de_notificacion) {
        this.tipo_de_notificacion = tipo_de_notificacion;
    }
}