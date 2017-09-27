package com.ismcenter.evataxiapp.Modelos;

import com.ismcenter.evataxiapp.Modelos.Request.RequestDeposito;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDeposito;

public class DataDeposito {

    public String message;
    public RequestDeposito deposito;
    public ResponseDeposito pago;

    public DataDeposito(String message, RequestDeposito deposito, ResponseDeposito pago) {
        this.message = message;
        this.deposito = deposito;
        this.pago = pago;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequestDeposito getDeposito() {
        return deposito;
    }

    public void setDeposito(RequestDeposito deposito) {
        this.deposito = deposito;
    }

    public ResponseDeposito getPago() {
        return pago;
    }

    public void setPago(ResponseDeposito pago) {
        this.pago = pago;
    }
}