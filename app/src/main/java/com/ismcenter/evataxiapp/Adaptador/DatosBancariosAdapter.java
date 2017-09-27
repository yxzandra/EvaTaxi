package com.ismcenter.evataxiapp.Adaptador;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.ArregloDatosBancarios;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.OnItemClickAccountBank;

import java.util.ArrayList;


public class DatosBancariosAdapter extends RecyclerView.Adapter<DatosBancariosAdapter.ViewHolder>{
    private ArrayList<ArregloDatosBancarios> arregloDatosBancarios;
    private OnItemClickAccountBank onRecyclerItemClickedListener;

    public DatosBancariosAdapter(ArrayList<ArregloDatosBancarios> arregloDatosBancarios, OnItemClickAccountBank onRecyclerItemClickedListener) {
        this.arregloDatosBancarios = arregloDatosBancarios;
        this.onRecyclerItemClickedListener = onRecyclerItemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_datos_bancarios, parent, false);

        return new ViewHolder(v, new ViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClick(View caller, int position) {
                onRecyclerItemClickedListener.onItemClicked(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textNombreBanco.setText(arregloDatosBancarios.get(position).getNombreBanco());
        holder.textTipoCuenta.setText(arregloDatosBancarios.get(position).getTipoCuenta());
        holder.textNumeroCuenta.setText(arregloDatosBancarios.get(position).getNumeroCuenta());

    }

    @Override
    public int getItemCount() {
        return arregloDatosBancarios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textNombreBanco,textNumeroCuenta,textTipoCuenta;

        interface ViewHolderClickListener {
            void onItemClick(View caller, int position);
        }

        ViewHolderClickListener mListener;

        ViewHolder(View itemView, ViewHolderClickListener listener) {
            super(itemView);
            mListener = listener;
            textNombreBanco = (TextView)itemView.findViewById(R.id.textInfoBanco);
            textNumeroCuenta = (TextView)itemView.findViewById(R.id.textNumeroCuenta);
            textTipoCuenta = (TextView)itemView.findViewById(R.id.textTipoCuenta);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("Clicked");
            mListener.onItemClick(view, getPosition());

        }
    }
}
