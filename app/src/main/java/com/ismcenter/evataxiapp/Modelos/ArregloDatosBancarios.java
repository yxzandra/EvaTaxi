package com.ismcenter.evataxiapp.Modelos;


public class ArregloDatosBancarios {
    private String nombreBanco;
    private String tipoCuenta;
    private String numeroCuenta;
    private String nombrePersona;
    private int dab_id;

    public ArregloDatosBancarios(String nombreBanco, String tipoCuenta, String numeroCuenta, String nombrePersona, int dab_id) {
        this.nombreBanco = nombreBanco;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.nombrePersona = nombrePersona;
        this.dab_id = dab_id;
    }

    @Override
    public String toString() {
        return "ArregloDatosBancarios{" +
                "nombreBanco='" + nombreBanco + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", nombrePersona='" + nombrePersona + '\'' +
                ", dab_id=" + dab_id +
                '}';
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public int getDab_id() {
        return dab_id;
    }

    public void setDab_id(int dab_id) {
        this.dab_id = dab_id;
    }
}
