package com.ismcenter.evataxiapp.Actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Actividades.Choferes.ServiciosDisponiblesFragment;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.PerfilPasajeroFragment;
import com.ismcenter.evataxiapp.Adaptador.CalificarAdapter;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Interfaces.QualifyDriver;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseListQualifiers;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

/**
 * Created by Desarrollo on 23/11/2016.
 */
public class ListarCalificarFragment extends Fragment implements QualifyDriver.OnRecyclerItemClickedListener {
    @BindView(R.id.brokers_list)
    RecyclerView brokersList;
    @BindView(R.id.textNotieneServicios)
    TextView textNotieneServicios;

    private CalificarAdapter adapter;
    private ProgressDialog loading;
    PreferencesManager preferencesManager;

    Fragment frag;
    FragmentTransaction fragTansaction;
    Toolbar toolbar;


    public ListarCalificarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_calificar, container, false);
        ButterKnife.bind(this, view);

        preferencesManager = new PreferencesManager(getContext());
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        initViews();
        return view;
    }

    private void initViews() {
        brokersList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        brokersList.setLayoutManager(layoutManager);
        service();

    }

    //se solicita el servicio
    public void service() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + preferencesManager.getServerToken())
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

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.getListarPorCalificar(preferencesManager.getUserId()).enqueue(new Callback<ArrayList<ResponseListQualifiers>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseListQualifiers>> call, Response<ArrayList<ResponseListQualifiers>> response) {
                loading.dismiss();

                if (response.code() == 200) {
                    if (response.body().size() == 0) {
                        textNotieneServicios.setVisibility(View.VISIBLE);
                        brokersList.setVisibility(View.GONE);
                    } else {
                        textNotieneServicios.setVisibility(View.GONE);
                        brokersList.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ArrayList<ResponseListQualifiers>> call, Throwable t) {

            }
        });
    }

    private void cargarAdaptador(ArrayList<ResponseListQualifiers> data) {
        brokersList.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        brokersList.setLayoutManager(gridLayoutManager);
        brokersList.setAdapter(new CalificarAdapter(data, this));
    }

    @Override
    public void onRecyclerItemClicked(ResponseListQualifiers clickDriver) {

        //mCallback.onUpdateTitleToolbar("Servicios");
        frag = new CalificarFragment();
        frag = CalificarFragment.newInstance(clickDriver);
        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();

    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
