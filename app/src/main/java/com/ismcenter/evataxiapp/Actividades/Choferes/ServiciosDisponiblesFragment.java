package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.ServiciosDisponiblesAdapter;
import com.ismcenter.evataxiapp.Dialog.ConfirmServicioDialog;
import com.ismcenter.evataxiapp.Interfaces.AcceptService;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Modelos.Request.RequestIdZona;
import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;
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

public class ServiciosDisponiblesFragment extends Fragment implements AcceptService.OnRecyclerItemClickedListener {

    private ProgressDialog loading;
    PreferencesManager pm;
    @BindView(R.id.textNotieneServicios)
    TextView textNotieneServicios;
    @BindView(R.id.recyclerViewServiciosDisponibles)
    RecyclerView recyclerViewServiciosDisponibles;


    public ServiciosDisponiblesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicios_disponibles, container, false);
        ButterKnife.bind(this, view);
        pm = new PreferencesManager(getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarServicios();
    }

    private void cargarServicios() {

        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + pm.getServerToken())
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
        RequestIdZona requestIdZona = new RequestIdZona("7");
        servicio.ListNearbyServices(requestIdZona).enqueue(new Callback<ArrayList<ResponseNearbyService>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseNearbyService>> call, Response<ArrayList<ResponseNearbyService>> response) {
                loading.dismiss();

                if (response.code() == 200) {
                    if (response.body().size() == 0) {
                        textNotieneServicios.setVisibility(View.VISIBLE);
                        recyclerViewServiciosDisponibles.setVisibility(View.GONE);
                    } else {
                            textNotieneServicios.setVisibility(View.GONE);
                            recyclerViewServiciosDisponibles.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<ArrayList<ResponseNearbyService>> call, Throwable t) {
                loading.dismiss();
                textNotieneServicios.setVisibility(View.VISIBLE);
                recyclerViewServiciosDisponibles.setVisibility(View.GONE);
                textNotieneServicios.setText("El Servidor tardo mucho tiempo en responder");

            }
        });
    }

    private void cargarAdaptador(ArrayList<ResponseNearbyService> data) {
        recyclerViewServiciosDisponibles.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewServiciosDisponibles.setLayoutManager(gridLayoutManager);
        recyclerViewServiciosDisponibles.setAdapter(new ServiciosDisponiblesAdapter(getContext(), data, pm.getUserId(), this));
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onRecyclerItemClicked(ResponseNearbyService clickService) {

        ConfirmServicioDialog alertdFragment = new ConfirmServicioDialog();
        ConfirmServicioDialog.newInstance(clickService);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(alertdFragment, alertdFragment.getTag());
        ft.commitAllowingStateLoss();

    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
