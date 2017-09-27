package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by Desarrollo on 20/10/2016.
 */
public class LoginUserModel {
    private String usu_usuario, usu_clave, tipo_usuario;

    public LoginUserModel(String usu_usuario, String usu_clave, String tipo_usuario) {
        this.usu_usuario = usu_usuario;
        this.usu_clave = usu_clave;
        this.tipo_usuario = tipo_usuario;
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

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
