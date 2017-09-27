package com.ismcenter.evataxiapp.Modelos.Response;


public class Instapago {

    private String insta_id;
    private String insta_codcomprobante;
    private String insta_banco;
    private String insta_fecha;
    private String insta_valido;
    private String insta_personid;
    private String insta_monto;

    public Instapago(String insta_id, String insta_codcomprobante, String insta_banco,
                     String insta_fecha, String insta_valido, String insta_personid, String insta_monto) {
        this.insta_id = insta_id;
        this.insta_codcomprobante = insta_codcomprobante;
        this.insta_banco = insta_banco;
        this.insta_fecha = insta_fecha;
        this.insta_valido = insta_valido;
        this.insta_personid = insta_personid;
        this.insta_monto = insta_monto;
    }

    public String getInsta_id() {
        return insta_id;
    }

    public void setInsta_id(String insta_id) {
        this.insta_id = insta_id;
    }

    public String getInsta_codcomprobante() {
        return insta_codcomprobante;
    }

    public void setInsta_codcomprobante(String insta_codcomprobante) {
        this.insta_codcomprobante = insta_codcomprobante;
    }

    public String getInsta_banco() {
        return insta_banco;
    }

    public void setInsta_banco(String insta_banco) {
        this.insta_banco = insta_banco;
    }

    public String getInsta_fecha() {
        return insta_fecha;
    }

    public void setInsta_fecha(String insta_fecha) {
        this.insta_fecha = insta_fecha;
    }

    public String getInsta_valido() {
        return insta_valido;
    }

    public void setInsta_valido(String insta_valido) {
        this.insta_valido = insta_valido;
    }

    public String getInsta_personid() {
        return insta_personid;
    }

    public void setInsta_personid(String insta_personid) {
        this.insta_personid = insta_personid;
    }

    public String getInsta_monto() {
        return insta_monto;
    }

    public void setInsta_monto(String insta_monto) {
        this.insta_monto = insta_monto;
    }

    @Override
    public String toString() {
        return "Instapago{" +
                "insta_id='" + insta_id + '\'' +
                ", insta_codcomprobante='" + insta_codcomprobante + '\'' +
                ", insta_banco='" + insta_banco + '\'' +
                ", insta_fecha='" + insta_fecha + '\'' +
                ", insta_valido='" + insta_valido + '\'' +
                ", insta_personid='" + insta_personid + '\'' +
                ", insta_monto='" + insta_monto + '\'' +
                '}';
    }
}