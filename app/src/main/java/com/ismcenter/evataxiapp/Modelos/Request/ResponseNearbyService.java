package com.ismcenter.evataxiapp.Modelos.Request;


public class ResponseNearbyService {

    private Integer ser_id;
    private String ser_fecha;
    private Integer ser_solicitante;
    private String ser_origen;
    private String ser_destino;
    private String ser_estado;
    private String ser_zonaid;
    private String ser_coordenadaOrigen;
    private String ser_CoordenadaDestino;
    private String person_nombre;
    private String person_telf;


    public ResponseNearbyService(Integer ser_id, String ser_fecha, Integer ser_solicitante,
                                 String ser_origen, String ser_destino, String ser_estado,
                                 String ser_zonaid, String ser_coordenadaOrigen,
                                 String ser_CoordenadaDestino, String person_nombre, String person_telf) {
        this.ser_id = ser_id;
        this.ser_fecha = ser_fecha;
        this.ser_solicitante = ser_solicitante;
        this.ser_origen = ser_origen;
        this.ser_destino = ser_destino;
        this.ser_estado = ser_estado;
        this.ser_zonaid = ser_zonaid;
        this.ser_coordenadaOrigen = ser_coordenadaOrigen;
        this.ser_CoordenadaDestino = ser_CoordenadaDestino;
        this.person_nombre = person_nombre;
        this.person_telf = person_telf;
    }

    public Integer getSer_id() {
        return ser_id;
    }

    public void setSer_id(Integer ser_id) {
        this.ser_id = ser_id;
    }

    public String getSer_fecha() {
        return ser_fecha;
    }

    public void setSer_fecha(String ser_fecha) {
        this.ser_fecha = ser_fecha;
    }

    public Integer getSer_solicitante() {
        return ser_solicitante;
    }

    public void setSer_solicitante(Integer ser_solicitante) {
        this.ser_solicitante = ser_solicitante;
    }

    public String getSer_origen() {
        return ser_origen;
    }

    public void setSer_origen(String ser_origen) {
        this.ser_origen = ser_origen;
    }

    public String getSer_destino() {
        return ser_destino;
    }

    public void setSer_destino(String ser_destino) {
        this.ser_destino = ser_destino;
    }

    public String getSer_estado() {
        return ser_estado;
    }

    public void setSer_estado(String ser_estado) {
        this.ser_estado = ser_estado;
    }

    public String getSer_zonaid() {
        return ser_zonaid;
    }

    public void setSer_zonaid(String ser_zonaid) {
        this.ser_zonaid = ser_zonaid;
    }

    public String getSer_coordenadaOrigen() {
        return ser_coordenadaOrigen;
    }

    public void setSer_coordenadaOrigen(String ser_coordenadaOrigen) {
        this.ser_coordenadaOrigen = ser_coordenadaOrigen;
    }

    public String getSer_CoordenadaDestino() {
        return ser_CoordenadaDestino;
    }

    public void setSer_CoordenadaDestino(String ser_CoordenadaDestino) {
        this.ser_CoordenadaDestino = ser_CoordenadaDestino;
    }

    public String getPerson_nombre() {
        return person_nombre;
    }

    public void setPerson_nombre(String person_nombre) {
        this.person_nombre = person_nombre;
    }

    public String getPerson_telf() {
        return person_telf;
    }

    public void setPerson_telf(String person_telf) {
        this.person_telf = person_telf;
    }

    @Override
    public String toString() {
        return "ResponseNearbyService{" +
                "ser_id=" + ser_id +
                ", ser_fecha='" + ser_fecha + '\'' +
                ", ser_solicitante=" + ser_solicitante +
                ", ser_origen='" + ser_origen + '\'' +
                ", ser_destino='" + ser_destino + '\'' +
                ", ser_estado='" + ser_estado + '\'' +
                ", ser_zonaid='" + ser_zonaid + '\'' +
                ", ser_coordenadaOrigen='" + ser_coordenadaOrigen + '\'' +
                ", ser_CoordenadaDestino='" + ser_CoordenadaDestino + '\'' +
                ", person_nombre='" + person_nombre + '\'' +
                ", person_telf='" + person_telf + '\'' +
                '}';
    }
}