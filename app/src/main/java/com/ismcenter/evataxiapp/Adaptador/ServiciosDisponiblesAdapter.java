package com.ismcenter.evataxiapp.Adaptador;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.AcceptService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxzan on 10/28/2016.
 */

public class ServiciosDisponiblesAdapter extends RecyclerView.Adapter<ServiciosDisponiblesAdapter.ViewHolder> {
    private List<ResponseNearbyService> arregloServiciosDisponibles;
    private Context context;
    private AcceptService.OnRecyclerItemClickedListener onRecyclerItemClickedListener;
    private String idUsuario;

    public ServiciosDisponiblesAdapter(Context context, ArrayList<ResponseNearbyService> arregloServiciosDisponibles,
                                       String idUsuario,
                                       AcceptService.OnRecyclerItemClickedListener onRecyclerItemClickedListener) {
        this.arregloServiciosDisponibles = arregloServiciosDisponibles;
        this.context = context;
        this.onRecyclerItemClickedListener = onRecyclerItemClickedListener;
        this.idUsuario = idUsuario;
    }

    @Override
    public ServiciosDisponiblesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_servicios_disponibles, parent, false);
        ViewHolder holder = new ViewHolder(v, new ViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClick(View caller, int position) {

                onRecyclerItemClickedListener.onRecyclerItemClicked(arregloServiciosDisponibles.get(position));
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (arregloServiciosDisponibles.get(position).getSer_origen() != null) {
            holder.textOrigenServicio.setText(arregloServiciosDisponibles.get(position).getSer_origen());
            holder.textDestinoServicio.setText(arregloServiciosDisponibles.get(position).getSer_destino());
        }else{
            holder.textOrigenServicio.setText(arregloServiciosDisponibles.get(position).getSer_coordenadaOrigen());
            holder.textDestinoServicio.setText(arregloServiciosDisponibles.get(position).getSer_CoordenadaDestino());
        }


        holder.textFechaServicio.setText(arregloServiciosDisponibles.get(position).getSer_fecha());

    }

    @Override
    public int getItemCount() {
        return arregloServiciosDisponibles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textOrigenServicio,textDestinoServicio,textFechaServicio;

        public interface ViewHolderClickListener {
            void onItemClick(View caller, int position);
        }

        public ViewHolderClickListener mListener;

        public ViewHolder(View itemView, ViewHolderClickListener listener) {
            super(itemView);
            mListener = listener;
            textOrigenServicio = (TextView)itemView.findViewById(R.id.textResultadoOrigenServicio);
            textDestinoServicio = (TextView)itemView.findViewById(R.id.textResultadoDestinoServicio);
            textFechaServicio = (TextView)itemView.findViewById(R.id.textResultadoFechaServicio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
            mListener.onItemClick(view, getPosition());

        }
    }
}
