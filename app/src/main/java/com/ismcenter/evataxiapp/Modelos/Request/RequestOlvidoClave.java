package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by yxzan on 04/12/2016.
 */
public class RequestOlvidoClave {
    String person_correo;

    public RequestOlvidoClave(String person_correo) {
        this.person_correo = person_correo;
    }

    public String getPerson_correo() {
        return person_correo;
    }

    public void setPerson_correo(String person_correo) {
        this.person_correo = person_correo;
    }

    @Override
    public String toString() {
        return "RequestOlvidoClave{" +
                "person_correo='" + person_correo + '\'' +
                '}';
    }
}
