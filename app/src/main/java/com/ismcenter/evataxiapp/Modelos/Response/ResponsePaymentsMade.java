package com.ismcenter.evataxiapp.Modelos.Response;

import java.util.List;

public class ResponsePaymentsMade {

    private List<Transferencia> transferencias = null;
    private List<Deposito> depositos = null;
    private List<Instapago> instapago = null;

    public ResponsePaymentsMade(List<Transferencia> transferencias, List<Deposito> depositos,
                                List<Instapago> instapago) {
        this.transferencias = transferencias;
        this.depositos = depositos;
        this.instapago = instapago;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public List<Deposito> getDepositos() {
        return depositos;
    }

    public void setDepositos(List<Deposito> depositos) {
        this.depositos = depositos;
    }

    public List<Instapago> getInstapago() {
        return instapago;
    }

    public void setInstapago(List<Instapago> instapago) {
        this.instapago = instapago;
    }

    @Override
    public String toString() {
        return "ResponsePaymentsMade{" +
                "transferencias=" + transferencias +
                ", depositos=" + depositos +
                ", instapago=" + instapago +
                '}';
    }
}