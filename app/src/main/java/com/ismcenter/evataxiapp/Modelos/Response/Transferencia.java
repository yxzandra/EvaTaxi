package com.ismcenter.evataxiapp.Modelos.Response;


public class Transferencia {

    private String trans_id;
    private String trans_comprobante;
    private String trans_fecha;
    private String trans_banco;
    private String trans_validado;
    private String trans_personid;
    private String trans_monto;

    public Transferencia(String trans_id, String trans_comprobante, String trans_fecha,
                         String trans_banco, String trans_validado, String trans_personid,
                         String trans_monto) {
        this.trans_id = trans_id;
        this.trans_comprobante = trans_comprobante;
        this.trans_fecha = trans_fecha;
        this.trans_banco = trans_banco;
        this.trans_validado = trans_validado;
        this.trans_personid = trans_personid;
        this.trans_monto = trans_monto;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getTrans_comprobante() {
        return trans_comprobante;
    }

    public void setTrans_comprobante(String trans_comprobante) {
        this.trans_comprobante = trans_comprobante;
    }

    public String getTrans_fecha() {
        return trans_fecha;
    }

    public void setTrans_fecha(String trans_fecha) {
        this.trans_fecha = trans_fecha;
    }

    public String getTrans_banco() {
        return trans_banco;
    }

    public void setTrans_banco(String trans_banco) {
        this.trans_banco = trans_banco;
    }

    public String getTrans_validado() {
        return trans_validado;
    }

    public void setTrans_validado(String trans_validado) {
        this.trans_validado = trans_validado;
    }

    public String getTrans_personid() {
        return trans_personid;
    }

    public void setTrans_personid(String trans_personid) {
        this.trans_personid = trans_personid;
    }

    public String getTrans_monto() {
        return trans_monto;
    }

    public void setTrans_monto(String trans_monto) {
        this.trans_monto = trans_monto;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "trans_id='" + trans_id + '\'' +
                ", trans_comprobante='" + trans_comprobante + '\'' +
                ", trans_fecha='" + trans_fecha + '\'' +
                ", trans_banco='" + trans_banco + '\'' +
                ", trans_validado='" + trans_validado + '\'' +
                ", trans_personid='" + trans_personid + '\'' +
                ", trans_monto='" + trans_monto + '\'' +
                '}';
    }
}