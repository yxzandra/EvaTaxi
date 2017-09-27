package com.ismcenter.evataxiapp.Modelos.Request;

/**
 * Created by yxzan on 17/12/2016.
 */
public class RequestChangeStatus {
    private int id;
    private int status;

    public RequestChangeStatus(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestChangeStatus{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
