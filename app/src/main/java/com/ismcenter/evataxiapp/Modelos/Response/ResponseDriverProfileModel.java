package com.ismcenter.evataxiapp.Modelos.Response;


public class ResponseDriverProfileModel {

    private String person_correo;
    private String person_direccion;
    private String person_nombre;
    private Integer person_id;
    private String car_modelo;
    private String person_telf;
    private String car_placa;
    private String car_color;

    public ResponseDriverProfileModel(String person_correo, String person_direccion,
                                      String person_nombre, Integer person_id, String car_modelo,
                                      String person_telf, String car_placa, String car_color) {
        this.person_correo = person_correo;
        this.person_direccion = person_direccion;
        this.person_nombre = person_nombre;
        this.person_id = person_id;
        this.car_modelo = car_modelo;
        this.car_placa = car_placa;
        this.car_color = car_color;
        this.person_telf = person_telf;
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

    public String getPerson_telf() {
        return person_telf;
    }

    public void setPerson_telf(String person_telf) {
        this.person_telf = person_telf;
    }

    @Override
    public String toString() {
        return "ResponseDriverProfileModel{" +
                "person_correo='" + person_correo + '\'' +
                ", person_direccion='" + person_direccion + '\'' +
                ", person_nombre='" + person_nombre + '\'' +
                ", person_id=" + person_id +
                ", car_modelo='" + car_modelo + '\'' +
                ", car_placa='" + car_placa + '\'' +
                ", car_color='" + car_color + '\'' +
                ", person_telf='" + person_telf + '\'' +
                '}';
    }
}