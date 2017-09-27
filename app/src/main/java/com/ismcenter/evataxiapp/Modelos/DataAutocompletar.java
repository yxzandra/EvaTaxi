package com.ismcenter.evataxiapp.Modelos;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataAutocompletar {

    private Integer zon_id;
    private String zon_nombre;
    private String zon_postal;
    private Integer par_id;
    private Integer est_id;
    private Integer ciu_id;

    public DataAutocompletar(Integer zon_id, String zon_nombre, String zon_postal, Integer par_id, Integer est_id, Integer ciu_id) {
        this.zon_id = zon_id;
        this.zon_nombre = zon_nombre;
        this.zon_postal = zon_postal;
        this.par_id = par_id;
        this.est_id = est_id;
        this.ciu_id = ciu_id;
    }

    public Integer getZon_id() {
        return zon_id;
    }

    public void setZon_id(Integer zon_id) {
        this.zon_id = zon_id;
    }

    public String getZon_nombre() {
        return zon_nombre;
    }

    public void setZon_nombre(String zon_nombre) {
        this.zon_nombre = zon_nombre;
    }

    public String getZon_postal() {
        return zon_postal;
    }

    public void setZon_postal(String zon_postal) {
        this.zon_postal = zon_postal;
    }

    public Integer getPar_id() {
        return par_id;
    }

    public void setPar_id(Integer par_id) {
        this.par_id = par_id;
    }

    public Integer getEst_id() {
        return est_id;
    }

    public void setEst_id(Integer est_id) {
        this.est_id = est_id;
    }

    public Integer getCiu_id() {
        return ciu_id;
    }

    public void setCiu_id(Integer ciu_id) {
        this.ciu_id = ciu_id;
    }

    @Override
    public String toString() {
        return "DataAutocompletar{" +
                "zon_id=" + zon_id +
                ", zon_nombre='" + zon_nombre + '\'' +
                ", zon_postal='" + zon_postal + '\'' +
                ", par_id=" + par_id +
                ", est_id=" + est_id +
                ", ciu_id=" + ciu_id +
                '}';
    }
}


