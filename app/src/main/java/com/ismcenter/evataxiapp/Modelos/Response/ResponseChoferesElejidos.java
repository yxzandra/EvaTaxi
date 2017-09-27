package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseChoferesElejidos {

    public Integer recep_conductor;
    public Integer recep_serid;
    public Integer ser_solicitante;
    public String person_correo;
    public String person_direccion;
    public String person_nombre;
    public Integer person_estatus;
    public Integer person_id;
    public String car_modelo;
    public String person_telf;
    public String car_placa;
    public String car_color;

    public ResponseChoferesElejidos(Integer recep_conductor, Integer recep_serid, Integer ser_solicitante, String person_correo, String person_direccion, String person_nombre, Integer person_estatus, Integer person_id, String car_modelo, String person_telf, String car_placa, String car_color) {
        this.recep_conductor = recep_conductor;
        this.recep_serid = recep_serid;
        this.ser_solicitante = ser_solicitante;
        this.person_correo = person_correo;
        this.person_direccion = person_direccion;
        this.person_nombre = person_nombre;
        this.person_estatus = person_estatus;
        this.person_id = person_id;
        this.car_modelo = car_modelo;
        this.person_telf = person_telf;
        this.car_placa = car_placa;
        this.car_color = car_color;
    }

    public Integer getRecep_conductor() {
        return recep_conductor;
    }

    public void setRecep_conductor(Integer recep_conductor) {
        this.recep_conductor = recep_conductor;
    }

    public Integer getRecep_serid() {
        return recep_serid;
    }

    public void setRecep_serid(Integer recep_serid) {
        this.recep_serid = recep_serid;
    }

    public Integer getSer_solicitante() {
        return ser_solicitante;
    }

    public void setSer_solicitante(Integer ser_solicitante) {
        this.ser_solicitante = ser_solicitante;
    }

    public String getPerson_correo() {
        return person_correo;
    }

    public void setPerson_correo(String person_correo) {
        this.person_correo = person_correo;
    }

    public String getPerson_direccion() {
        return person_direccion;
    }

    public void setPerson_direccion(String person_direccion) {
        this.person_direccion = person_direccion;
    }

    public String getPerson_nombre() {
        return person_nombre;
    }

    public void setPerson_nombre(String person_nombre) {
        this.person_nombre = person_nombre;
    }

    public Integer getPerson_estatus() {
        return person_estatus;
    }

    public void setPerson_estatus(Integer person_estatus) {
        this.person_estatus = person_estatus;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getCar_modelo() {
        return car_modelo;
    }

    public void setCar_modelo(String car_modelo) {
        this.car_modelo = car_modelo;
    }

    public String getPerson_telf() {
        return person_telf;
    }

    public void setPerson_telf(String person_telf) {
        this.person_telf = person_telf;
    }

    public String getCar_placa() {
        return car_placa;
    }

    public void setCar_placa(String car_placa) {
        this.car_placa = car_placa;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }
}