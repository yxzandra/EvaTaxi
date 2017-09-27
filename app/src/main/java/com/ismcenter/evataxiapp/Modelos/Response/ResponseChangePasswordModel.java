package com.ismcenter.evataxiapp.Modelos.Response;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResponseChangePasswordModel {

    private Boolean success;
    private Boolean data;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseChangePasswordModel() {
    }

    public ResponseChangePasswordModel(Boolean success, Boolean data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ResponseChangePasswordModel{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}