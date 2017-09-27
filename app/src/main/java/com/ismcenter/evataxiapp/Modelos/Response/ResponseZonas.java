package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseZonas {
    public Integer zon_id;
    public String zon_nombre;
    public String zon_postal;
    public Integer par_id;
    public Integer est_id;
    public Integer ciu_id;

    public ResponseZonas(Integer zon_id, String zon_nombre, String zon_postal, Integer par_id, Integer est_id, Integer ciu_id) {
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
        return "ResponseZonas{" +
                "zon_id=" + zon_id +
                ", zon_nombre='" + zon_nombre + '\'' +
                ", zon_postal='" + zon_postal + '\'' +
                ", par_id=" + par_id +
                ", est_id=" + est_id +
                ", ciu_id=" + ciu_id +
                '}';
    }
}