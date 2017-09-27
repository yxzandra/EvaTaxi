package com.ismcenter.evataxiapp.Modelos;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DatosBancariosModel {

    private Boolean success;
    private List<DataDatosBancarios> data = new ArrayList<DataDatosBancarios>();
    private String message;

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
    public List<DataDatosBancarios> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<DataDatosBancarios> data) {
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