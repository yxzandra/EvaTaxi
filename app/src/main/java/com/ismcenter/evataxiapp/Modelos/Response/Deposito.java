package com.ismcenter.evataxiapp.Modelos.Response;


public class Deposito {

    private String dep_id;
    private String dep_comprobante;
    private String dep_fecha;
    private String dep_banco;
    private String dep_validado;
    private String dep_personid;
    private String dep_monto;

    public Deposito(String dep_id, String dep_comprobante, String dep_fecha, String dep_banco,
                    String dep_validado, String dep_personid, String dep_monto) {
        this.dep_id = dep_id;
        this.dep_comprobante = dep_comprobante;
        this.dep_fecha = dep_fecha;
        this.dep_banco = dep_banco;
        this.dep_validado = dep_validado;
        this.dep_personid = dep_personid;
        this.dep_monto = dep_monto;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_comprobante() {
        return dep_comprobante;
    }

    public void setDep_comprobante(String dep_comprobante) {
        this.dep_comprobante = dep_comprobante;
    }

    public String getDep_fecha() {
        return dep_fecha;
    }

    public void setDep_fecha(String dep_fecha) {
        this.dep_fecha = dep_fecha;
    }

    public String getDep_banco() {
        return dep_banco;
    }

    public void setDep_banco(String dep_banco) {
        this.dep_banco = dep_banco;
    }

    public String getDep_validado() {
        return dep_validado;
    }

    public void setDep_validado(String dep_validado) {
        this.dep_validado = dep_validado;
    }

    public String getDep_personid() {
        return dep_personid;
    }

    public void setDep_personid(String dep_personid) {
        this.dep_personid = dep_personid;
    }

    public String getDep_monto() {
        return dep_monto;
    }

    public void setDep_monto(String dep_monto) {
        this.dep_monto = dep_monto;
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "dep_id='" + dep_id + '\'' +
                ", dep_comprobante='" + dep_comprobante + '\'' +
                ", dep_fecha='" + dep_fecha + '\'' +
                ", dep_banco='" + dep_banco + '\'' +
                ", dep_validado='" + dep_validado + '\'' +
                ", dep_personid='" + dep_personid + '\'' +
                ", dep_monto='" + dep_monto + '\'' +
                '}';
    }
}