package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.PagosPendientesAdapter;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePaymentsMade;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PagosPendientesFragment extends Fragment {
    static String idUsuarioFragment;
    @BindView(R.id.textNotieneBancos)
    TextView textNotieneBancos;
    @BindView(R.id.recyclerTransferencias)
    RecyclerView recyclerTransferencias;
    @BindView(R.id.recyclerDepositos)
    RecyclerView recyclerDepositos;
    @BindView(R.id.recyclerInstapagos)
    RecyclerView recyclerInstapagos;
    @BindView(R.id.recyclerContainer)
    LinearLayout recyclerContainer;
    @BindView(R.id.textTransferencias)
    TextView textTransferencias;
    @BindView(R.id.textDepositos)
    TextView textDepositos;
    @BindView(R.id.textInstapagos)
    TextView textInstapagos;

    private String token;
    private ProgressDialog loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pagos_pendientes, container, false);
        ButterKnife.bind(this, view);

        PreferencesManager pm = new PreferencesManager(getContext());
        token = pm.getServerToken();
        idUsuarioFragment = pm.getUserId();

        return view;
    }

    public PagosPendientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        cargarServicios();
    }

    private void cargarServicios() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        //declaromos la interfaz
        //Integer.parseInt(idUsuario)

        DriverInterface servicio = retrofit.create(DriverInterface.class);
        servicio.pendingPayments(Integer.parseInt(idUsuarioFragment)).enqueue(new Callback<ResponsePaymentsMade>() {
            @Override
            public void onResponse(Call<ResponsePaymentsMade> call, Response<ResponsePaymentsMade> response) {


                if (response.code() == 200) {
                    if (response.body().getTransferencias().size() == 0 &&
                            response.body().getDepositos().size() == 0 &&
                            response.body().getInstapago().size() == 0) {

                        textNotieneBancos.setVisibility(View.VISIBLE);
                        recyclerTransferencias.setVisibility(View.GONE);
                    } else {
                        // respuestaServicio("UD ha realizado " + response.body().size() + " servicios");
                        textNotieneBancos.setVisibility(View.GONE);
                        recyclerTransferencias.setVisibility(View.VISIBLE);
                        cargarAdaptador(response.body());
                    }

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePaymentsMade> call, Throwable t) {
                loading.dismiss();
                textNotieneBancos.setVisibility(View.VISIBLE);
                recyclerTransferencias.setVisibility(View.GONE);
                textNotieneBancos.setText("El Servidor tardo mucho tiempo en responder");

            }
        });
    }

    private void cargarAdaptador(ResponsePaymentsMade data) {
        GridLayoutManager gridLayoutManager;

        //Transferencias
        if (data.getTransferencias().size() != 0){
            textTransferencias.setVisibility(View.VISIBLE);
            recyclerTransferencias.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerTransferencias.setLayoutManager(gridLayoutManager);
            recyclerTransferencias.setAdapter(new PagosPendientesAdapter(data, 1));

        }else{
            textTransferencias.setVisibility(View.GONE);
        }

        //Depositos
        if (data.getDepositos().size() != 0) {
            textDepositos.setVisibility(View.VISIBLE);
            recyclerDepositos.setHasFixedSize(true);
             gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerDepositos.setLayoutManager(gridLayoutManager);
            recyclerDepositos.setAdapter(new PagosPendientesAdapter(data, 2));

        }else{
            textDepositos.setVisibility(View.GONE);
        }

        //Instapagos
        if (data.getInstapago().size() != 0){
            textInstapagos.setVisibility(View.VISIBLE);
            recyclerInstapagos.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerInstapagos.setLayoutManager(gridLayoutManager);
            recyclerInstapagos.setAdapter(new PagosPendientesAdapter(data, 3));

        }else {
            textInstapagos.setVisibility(View.GONE);

        }
            recyclerContainer.setVisibility(View.VISIBLE);
            loading.dismiss();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
