package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.DataChoferCandidato;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChoferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "choferAdapter" ;
    private ChoferAdapter.BrokerItemClick mItemClickListener;
    private ArrayList<DataChoferCandidato> choferesList;
     int temp;


    public ChoferAdapter(List<DataChoferCandidato> choferes, BrokerItemClick brokerItemClick) {

        this.choferesList = (ArrayList<DataChoferCandidato>) choferes;
        mItemClickListener = (ChoferAdapter.BrokerItemClick) brokerItemClick;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.list_item_add_chofer, parent, false);
                return new ChoferAdapter.BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //   DataChofer currentItem = mItems.get(position);

        DataChoferCandidato currentItem = choferesList.get(position);
        ChoferAdapter.BrokerViewHolder brokerViewHolder = (ChoferAdapter.BrokerViewHolder) holder;

        Log.i(TAG, "ChoferAdapter: " + currentItem.getPersonNombre());

        brokerViewHolder.nombre.setText(currentItem.getPersonNombre());
        brokerViewHolder.correo.setText(currentItem.getPersonCorreo());
        brokerViewHolder.estatus.setText("Activo");




        //  brokerViewHolder.usersPerMonth.setText("Activo");

       /* Glide.with(mContext)
                .load(currentItem.getFeaturedImageResId())
                .centerCrop()
                .into(brokerViewHolder.featuredImage);

        Glide.with(mContext)
                .load(currentItem.getFeaturedImageResIdFav())
                .centerCrop()
                .into(brokerViewHolder.featuredImageFav);*/

    }

    @Override
    public int getItemCount() {

        return choferesList.size();
    }

    public class BrokerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView nombre;
        @BindView(R.id.tv_estatus)
        TextView estatus;
        @BindView(R.id.tv_correo)
        TextView correo;
        @BindView(R.id.iv_check)
        ImageView ivCheck;
        //  @BindView(R.id.tv_estatus) TextView estatus;

        public BrokerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mItemClickListener.onBrokerClick(choferesList.get(position));
            ivCheck.setImageResource(R.drawable.star_up);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(DataChoferCandidato clickedBroker);
    }
}
