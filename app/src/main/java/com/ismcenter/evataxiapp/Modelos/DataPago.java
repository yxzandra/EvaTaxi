package com.ismcenter.evataxiapp.Modelos;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataPago {

    private Integer trans_comprobante;
    private String trans_fecha;
    private Integer trans_banco;
    private Integer trans_personid;
    private Integer trans_monto;
    private Integer trans_id;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataPago() {
    }

    /**
     *
     * @param transComprobante
     * @param transId
     * @param transMonto
     * @param transBanco
     * @param transPersonid
     * @param transFecha
     */
    public DataPago(Integer transComprobante, String transFecha, Integer transBanco, Integer transPersonid, Integer transMonto, Integer transId) {
        this.trans_comprobante = transComprobante;
        this.trans_fecha = transFecha;
        this.trans_banco = transBanco;
        this.trans_personid = transPersonid;
        this.trans_monto = transMonto;
        this.trans_id = transId;
    }

    /**
     *
     * @return
     * The transComprobante
     */
    public Integer getTransComprobante() {
        return trans_comprobante;
    }

    /**
     *
     * @param transComprobante
     * The trans_comprobante
     */
    public void setTransComprobante(Integer transComprobante) {
        this.trans_comprobante = transComprobante;
    }

    /**
     *
     * @return
     * The transFecha
     */
    public String getTransFecha() {
        return trans_fecha;
    }

    /**
     *
     * @param transFecha
     * The trans_fecha
     */
    public void setTransFecha(String transFecha) {
        this.trans_fecha = transFecha;
    }

    /**
     *
     * @return
     * The transBanco
     */
    public Integer getTransBanco() {
        return trans_banco;
    }

    /**
     *
     * @param transBanco
     * The trans_banco
     */
    public void setTransBanco(Integer transBanco) {
        this.trans_banco = transBanco;
    }

    /**
     *
     * @return
     * The transPersonid
     */
    public Integer getTransPersonid() {
        return trans_personid;
    }

    /**
     *
     * @param transPersonid
     * The trans_personid
     */
    public void setTransPersonid(Integer transPersonid) {
        this.trans_personid = transPersonid;
    }

    /**
     *
     * @return
     * The transMonto
     */
    public Integer getTransMonto() {
        return trans_monto;
    }

    /**
     *
     * @param transMonto
     * The trans_monto
     */
    public void setTransMonto(Integer transMonto) {
        this.trans_monto = transMonto;
    }

    /**
     *
     * @return
     * The transId
     */
    public Integer getTransId() {
        return trans_id;
    }

    /**
     *
     * @param transId
     * The trans_id
     */
    public void setTransId(Integer transId) {
        this.trans_id = transId;
    }

}