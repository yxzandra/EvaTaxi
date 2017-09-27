package com.ismcenter.evataxiapp.Modelos.Request;

public class RequestTransferencia {

    public String trans_comprobante;
    public String trans_fecha;
    public String trans_banco;
    public String trans_monto;
    public String trans_personid;

    public RequestTransferencia(String trans_comprobante, String trans_fecha, String trans_banco, String trans_monto, String trans_personid) {
        this.trans_comprobante = trans_comprobante;
        this.trans_fecha = trans_fecha;
        this.trans_banco = trans_banco;
        this.trans_monto = trans_monto;
        this.trans_personid = trans_personid;
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

    public String getTrans_monto() {
        return trans_monto;
    }

    public void setTrans_monto(String trans_monto) {
        this.trans_monto = trans_monto;
    }

    public String getTrans_personid() {
        return trans_personid;
    }

    public void setTrans_personid(String trans_personid) {
        this.trans_personid = trans_personid;
    }
}
