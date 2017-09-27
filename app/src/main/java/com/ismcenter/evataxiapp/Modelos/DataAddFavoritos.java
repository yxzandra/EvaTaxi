package com.ismcenter.evataxiapp.Modelos;

public class DataAddFavoritos {

    private Integer id_usuario;
    private Integer id_conductor;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataAddFavoritos() {
    }


    /**
     * @param idpersonasecundaria
     * @param idpersonaprincipal
     */
    public DataAddFavoritos(Integer idpersonaprincipal, Integer idpersonasecundaria) {
        this.id_usuario = idpersonaprincipal;
        this.id_conductor = idpersonasecundaria;

    }

    /**
     *
     * @return
     * The idpersonaprincipal
     */
    public Integer getIdpersonaprincipal() {
        return id_usuario;
    }

    /**
     *
     * @param idpersonaprincipal
     * The idpersonaprincipal
     */
    public void setIdpersonaprincipal(Integer idpersonaprincipal) {
        this.id_usuario = idpersonaprincipal;
    }

    /**
     *
     * @return
     * The idpersonasecundaria
     */
    public Integer getIdpersonasecundaria() {
        return id_conductor;
    }

    /**
     *
     * @param idpersonasecundaria
     * The idpersonasecundaria
     */
    public void setIdpersonasecundaria(Integer idpersonasecundaria) {
        this.id_conductor = idpersonasecundaria;
    }

}