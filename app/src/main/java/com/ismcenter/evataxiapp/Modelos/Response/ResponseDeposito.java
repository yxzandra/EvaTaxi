package com.ismcenter.evataxiapp.Modelos.Response;

public class ResponseDeposito {

    public String pago_person_id;
    public Integer pago_tipo;
    public Integer pago_ref_id;
    public String pagos_fecha;
    public String pagos_monto;
    public Integer pago_id;

    public ResponseDeposito(String pago_person_id, Integer pago_tipo, Integer pago_ref_id, String pagos_fecha, String pagos_monto, Integer pago_id) {
        this.pago_person_id = pago_person_id;
        this.pago_tipo = pago_tipo;
        this.pago_ref_id = pago_ref_id;
        this.pagos_fecha = pagos_fecha;
        this.pagos_monto = pagos_monto;
        this.pago_id = pago_id;
    }

    public String getPago_person_id() {
        return pago_person_id;
    }

    public void setPago_person_id(String pago_person_id) {
        this.pago_person_id = pago_person_id;
    }

    public Integer getPago_tipo() {
        return pago_tipo;
    }

    public void setPago_tipo(Integer pago_tipo) {
        this.pago_tipo = pago_tipo;
    }

    public Integer getPago_ref_id() {
        return pago_ref_id;
    }

    public void setPago_ref_id(Integer pago_ref_id) {
        this.pago_ref_id = pago_ref_id;
    }

    public String getPagos_fecha() {
        return pagos_fecha;
    }

    public void setPagos_fecha(String pagos_fecha) {
        this.pagos_fecha = pagos_fecha;
    }

    public String getPagos_monto() {
        return pagos_monto;
    }

    public void setPagos_monto(String pagos_monto) {
        this.pagos_monto = pagos_monto;
    }

    public Integer getPago_id() {
        return pago_id;
    }

    public void setPago_id(Integer pago_id) {
        this.pago_id = pago_id;
    }
}

