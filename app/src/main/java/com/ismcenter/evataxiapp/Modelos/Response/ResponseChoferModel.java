package com.ismcenter.evataxiapp.Modelos.Response;

import com.ismcenter.evataxiapp.Modelos.DataChofer;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResponseChoferModel {

    private Boolean success;
    private List<DataChofer> data = null;
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseChoferModel() {
    }

    /**
     *
     * @param message
     * @param data
     * @param success
     */
    public ResponseChoferModel(Boolean success, List<DataChofer> data, String message) {
        super();
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
    public List<DataChofer> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<DataChofer> data) {
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

