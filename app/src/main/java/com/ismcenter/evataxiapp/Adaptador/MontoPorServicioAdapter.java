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
public class MontoPorServicioAdapter extends RecyclerView.Adapter<MontoPorServicioAdapter.ViewHolder> {
    private ArrayList<ResponseHistoryAcceptService> arrayList;

    public MontoPorServicioAdapter(ArrayList<ResponseHistoryAcceptService> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_monto_por_servicio,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("arraylist", arrayList.toString());
        holder.idServicio.setText(arrayList.get(position).getPerson_nombre());
        holder.fecha.setText(arrayList.get(position).getSer_fecha());
        holder.monto.setText( String.valueOf(arrayList.get(position).getSer_id()));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idServicio, fecha, monto;
        public ViewHolder(View itemView) {
            super(itemView);
            idServicio = (TextView)itemView.findViewById(R.id.textRespuestaIdServicio);
            fecha = (TextView)itemView.findViewById(R.id.textRespuestaFecha);
            monto = (TextView)itemView.findViewById(R.id.textResultadoMonto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
        }
    }
}
