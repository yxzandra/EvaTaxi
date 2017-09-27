package com.ismcenter.evataxiapp.Modelos;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataFavoritos {

    private Integer idfavoritos;
    private Integer idpersonaprincipal;
    private Integer idpersonasecundaria;
    private Integer estatus;
    private String person_cedula;
    private String person_nombre;
    private String person_correo;
    private String person_direccion;
    private String person_telf;
    private Integer person_tipo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     *
     * @return
     * The personCedula
     */
    public String getPersonCedula() {
        return person_cedula;
    }

    /**
     *
     * @param personCedula
     * The person_cedula
     */
    public void setPersonCedula(String personCedula) {
        this.person_cedula = personCedula;
    }

    /**
     *
     * @return
     * The personNombre
     */
    public String getPersonNombre() {
        return person_nombre;
    }

    /**
     *
     * @param personNombre
     * The person_nombre
     */
    public void setPersonNombre(String personNombre) {
        this.person_nombre = personNombre;
    }

    /**
     *
     * @return
     * The personCorreo
     */
    public String getPersonCorreo() {
        return person_correo;
    }

    /**
     *
     * @param personCorreo
     * The person_correo
     */
    public void setPersonCorreo(String personCorreo) {
        this.person_correo = personCorreo;
    }

    /**
     *
     * @return
     * The personDireccion
     */
    public String getPersonDireccion() {
        return person_direccion;
    }

    /**
     *
     * @param personDireccion
     * The person_direccion
     */
    public void setPersonDireccion(String personDireccion) {
        this.person_direccion = personDireccion;
    }

    /**
     *
     * @return
     * The personTelf
     */
    public String getPersonTelf() {
        return person_telf;
    }

    /**
     *
     * @param personTelf
     * The person_telf
     */
    public void setPersonTelf(String personTelf) {
        this.person_telf = personTelf;
    }

    /**
     *
     * @return
     * The personTipo
     */
    public Integer getPersonTipo() {
        return person_tipo;
    }

    /**
     *
     * @param personTipo
     * The person_tipo
     */
    public void setPersonTipo(Integer personTipo) {
        this.person_tipo = personTipo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
