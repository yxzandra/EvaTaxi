package com.ismcenter.evataxiapp.Modelos.Response;

import com.ismcenter.evataxiapp.Modelos.DataAutocompletar;

import java.util.List;

public class ResponseAutocompletar {

    public Boolean success;
    public List<DataAutocompletar> data = null;
    public String message;


    public ResponseAutocompletar(Boolean success, List<DataAutocompletar> data, String message) {
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

    public List<DataAutocompletar> getData() {
        return data;
    }

    public void setData(List<DataAutocompletar> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseAutocompletar{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}

