package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Response.ResponsePaymentsMade;
import com.ismcenter.evataxiapp.R;

/**
 * Created by Desarrollo on 28/7/2016.
 */
public class PagosPendientesAdapter extends RecyclerView.Adapter<PagosPendientesAdapter.ViewHolder> {
    private ResponsePaymentsMade paymentsMade;
    private int tipoTransaccion;

    public PagosPendientesAdapter(ResponsePaymentsMade arrayList, int tipoTransaccion) {
        this.paymentsMade = arrayList;
        this.tipoTransaccion = tipoTransaccion;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_pagos_realizados,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("arraylist", paymentsMade.toString());

        if (tipoTransaccion == 1) {
            holder.idTransaccion.setText(paymentsMade.getTransferencias().get(position).getTrans_id());
            holder.idComprobante.setText(paymentsMade.getTransferencias().get(position).getTrans_comprobante());
            holder.fecha.setText(paymentsMade.getTransferencias().get(position).getTrans_fecha());
            holder.monto.setText(paymentsMade.getTransferencias().get(position).getTrans_monto());

        }if (tipoTransaccion == 2) {
            holder.idTransaccion.setText(paymentsMade.getDepositos().get(position).getDep_id());
            holder.idComprobante.setText(paymentsMade.getDepositos().get(position).getDep_comprobante());
            holder.fecha.setText(paymentsMade.getDepositos().get(position).getDep_fecha());
            holder.monto.setText(paymentsMade.getDepositos().get(position).getDep_monto());

        }if (tipoTransaccion == 3) {
            holder.idTransaccion.setText(paymentsMade.getInstapago().get(position).getInsta_id());
            holder.idComprobante.setText(paymentsMade.getInstapago().get(position).getInsta_codcomprobante());
            holder.fecha.setText(paymentsMade.getInstapago().get(position).getInsta_fecha());
            holder.monto.setText(paymentsMade.getInstapago().get(position).getInsta_monto());
        }


    }

    @Override
    public int getItemCount() {
        int size = 0;

        if (tipoTransaccion == 1){
            size = paymentsMade.getTransferencias().size();

        }if (tipoTransaccion == 2){
            size = paymentsMade.getDepositos().size();

        }if (tipoTransaccion == 3){
            size = paymentsMade.getInstapago().size();
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTransaccion, idComprobante, fecha, monto;
        public ViewHolder(View itemView) {
            super(itemView);
            idTransaccion = (TextView)itemView.findViewById(R.id.textRespuestaTransaccion);
            idComprobante = (TextView)itemView.findViewById(R.id.textRespuestaComprobante);
            fecha = (TextView)itemView.findViewById(R.id.textResultadoFecha);
            monto = (TextView)itemView.findViewById(R.id.textRespuestaMonto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
        }
    }
}
