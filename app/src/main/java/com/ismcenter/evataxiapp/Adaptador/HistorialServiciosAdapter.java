package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryAcceptService;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;

/**
 * Created by Desarrollo on 28/7/2016.
 */
public class HistorialServiciosAdapter extends RecyclerView.Adapter<HistorialServiciosAdapter.ViewHolder> {
    private ArrayList<ResponseHistoryAcceptService> arrayList;

    public HistorialServiciosAdapter(ArrayList<ResponseHistoryAcceptService> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_servicios_historial,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("arraylist", arrayList.toString());
        holder.respuestaNombre.setText(arrayList.get(position).getPerson_nombre());
        holder.respuestaFecha.setText(arrayList.get(position).getSer_fecha());
        holder.idServicio.setText( String.valueOf(arrayList.get(position).getSer_id()));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView respuestaNombre,respuestaFecha,idServicio;
        public ViewHolder(View itemView) {
            super(itemView);
            respuestaNombre = (TextView)itemView.findViewById(R.id.textRespuestaNombre);
            respuestaFecha = (TextView)itemView.findViewById(R.id.textResultadoFechaServicio);
            idServicio = (TextView)itemView.findViewById(R.id.textResultadoServicio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
        }
    }
}
