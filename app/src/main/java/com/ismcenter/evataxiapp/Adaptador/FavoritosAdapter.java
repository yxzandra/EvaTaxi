package com.ismcenter.evataxiapp.Adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.Response.ResponseFavoritosModel;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "FavAdapter" ;
    private BrokerItemClick mItemClickListener;
    private ArrayList<ResponseFavoritosModel> favoritosList;
    private int temp;
    private Context contexto;
    private String idPersona;

    public FavoritosAdapter(Context applicationContext, List<ResponseFavoritosModel> favoritos, BrokerItemClick brokerItemClick) {
        this.favoritosList = (ArrayList<ResponseFavoritosModel>) favoritos;
        this.contexto = applicationContext;
        mItemClickListener = (FavoritosAdapter.BrokerItemClick) brokerItemClick;
        Log.i(TAG, "FavoritosAdapter: ");

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.list_item_chofer_favoritos, parent, false);
                return new BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResponseFavoritosModel currentItem = favoritosList.get(position);
        FavoritosAdapter.BrokerViewHolder brokerViewHolder = (FavoritosAdapter.BrokerViewHolder) holder;

        brokerViewHolder.nombre.setText(currentItem.getPersonNombre());
        brokerViewHolder.direccion.setText(currentItem.getPersonCorreo());

        if(currentItem.getPersonEstatus() == 1)brokerViewHolder.estatus.setText("Activo");
           else brokerViewHolder.estatus.setText("Inactivo");


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

        return favoritosList.size();
    }

    public class BrokerViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView nombre;
        @BindView(R.id.tv_estatus)
        TextView estatus;
        @BindView(R.id.tv_direccion)
        TextView direccion;

        public BrokerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
             mItemClickListener.onBrokerClick(favoritosList.get(position));
            //   favoritosList.remove(position);
            //   notifyItemRemoved(position);
            //   notifyItemRangeChanged(position,favoritosList.size());
            //  Toast.makeText(contexto,"Eliminado : " + nombre.getText().toString() ,Toast.LENGTH_SHORT).show();


            //   ivCheck.setImageResource(R.drawable.ic_check);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(ResponseFavoritosModel clickedBroker);
    }


}
