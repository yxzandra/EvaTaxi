package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by yxzan on 25/12/2016.
 */
public class RequestRateDriver {
    private String coment;
    private String calif_chofer;
    private String ser_id;

    public RequestRateDriver(String coment, String calif_chofer, String ser_id) {
        this.coment = coment;
        this.calif_chofer = calif_chofer;
        this.ser_id = ser_id;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getCalif_chofer() {
        return calif_chofer;
    }

    public void setCalif_chofer(String calif_chofer) {
        this.calif_chofer = calif_chofer;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    @Override
    public String toString() {
        return "RequestRateDriver{" +
                "coment='" + coment + '\'' +
                ", calif_chofer='" + calif_chofer + '\'' +
                ", ser_id='" + ser_id + '\'' +
                '}';
    }
}
