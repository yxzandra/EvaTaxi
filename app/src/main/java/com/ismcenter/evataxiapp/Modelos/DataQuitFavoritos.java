package com.ismcenter.evataxiapp.Modelos;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataQuitFavoritos {

    private Integer idfavoritos;
    private Integer idpersonaprincipal;
    private Integer idpersonasecundaria;
    private Integer estatus;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataQuitFavoritos() {
    }

    /**
     *
     * @param idfavoritos
     * @param estatus
     * @param idpersonasecundaria
     * @param idpersonaprincipal
     */
    public DataQuitFavoritos(Integer idfavoritos, Integer idpersonaprincipal, Integer idpersonasecundaria, Integer estatus) {
        this.idfavoritos = idfavoritos;
        this.idpersonaprincipal = idpersonaprincipal;
        this.idpersonasecundaria = idpersonasecundaria;
        this.estatus = estatus;
    }

    /**
     *
     * @return
     * The idfavoritos
     */
    public Integer getIdfavoritos() {
        return idfavoritos;
    }

    /**
     *
     * @param idfavoritos
     * The idfavoritos
     */
    public void setIdfavoritos(Integer idfavoritos) {
        this.idfavoritos = idfavoritos;
    }

    /**
     *
     * @return
     * The idpersonaprincipal
     */
    public Integer getIdpersonaprincipal() {
        return idpersonaprincipal;
    }

    /**
     *
     * @param idpersonaprincipal
     * The idpersonaprincipal
     */
    public void setIdpersonaprincipal(Integer idpersonaprincipal) {
        this.idpersonaprincipal = idpersonaprincipal;
    }

    /**
     *
     * @return
     * The idpersonasecundaria
     */
    public Integer getIdpersonasecundaria() {
        return idpersonasecundaria;
    }

    /**
     *
     * @param idpersonasecundaria
     * The idpersonasecundaria
     */
    public void setIdpersonasecundaria(Integer idpersonasecundaria) {
        this.idpersonasecundaria = idpersonasecundaria;
    }

    /**
     *
     * @return
     * The estatus
     */
    public Integer getEstatus() {
        return estatus;
    }

    /**
     *
     * @param estatus
     * The estatus
     */
    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
