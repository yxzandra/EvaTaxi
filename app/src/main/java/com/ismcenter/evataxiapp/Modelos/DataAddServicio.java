package com.ismcenter.evataxiapp.Modelos;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataAddServicio {

    private String ser_fecha;
    private Integer ser_solicitante;
    private String ser_origen;
    private String ser_destino;
    private Integer ser_estado;
    private Integer ser_id;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataAddServicio() {
    }

    /**
     *
     * @param serId
     * @param serEstado
     * @param serOrigen
     * @param serFecha
     * @param serSolicitante
     * @param serDestino
     */
    public DataAddServicio(String serFecha, Integer serSolicitante, String serOrigen, String serDestino, Integer serEstado, Integer serId) {
        this.ser_fecha = serFecha;
        this.ser_solicitante = serSolicitante;
        this.ser_origen = serOrigen;
        this.ser_destino = serDestino;
        this.ser_estado = serEstado;
        this.ser_id = serId;
    }

    /**
     *
     * @return
     * The serFecha
     */
    public String getSerFecha() {
        return ser_fecha;
    }

    /**
     *
     * @param serFecha
     * The ser_fecha
     */
    public void setSerFecha(String serFecha) {
        this.ser_fecha = serFecha;
    }

    /**
     *
     * @return
     * The serSolicitante
     */
    public Integer getSerSolicitante() {
        return ser_solicitante;
    }

    /**
     *
     * @param serSolicitante
     * The ser_solicitante
     */
    public void setSerSolicitante(Integer serSolicitante) {
        this.ser_solicitante = serSolicitante;
    }

    /**
     *
     * @return
     * The serOrigen
     */
    public String getSerOrigen() {
        return ser_origen;
    }

    /**
     *
     * @param serOrigen
     * The ser_origen
     */
    public void setSerOrigen(String serOrigen) {
        this.ser_origen = serOrigen;
    }

    /**
     *
     * @return
     * The serDestino
     */
    public String getSerDestino() {
        return ser_destino;
    }

    /**
     *
     * @param serDestino
     * The ser_destino
     */
    public void setSerDestino(String serDestino) {
        this.ser_destino = serDestino;
    }

    /**
     *
     * @return
     * The serEstado
     */
    public Integer getSerEstado() {
        return ser_estado;
    }

    /**
     *
     * @param serEstado
     * The ser_estado
     */
    public void setSerEstado(Integer serEstado) {
        this.ser_estado = serEstado;
    }

    /**
     *
     * @return
     * The serId
     */
    public Integer getSerId() {
        return ser_id;
    }

    /**
     *
     * @param serId
     * The ser_id
     */
    public void setSerId(Integer serId) {
        this.ser_id = serId;
    }

}