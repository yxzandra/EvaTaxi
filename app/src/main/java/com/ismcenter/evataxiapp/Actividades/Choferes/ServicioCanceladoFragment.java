package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Notifications.CancelacionDeSolicitud;
import com.ismcenter.evataxiapp.Modelos.Notifications.Notificacion;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.Modelos.Request.RequestIdSolicitud;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferenceActualService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class ServicioCanceladoFragment extends Fragment {
    private static Notificacion notificacionFragment;
    PreferenceActualService preferenceActualService;
    PreferencesManager preferencesManager;
    RequestIdSolicitud requestIdSolicitud;
    UpdateTitleToolbar mCallback;
    Fragment frag;
    FragmentTransaction fragTansaction;
    @BindView(R.id.editIdServicio)
    EditText editIdServicio;
    @BindView(R.id.editNombrePasajero)
    EditText editNombrePasajero;
    private ProgressDialog loading;
    private CancelacionDeSolicitud cancelacionDeSolicitud;


    public ServicioCanceladoFragment() {
    }

    public static ServicioCanceladoFragment newInstance(Notificacion notificacion) {
        ServicioCanceladoFragment.notificacionFragment = notificacion;
        return new ServicioCanceladoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicio_cancelado, container, false);
        ButterKnife.bind(this, view);
        preferenceActualService = new PreferenceActualService(getContext());
        preferencesManager = new PreferencesManager(getContext());
        cancelacionDeSolicitud = (CancelacionDeSolicitud) notificacionFragment;

        editIdServicio.setText(editIdServicio.getText().toString()+""+cancelacionDeSolicitud.getId_solicitud());
        editNombrePasajero.setText(editNombrePasajero.getText().toString()+""+cancelacionDeSolicitud.getMotivo());
        preferenceActualService.clearPreferences();
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        habilitarStatusChofer();

        return view;
    }


    private void habilitarStatusChofer() {
        preferenceActualService.clearPreferences();
        final RequestChangeStatus requestChangeStatus = new RequestChangeStatus(Integer.parseInt(preferencesManager.getPersonID()), 3);
        servicioRetrofit().changeStatusDriver(requestChangeStatus).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    respuestaServicio(response.body().getAsJsonObject().get("mensaje").getAsString());
                   // respuestaServicio(response.body().getAsJsonObject().get("mensaje").getAsString());

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("mensaje")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private DriverInterface servicioRetrofit() {
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
        //declaramos la interfaz

        return retrofit.create(DriverInterface.class);
    }

    public void llamarFragmento() {

        mCallback.onUpdateTitleToolbar("Servicios");
        frag = new ServiciosDisponiblesFragment();

        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (UpdateTitleToolbar) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.btnVolver)
    public void onClick() {
        llamarFragmento();
    }
}
