package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.DataAutocompletar;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZonaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "zonaAdapter" ;
    private ZonaAdapter.BrokerItemClick mItemClickListener;
    private ArrayList<DataAutocompletar> zonasList;




    public ZonaAdapter(List<DataAutocompletar> zonas, BrokerItemClick brokerItemClick) {
        this.zonasList = (ArrayList<DataAutocompletar>) zonas;
        mItemClickListener = (BrokerItemClick) brokerItemClick;
        Log.i(TAG, "ZonaAdapter: ");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.list_item_zonas, parent, false);
                return new ZonaAdapter.BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataAutocompletar currentItem = zonasList.get(position);
        ZonaAdapter.BrokerViewHolder brokerViewHolder = (ZonaAdapter.BrokerViewHolder) holder;

        brokerViewHolder.nombre.setText(currentItem.getZon_nombre().toString());


    }

    @Override
    public int getItemCount() {

        return zonasList.size();
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
            Log.i(TAG, "onClick: ");


           mItemClickListener.onBrokerClick(zonasList.get(position));
       //     ivCheck.setImageResource(R.drawable.star_up);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(DataAutocompletar clickedBroker);
    }
}
