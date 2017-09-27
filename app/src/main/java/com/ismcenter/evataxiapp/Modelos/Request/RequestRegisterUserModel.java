package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * El modelo de registro de usuario que requiere la api con el enlace http://api.evataxi.com.ve/api/users
 */
public class RequestRegisterUserModel {

    private String person_cedula,
            person_nombre,
            person_correo,
            person_direccion,
            person_telf,
            person_fechaNacimiento;


    private int person_tipo;

    private String usu_usuario,
            usu_clave;
    private int rol_id;

    public RequestRegisterUserModel(String person_cedula, String person_nombre, String person_correo,
                                    String person_direccion, String person_telf,
                                    String person_fechaNacimiento, int person_tipo, String usu_usuario,
                                    String usu_clave, int rol_id) {
        this.person_cedula = person_cedula;
        this.person_correo = person_correo;
        this.person_direccion = person_direccion;
        this.person_fechaNacimiento = person_fechaNacimiento;
        this.person_nombre = person_nombre;
        this.person_telf = person_telf;
        this.person_tipo = person_tipo;
        this.rol_id = rol_id;
        this.usu_clave = usu_clave;
        this.usu_usuario = usu_usuario;
    }

    public String getPerson_cedula() {
        return person_cedula;
    }

    public void setPerson_cedula(String person_cedula) {
        this.person_cedula = person_cedula;
    }

    public String getPerson_correo() {
        return person_correo;
    }

    public void setPerson_correo(String person_correo) {
        this.person_correo = person_correo;
    }

    public String getPerson_direccion() {
        return person_direccion;
    }

    public void setPerson_direccion(String person_direccion) {
        this.person_direccion = person_direccion;
    }

    public String getPerson_fechaNacimiento() {
        return person_fechaNacimiento;
    }

    public void setPerson_fechaNacimiento(String person_fechaNacimiento) {
        this.person_fechaNacimiento = person_fechaNacimiento;
    }

    public String getPerson_nombre() {
        return person_nombre;
    }

    public void setPerson_nombre(String person_nombre) {
        this.person_nombre = person_nombre;
    }

    public String getPerson_telf() {
        return person_telf;
    }

    public void setPerson_telf(String person_telf) {
        this.person_telf = person_telf;
    }

    public int getPerson_tipo() {
        return person_tipo;
    }

    public void setPerson_tipo(int person_tipo) {
        this.person_tipo = person_tipo;
    }

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public String getUsu_clave() {
        return usu_clave;
    }

    public void setUsu_clave(String usu_clave) {
        this.usu_clave = usu_clave;
    }

    public String getUsu_usuario() {
        return usu_usuario;
    }

    public void setUsu_usuario(String usu_usuario) {
        this.usu_usuario = usu_usuario;
    }

    @Override
    public String toString() {
        return "RequestRegisterUserModel{" +
                "person_cedula='" + person_cedula + '\'' +
                ", person_nombre='" + person_nombre + '\'' +
                ", person_correo='" + person_correo + '\'' +
                ", person_direccion='" + person_direccion + '\'' +
                ", person_telf='" + person_telf + '\'' +
                ", person_fechaNacimiento='" + person_fechaNacimiento + '\'' +
                ", person_tipo=" + person_tipo +
                ", usu_usuario='" + usu_usuario + '\'' +
                ", usu_clave='" + usu_clave + '\'' +
                ", rol_id=" + rol_id +
                '}';
    }
}
