package com.ismcenter.evataxiapp.Modelos.Request;

public class RequestDeposito {

    public String dep_comprobante;
    public String dep_fecha;
    public String dep_banco;
    public String dep_monto;
    public String dep_personid;

    public RequestDeposito(String dep_comprobante, String dep_fecha, String dep_banco, String dep_monto, String dep_personid) {
        this.dep_comprobante = dep_comprobante;
        this.dep_fecha = dep_fecha;
        this.dep_banco = dep_banco;
        this.dep_monto = dep_monto;
        this.dep_personid = dep_personid;
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

    public String getDep_monto() {
        return dep_monto;
    }

    public void setDep_monto(String dep_monto) {
        this.dep_monto = dep_monto;
    }

    public String getDep_personid() {
        return dep_personid;
    }

    public void setDep_personid(String dep_personid) {
        this.dep_personid = dep_personid;
    }

    @Override
    public String toString() {
        return "RequestDeposito{" +
                "dep_comprobante='" + dep_comprobante + '\'' +
                ", dep_fecha='" + dep_fecha + '\'' +
                ", dep_banco='" + dep_banco + '\'' +
                ", dep_monto='" + dep_monto + '\'' +
                ", dep_personid='" + dep_personid + '\'' +
                '}';
    }
}
