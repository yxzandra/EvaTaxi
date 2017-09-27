package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by yxzan on 03/12/2016.
 */
public class ChangePasswordModel {
    String usu_usuario, usu_clave, usu_nueva_clave;

    public ChangePasswordModel(String usu_usuario, String usu_clave, String usu_nueva_clave) {
        this.usu_usuario = usu_usuario;
        this.usu_clave = usu_clave;
        this.usu_nueva_clave = usu_nueva_clave;
    }

    public String getUsu_usuario() {
        return usu_usuario;
    }

    public void setUsu_usuario(String usu_usuario) {
        this.usu_usuario = usu_usuario;
    }

    public String getUsu_clave() {
        return usu_clave;
    }

    public void setUsu_clave(String usu_clave) {
        this.usu_clave = usu_clave;
    }

    public String getUsu_nueva_clave() {
        return usu_nueva_clave;
    }

    public void setUsu_nueva_clave(String usu_nueva_clave) {
        this.usu_nueva_clave = usu_nueva_clave;
    }

    @Override
    public String toString() {
        return "ChangePasswordModel{" +
                "usu_usuario='" + usu_usuario + '\'' +
                ", usu_clave='" + usu_clave + '\'' +
                ", usu_nueva_clave='" + usu_nueva_clave + '\'' +
                '}';
    }
}
