package com.ismcenter.evataxiapp.Modelos;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FavoritosAddModel {

    private Boolean success;
    private Data data;
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public FavoritosAddModel() {
    }

    /**
     *
     * @param message
     * @param data
     * @param success
     */
    public FavoritosAddModel(Boolean success, Data data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    /**
     *
     * @return
     * The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
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