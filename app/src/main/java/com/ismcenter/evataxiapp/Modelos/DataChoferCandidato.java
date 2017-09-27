package com.ismcenter.evataxiapp.Modelos;

public class DataChoferCandidato {

    private String person_correo;
    private String person_direccion;
    private String person_nombre;
    private Integer person_id;
    private Object car_modelo;
    private String person_telf;
    private Object car_placa;
    private Object car_color;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataChoferCandidato() {
    }

    /**
     *
     * @param personNombre
     * @param carPlaca
     * @param personTelf
     * @param carModelo
     * @param personId
     * @param personDireccion
     * @param carColor
     * @param personCorreo
     */
    public DataChoferCandidato(String personCorreo, String personDireccion, String personNombre, Integer personId, Object carModelo, String personTelf, Object carPlaca, Object carColor) {
        super();
        this.person_correo = personCorreo;
        this.person_direccion = personDireccion;
        this.person_nombre = personNombre;
        this.person_id = personId;
        this.car_modelo = carModelo;
        this.person_telf = personTelf;
        this.car_placa = carPlaca;
        this.car_color = carColor;
    }

    /**
     *
     * @return
     * The personCorreo
     */
    public String getPersonCorreo() {
        return person_correo;
    }

    /**
     *
     * @param personCorreo
     * The person_correo
     */
    public void setPersonCorreo(String personCorreo) {
        this.person_correo = personCorreo;
    }

    /**
     *
     * @return
     * The personDireccion
     */
    public String getPersonDireccion() {
        return person_direccion;
    }

    /**
     *
     * @param personDireccion
     * The person_direccion
     */
    public void setPersonDireccion(String personDireccion) {
        this.person_direccion = personDireccion;
    }

    /**
     *
     * @return
     * The personNombre
     */
    public String getPersonNombre() {
        return person_nombre;
    }

    /**
     *
     * @param personNombre
     * The person_nombre
     */
    public void setPersonNombre(String personNombre) {
        this.person_nombre = personNombre;
    }

    /**
     *
     * @return
     * The personId
     */
    public Integer getPersonId() {
        return person_id;
    }

    /**
     *
     * @param personId
     * The person_id
     */
    public void setPersonId(Integer personId) {
        this.person_id = personId;
    }

    /**
     *
     * @return
     * The carModelo
     */
    public Object getCarModelo() {
        return car_modelo;
    }

    /**
     *
     * @param carModelo
     * The car_modelo
     */
    public void setCarModelo(Object carModelo) {
        this.car_modelo = carModelo;
    }

    /**
     *
     * @return
     * The personTelf
     */
    public String getPersonTelf() {
        return person_telf;
    }

    /**
     *
     * @param personTelf
     * The person_telf
     */
    public void setPersonTelf(String personTelf) {
        this.person_telf = personTelf;
    }

    /**
     *
     * @return
     * The carPlaca
     */
    public Object getCarPlaca() {
        return car_placa;
    }

    /**
     *
     * @param carPlaca
     * The car_placa
     */
    public void setCarPlaca(Object carPlaca) {
        this.car_placa = carPlaca;
    }

    /**
     *
     * @return
     * The carColor
     */
    public Object getCarColor() {
        return car_color;
    }

    /**
     *
     * @param carColor
     * The car_color
     */
    public void setCarColor(Object carColor) {
        this.car_color = carColor;
    }

}
