package com.ismcenter.evataxiapp.Modelos.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hector on 13/12/16.
 */

public class AceptacionDeSolicitudDeServicioAutomatica implements Notificacion {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("recep_fecha")
    @Expose
    private String recepFecha;
    @SerializedName("recep_conductor")
    @Expose
    private String recepConductor;
    @SerializedName("recep_serid")
    @Expose
    private String recepSerid;
    @SerializedName("usuario_conductor")
    @Expose
    private Object usuarioConductor;
    @SerializedName("telf_conductor")
    @Expose
    private String telfConductor;
    @SerializedName("nombre_conductor")
    @Expose
    private String nombreConductor;
    @SerializedName("fecha_ingreso_conductor")
    @Expose
    private String fechaIngresoConductor;
    @SerializedName("tipo_de_notificacion")
    @Expose
    private String tipo_de_notificacion;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTipo() {
        return "AceptacionDeSolicitudDeServicioAutomatica";
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getRecepFecha() {
        return recepFecha;
    }

    public void setRecepFecha(String recepFecha) {
        this.recepFecha = recepFecha;
    }

    public String getRecepConductor() {
        return recepConductor;
    }

    public void setRecepConductor(String recepConductor) {
        this.recepConductor = recepConductor;
    }

    public String getRecepSerid() {
        return recepSerid;
    }

    public void setRecepSerid(String recepSerid) {
        this.recepSerid = recepSerid;
    }

    public Object getUsuarioConductor() {
        return usuarioConductor;
    }

    public void setUsuarioConductor(Object usuarioConductor) {
        this.usuarioConductor = usuarioConductor;
    }

    public String getTelfConductor() {
        return telfConductor;
    }

    public void setTelfConductor(String telfConductor) {
        this.telfConductor = telfConductor;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getFechaIngresoConductor() {
        return fechaIngresoConductor;
    }

    public void setFechaIngresoConductor(String fechaIngresoConductor) {
        this.fechaIngresoConductor = fechaIngresoConductor;
    }


}