package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseHistoryService {


    public Integer ser_id;
    public Integer ser_solicitante;
    public String ser_origen;
    public String serDestino;
    public Integer recep_conductor;
    public Integer usu_idPrincipal;
    public String person_nombre;
    public String person_correo;
    public Integer recep_id;
    public Integer recep_serid;

    public ResponseHistoryService(Integer ser_id, Integer ser_solicitante, String ser_origen, String serDestino, Integer recep_conductor, Integer usu_idPrincipal, String person_nombre, String person_correo, Integer recep_id, Integer recep_serid) {
        this.ser_id = ser_id;
        this.ser_solicitante = ser_solicitante;
        this.ser_origen = ser_origen;
        this.serDestino = serDestino;
        this.recep_conductor = recep_conductor;
        this.usu_idPrincipal = usu_idPrincipal;
        this.person_nombre = person_nombre;
        this.person_correo = person_correo;
        this.recep_id = recep_id;
        this.recep_serid = recep_serid;
    }

    public Integer getSer_id() {
        return ser_id;
    }

    public void setSer_id(Integer ser_id) {
        this.ser_id = ser_id;
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

    public String getSerDestino() {
        return serDestino;
    }

    public void setSerDestino(String serDestino) {
        this.serDestino = serDestino;
    }

    public Integer getRecep_conductor() {
        return recep_conductor;
    }

    public void setRecep_conductor(Integer recep_conductor) {
        this.recep_conductor = recep_conductor;
    }

    public Integer getUsu_idPrincipal() {
        return usu_idPrincipal;
    }

    public void setUsu_idPrincipal(Integer usu_idPrincipal) {
        this.usu_idPrincipal = usu_idPrincipal;
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

    public Integer getRecep_id() {
        return recep_id;
    }

    public void setRecep_id(Integer recep_id) {
        this.recep_id = recep_id;
    }

    public Integer getRecep_serid() {
        return recep_serid;
    }

    public void setRecep_serid(Integer recep_serid) {
        this.recep_serid = recep_serid;
    }
}