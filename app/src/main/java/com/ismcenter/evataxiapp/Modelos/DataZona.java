package com.ismcenter.evataxiapp.Modelos;


import com.ismcenter.evataxiapp.Modelos.Response.ResponseZonas;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataZona {


    public Boolean success;
    public List<ResponseZonas> data = null;
    public String message;

    @Override
    public String toString() {
        return "DataZona{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}