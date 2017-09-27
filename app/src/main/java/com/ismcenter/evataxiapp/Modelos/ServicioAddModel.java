package com.ismcenter.evataxiapp.Modelos;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ServicioAddModel {

    private Boolean success;
    private DataAddServicio data;
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ServicioAddModel() {
    }

    /**
     *
     * @param message
     * @param data
     * @param success
     */
    public ServicioAddModel(Boolean success, DataAddServicio data, String message) {
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
    public DataAddServicio getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataAddServicio data) {
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