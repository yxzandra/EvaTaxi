package com.ismcenter.evataxiapp.Modelos;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataBanco {

    private Integer ban_id;
    private String ban_rif;
    private String ban_nombre;
    private Integer ban_estatus;

    public Integer getBanId() {
        return ban_id;
    }

    public void setBanId(Integer banId) {
        this.ban_id = banId;
    }

    public String getBanRif() {
        return ban_rif;
    }

    public void setBanRif(String banRif) {
        this.ban_rif = banRif;
    }

    public String getBanNombre() {
        return ban_nombre;
    }

    public void setBanNombre(String banNombre) {
        this.ban_nombre = banNombre;
    }

    public Integer getBanEstatus() {
        return ban_estatus;
    }

    public void setBanEstatus(Integer banEstatus) {
        this.ban_estatus = banEstatus;
    }

}