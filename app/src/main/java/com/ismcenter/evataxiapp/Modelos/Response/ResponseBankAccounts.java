package com.ismcenter.evataxiapp.Modelos.Response;


public class ResponseBankAccounts {

    private Integer dab_id;
    private Integer usu_id;
    private String dab_banco;
    private String dab_cuenta;
    private String dab_tipoCuenta;
    private String dab_nombre;
    private Integer person_id;

    public ResponseBankAccounts(Integer dab_id, Integer usu_id, String dab_banco, String dab_cuenta, String dab_tipoCuenta, String dab_nombre, Integer person_id) {
        super();
        this.dab_id = dab_id;
        this.usu_id = usu_id;
        this.dab_banco = dab_banco;
        this.dab_cuenta = dab_cuenta;
        this.dab_tipoCuenta = dab_tipoCuenta;
        this.dab_nombre = dab_nombre;
        this.person_id = person_id;
    }

    public Integer getDabId() {
        return dab_id;
    }

    public void setDabId(Integer dabId) {
        this.dab_id = dabId;
    }

    public Integer getUsuId() {
        return usu_id;
    }

    public void setUsuId(Integer usuId) {
        this.usu_id = usuId;
    }

    public String getDabBanco() {
        return dab_banco;
    }

    public void setDabBanco(String dabBanco) {
        this.dab_banco = dabBanco;
    }

    public String getDabCuenta() {
        return dab_cuenta;
    }

    public void setDabCuenta(String dabCuenta) {
        this.dab_cuenta = dabCuenta;
    }

    public String getDabTipoCuenta() {
        return dab_tipoCuenta;
    }

    public void setDabTipoCuenta(String dabTipoCuenta) {
        this.dab_tipoCuenta = dabTipoCuenta;
    }

    public String getDabNombre() {
        return dab_nombre;
    }

    public void setDabNombre(String dabNombre) {
        this.dab_nombre = dabNombre;
    }

    public Integer getPersonId() {
        return person_id;
    }

    public void setPersonId(Integer personId) {
        this.person_id = personId;
    }

}