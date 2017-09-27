package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponsePasajeroProfileModel {

    private Integer person_id;
    private String person_cedula;
    private String person_nombre;
    private String person_correo;
    private String person_direccion;
    private String person_telf;
    private String person_fechaNacimiento;
    private Integer person_estatus;
    private Integer person_tipo;
    private String person_fechaingreso;
    private Integer person_est;
    private String zon_postal;

    public ResponsePasajeroProfileModel(Integer personId, String personCedula, String personNombre, String personCorreo, String personDireccion, String personTelf, String personFechaNacimiento, Integer personEstatus, Integer personTipo, String personFechaingreso, Integer personEst, String zonPostal) {
        super();
        this.person_id = personId;
        this.person_cedula = personCedula;
        this.person_nombre = personNombre;
        this.person_correo = personCorreo;
        this.person_direccion = personDireccion;
        this.person_telf = personTelf;
        this.person_fechaNacimiento = personFechaNacimiento;
        this.person_estatus = personEstatus;
        this.person_tipo = personTipo;
        this.person_fechaingreso = personFechaingreso;
        this.person_est = personEst;
        this.zon_postal = zonPostal;
    }

    public Integer getPersonId() {
        return person_id;
    }

    public void setPersonId(Integer personId) {
        this.person_id = personId;
    }

    public String getPersonCedula() {
        return person_cedula;
    }

    public void setPersonCedula(String personCedula) {
        this.person_cedula = personCedula;
    }

    public String getPersonNombre() {
        return person_nombre;
    }

    public void setPersonNombre(String personNombre) {
        this.person_nombre = personNombre;
    }

    public String getPersonCorreo() {
        return person_correo;
    }

    public void setPersonCorreo(String personCorreo) {
        this.person_correo = personCorreo;
    }

    public String getPersonDireccion() {
        return person_direccion;
    }

    public void setPersonDireccion(String personDireccion) {
        this.person_direccion = personDireccion;
    }

    public String getPersonTelf() {
        return person_telf;
    }

    public void setPersonTelf(String personTelf) {
        this.person_telf = personTelf;
    }

    public String getPersonFechaNacimiento() {
        return person_fechaNacimiento;
    }

    public void setPersonFechaNacimiento(String personFechaNacimiento) {
        this.person_fechaNacimiento = personFechaNacimiento;
    }

    public Integer getPersonEstatus() {
        return person_estatus;
    }

    public void setPersonEstatus(Integer personEstatus) {
        this.person_estatus = personEstatus;
    }

    public Integer getPersonTipo() {
        return person_tipo;
    }

    public void setPersonTipo(Integer personTipo) {
        this.person_tipo = personTipo;
    }

    public String getPersonFechaingreso() {
        return person_fechaingreso;
    }

    public void setPersonFechaingreso(String personFechaingreso) {
        this.person_fechaingreso = personFechaingreso;
    }

    public Integer getPersonEst() {
        return person_est;
    }

    public void setPersonEst(Integer personEst) {
        this.person_est = personEst;
    }

    public String getZonPostal() {
        return zon_postal;
    }

    public void setZonPostal(String zonPostal) {
        this.zon_postal = zonPostal;
    }

}