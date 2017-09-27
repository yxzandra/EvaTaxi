package com.ismcenter.evataxiapp.Modelos.Response;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResponseBancos {


    public Integer ban_id;
    public String ban_rif;
    public String ban_nombre;
    public Integer ban_estatus;

    public Integer getBan_id() {
        return ban_id;
    }

    public void setBan_id(Integer ban_id) {
        this.ban_id = ban_id;
    }

    public String getBan_rif() {
        return ban_rif;
    }

    public void setBan_rif(String ban_rif) {
        this.ban_rif = ban_rif;
    }

    public String getBan_nombre() {
        return ban_nombre;
    }

    public void setBan_nombre(String ban_nombre) {
        this.ban_nombre = ban_nombre;
    }

    public Integer getBan_estatus() {
        return ban_estatus;
    }

    public void setBan_estatus(Integer ban_estatus) {
        this.ban_estatus = ban_estatus;
    }

    @Override
    public String toString() {
        return "ResponseBancos{" +
                "ban_id=" + ban_id +
                ", ban_rif='" + ban_rif + '\'' +
                ", ban_nombre='" + ban_nombre + '\'' +
                ", ban_estatus=" + ban_estatus +
                '}';
    }
}