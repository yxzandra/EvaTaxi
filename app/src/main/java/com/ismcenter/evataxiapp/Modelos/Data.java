package com.ismcenter.evataxiapp.Modelos;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Data {

    private Integer person_id;
    private String person_cedula;
    private String person_nombre;
    private String person_correo;
    private String person_direccion;
    private String person_telf;
    private String person_fechaNacimiento;
    private Integer person_estatus;
    private String person_tipo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The personId
     */
    public Integer getPersonId() {
        return person_id;
    }

    /**
     *
     * @param personId
     * The person_id
     */
    public void setPersonId(Integer personId) {
        this.person_id = personId;
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
     * The personFechaNacimiento
     */
    public String getPersonFechaNacimiento() {
        return person_fechaNacimiento;
    }

    /**
     *
     * @param personFechaNacimiento
     * The person_fechaNacimiento
     */
    public void setPersonFechaNacimiento(String personFechaNacimiento) {
        this.person_fechaNacimiento = personFechaNacimiento;
    }

    /**
     *
     * @return
     * The personEstatus
     */
    public Integer getPersonEstatus() {
        return person_estatus;
    }

    /**
     *
     * @param personEstatus
     * The person_estatus
     */
    public void setPersonEstatus(Integer personEstatus) {
        this.person_estatus = personEstatus;
    }

    /**
     *
     * @return
     * The personTipo
     */
    public String getPersonTipo() {
        return person_tipo;
    }

    /**
     *
     * @param personTipo
     * The person_tipo
     */
    public void setPersonTipo(String personTipo) {
        this.person_tipo = personTipo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Data{" +
                "person_id=" + person_id +
                ", person_cedula='" + person_cedula + '\'' +
                ", person_nombre='" + person_nombre + '\'' +
                ", person_correo='" + person_correo + '\'' +
                ", person_direccion='" + person_direccion + '\'' +
                ", person_telf='" + person_telf + '\'' +
                ", person_fechaNacimiento='" + person_fechaNacimiento + '\'' +
                ", person_estatus=" + person_estatus +
                ", person_tipo='" + person_tipo + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}