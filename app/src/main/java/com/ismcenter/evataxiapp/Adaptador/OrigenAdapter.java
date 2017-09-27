package com.ismcenter.evataxiapp.Adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Response.ResponseOrigenByZona;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrigenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DestinoAdapter" ;
    private BrokerItemClick mItemClickListener;
    private ArrayList<ResponseOrigenByZona> destinosList;
    private Context contexto;

    public OrigenAdapter(Context applicationContext, List<ResponseOrigenByZona> destinos, BrokerItemClick brokerItemClick) {
        this.destinosList = (ArrayList<ResponseOrigenByZona>) destinos;
        this.contexto = applicationContext;
        mItemClickListener = (OrigenAdapter.BrokerItemClick) brokerItemClick;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.list_item_zonas, parent, false);
                return new BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResponseOrigenByZona currentItem = destinosList.get(position);
        OrigenAdapter.BrokerViewHolder brokerViewHolder = (OrigenAdapter.BrokerViewHolder) holder;

        brokerViewHolder.nombre.setText(currentItem.tab_origen);

    }

    @Override
    public int getItemCount() {

        return destinosList.size();
    }

    public class BrokerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @BindView(R.id.tv_nombre)
        TextView nombre;


        public BrokerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mItemClickListener.onBrokerClick(destinosList.get(position));
            //   favoritosList.remove(position);
            //   notifyItemRemoved(position);
            //   notifyItemRangeChanged(position,favoritosList.size());
            //  Toast.makeText(contexto,"Eliminado : " + nombre.getText().toString() ,Toast.LENGTH_SHORT).show();
            //   ivCheck.setImageResource(R.drawable.ic_check);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(ResponseOrigenByZona clickedBroker);
    }


}
