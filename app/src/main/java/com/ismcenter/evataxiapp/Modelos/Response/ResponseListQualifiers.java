package com.ismcenter.evataxiapp.Modelos.Response;


public class ResponseListQualifiers {

    private int recep_id;
    private String person_nombre;
    private String person_correo;
    private int person_estatus;
    private String ser_id;

    public ResponseListQualifiers(int recep_id, String person_nombre, String person_correo,
                                  int person_estatus, String ser_id) {
        this.recep_id = recep_id;
        this.person_nombre = person_nombre;
        this.person_correo = person_correo;
        this.person_estatus = person_estatus;
        this.ser_id = ser_id;
    }

    public int getRecep_id() {
        return recep_id;
    }

    public void setRecep_id(int recep_id) {
        this.recep_id = recep_id;
    }

    public String getPerson_nombre() {
        return person_nombre;
    }

    public void setPerson_nombre(String person_nombre) {
        this.person_nombre = person_nombre;
    }

    public String getPerson_correo() {
        return person_correo;
    }

    public void setPerson_correo(String person_correo) {
        this.person_correo = person_correo;
    }

    public int getPerson_estatus() {
        return person_estatus;
    }

    public void setPerson_estatus(int person_estatus) {
        this.person_estatus = person_estatus;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    @Override
    public String toString() {
        return "ResponseListQualifiers{" +
                "recep_id=" + recep_id +
                ", person_nombre='" + person_nombre + '\'' +
                ", person_correo='" + person_correo + '\'' +
                ", person_estatus=" + person_estatus +
                ", ser_id=" + ser_id +
                '}';
    }
}
