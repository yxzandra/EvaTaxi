package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseTarifa {

    private Integer tarifa;
    private Integer incrementos;
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseTarifa() {
    }

    /**
     *
     * @param message
     * @param incrementos
     * @param tarifa
     */
    public ResponseTarifa(Integer tarifa, Integer incrementos, String message) {
        super();
        this.tarifa = tarifa;
        this.incrementos = incrementos;
        this.message = message;
    }

    /**
     *
     * @return
     * The tarifa
     */
    public Integer getTarifa() {
        return tarifa;
    }

    /**
     *
     * @param tarifa
     * The tarifa
     */
    public void setTarifa(Integer tarifa) {
        this.tarifa = tarifa;
    }

    /**
     *
     * @return
     * The incrementos
     */
    public Integer getIncrementos() {
        return incrementos;
    }

    /**
     *
     * @param incrementos
     * The incrementos
     */
    public void setIncrementos(Integer incrementos) {
        this.incrementos = incrementos;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}