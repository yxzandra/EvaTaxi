package com.ismcenter.evataxiapp.Modelos.Response;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResponseLoginUserModel {

    private boolean success;
    private Integer id_usuario;
    private Integer id_persona;
    private Integer tipo_persona;
    private Boolean primer_login;
    private String token;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.id_usuario = idUsuario;
    }

    public Integer getIdPersona() {
        return id_persona;
    }

    public void setIdPersona(Integer idPersona) {
        this.id_persona = idPersona;
    }

    public Integer getTipoPersona() {
        return tipo_persona;
    }

    public void setTipoPersona(Integer tipoPersona) {
        this.tipo_persona = tipoPersona;
    }

    public Boolean getPrimerLogin() {
        return primer_login;
    }

    public void setPrimerLogin(Boolean primerLogin) {
        this.primer_login = primerLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ResponseLoginUserModel{" +
                "success=" + success +
                ", id_usuario=" + id_usuario +
                ", id_persona=" + id_persona +
                ", tipo_persona=" + tipo_persona +
                ", primer_login=" + primer_login +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
