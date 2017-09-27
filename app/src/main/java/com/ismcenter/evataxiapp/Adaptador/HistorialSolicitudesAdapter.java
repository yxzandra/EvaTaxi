package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryService;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistorialSolicitudesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "HistorialAdapter" ;
    private HistorialSolicitudesAdapter.BrokerItemClick mItemClickListener;
    private ArrayList<ResponseHistoryService> historialList;

    public HistorialSolicitudesAdapter(List<ResponseHistoryService> zonas, BrokerItemClick brokerItemClick) {
        this.historialList = (ArrayList<ResponseHistoryService>) zonas;
        mItemClickListener = (BrokerItemClick) brokerItemClick;
        Log.i(TAG, "historialAdapter: ");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.card_view_solicitudes_historial_pasajero, parent, false);
                return new HistorialSolicitudesAdapter.BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ResponseHistoryService currentItem = historialList.get(position);
        HistorialSolicitudesAdapter.BrokerViewHolder brokerViewHolder = (HistorialSolicitudesAdapter.BrokerViewHolder) holder;
        brokerViewHolder.nombre.setText(currentItem.getPerson_nombre());
        brokerViewHolder.cedula.setText("Correo: " + currentItem.getPerson_correo());
        brokerViewHolder.servicio.setText("Solicitud: "+ currentItem.getRecep_id().toString());
        brokerViewHolder.fecha.setText("Recep:: "+currentItem.getRecep_serid());



    }

    @Override
    public int getItemCount() {

        return historialList.size();
    }

    public class BrokerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @BindView(R.id.textRespuestaNombre)
        TextView nombre;
        @BindView(R.id.textResultadoServicio)
        TextView servicio;
        @BindView(R.id.textResultadoFechaServicio)
        TextView fecha;
        @BindView(R.id.textRespuestaCedula)
        TextView cedula;

        public BrokerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i(TAG, "onClick: ");


           mItemClickListener.onBrokerClick(historialList.get(position));
       //     ivCheck.setImageResource(R.drawable.star_up);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(ResponseHistoryService clickedBroker);
    }
}
