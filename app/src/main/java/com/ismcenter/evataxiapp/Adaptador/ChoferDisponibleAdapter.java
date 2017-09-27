package com.ismcenter.evataxiapp.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismcenter.evataxiapp.Modelos.DataChofer;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoferDisponibleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "choferAdapter" ;
    private BrokerItemClick mItemClickListener;
    private ArrayList<DataChofer> choferesList;
    private int temp;

    public ChoferDisponibleAdapter(List<DataChofer> choferes, BrokerItemClick brokerItemClick) {
        this.choferesList = (ArrayList<DataChofer>) choferes;
        mItemClickListener = (BrokerItemClick) brokerItemClick;

    }

/*  public ChoferDisponibleAdapter(Callback<ChoferModel> context,
                                   List<DataChofer> choferes,
                                   BrokerItemClick brokerItemClick) {
        mContext = context;
        mItems = choferes;
        mItemClickListener = brokerItemClick;

    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.list_item_chofer_disponible, parent, false);
                return new BrokerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     //   DataChofer currentItem = mItems.get(position);

        DataChofer currentItem = choferesList.get(position);
        BrokerViewHolder brokerViewHolder = (BrokerViewHolder) holder;

        brokerViewHolder.nombre.setText(currentItem.getPersonNombre());
        brokerViewHolder.correo.setText(currentItem.getPersonCorreo());



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
            ivCheck.setImageResource(R.drawable.ic_check);

        }

    }

    public interface BrokerItemClick {
        void onBrokerClick(DataChofer clickedBroker);
    }
}
