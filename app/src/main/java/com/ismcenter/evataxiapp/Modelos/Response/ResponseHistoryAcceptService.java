package com.ismcenter.evataxiapp.Modelos.Response;


public class ResponseHistoryAcceptService {

    private Integer ser_id;
    private String ser_fecha;
    private String ser_solicitante;
    private String ser_origen;
    private String ser_destino;
    private String ser_estado;
    private String ser_zonaid;
    private String ser_coordenadaOrigen;
    private String ser_CoordenadaDestino;
    private String person_nombre;

    public ResponseHistoryAcceptService(Integer ser_id, String ser_fecha, String ser_solicitante,
                                        String ser_origen, String ser_destino, String ser_estado,
                                        String ser_zonaid, String ser_coordenadaOrigen,
                                        String ser_CoordenadaDestino, String person_nombre) {
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

    public String getSer_solicitante() {
        return ser_solicitante;
    }

    public void setSer_solicitante(String ser_solicitante) {
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

    @Override
    public String toString() {
        return "ResponseHistoryAcceptService{" +
                "ser_id=" + ser_id +
                ", ser_fecha='" + ser_fecha + '\'' +
                ", ser_solicitante='" + ser_solicitante + '\'' +
                ", ser_origen='" + ser_origen + '\'' +
                ", ser_destino='" + ser_destino + '\'' +
                ", ser_estado='" + ser_estado + '\'' +
                ", ser_zonaid='" + ser_zonaid + '\'' +
                ", ser_coordenadaOrigen='" + ser_coordenadaOrigen + '\'' +
                ", ser_CoordenadaDestino='" + ser_CoordenadaDestino + '\'' +
                ", person_nombre='" + person_nombre + '\'' +
                '}';
    }
}