package com.ismcenter.evataxiapp.Adaptador;



import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Interfaces.QualifyDriver;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseListQualifiers;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxzan on 10/28/2016.
 */

public class CalificarAdapter extends RecyclerView.Adapter<CalificarAdapter.ViewHolder> {

    private List<ResponseListQualifiers> arregloServiciosDisponibles;
    private QualifyDriver.OnRecyclerItemClickedListener onRecyclerItemClickedListener;

    public CalificarAdapter(ArrayList<ResponseListQualifiers> arregloServiciosDisponibles,
                                       QualifyDriver.OnRecyclerItemClickedListener onRecyclerItemClickedListener) {

        this.arregloServiciosDisponibles = arregloServiciosDisponibles;
        this.onRecyclerItemClickedListener = onRecyclerItemClickedListener;
    }

    @Override
    public CalificarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calificar, parent, false);
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

        holder.textNombre.setText(arregloServiciosDisponibles.get(position).getPerson_nombre());
        holder.textCorreo.setText(arregloServiciosDisponibles.get(position).getPerson_correo());
        holder.textServicio.setText(arregloServiciosDisponibles.get(position).getSer_id());
    }

    @Override
    public int getItemCount() {
        return arregloServiciosDisponibles.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textNombre,textCorreo,textServicio;

        public interface ViewHolderClickListener {
            void onItemClick(View caller, int position);
        }

        public ViewHolderClickListener mListener;

        public ViewHolder(View itemView, ViewHolderClickListener listener) {
            super(itemView);
            mListener = listener;
            textNombre = (TextView)itemView.findViewById(R.id.textResultadoNombre);
            textCorreo = (TextView)itemView.findViewById(R.id.textResultadoCorreo);
            textServicio = (TextView)itemView.findViewById(R.id.textResultadoServicio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
            mListener.onItemClick(view, getPosition());

        }
    }
}
