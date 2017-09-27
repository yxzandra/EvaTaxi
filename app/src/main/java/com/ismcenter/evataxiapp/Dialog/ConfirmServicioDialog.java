package com.ismcenter.evataxiapp.Dialog;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Actividades.Choferes.ServicioActualFragment;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Request.RequestAcceptService;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;
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


public class ConfirmServicioDialog extends DialogFragment {

    static ResponseNearbyService clickNearbyDialog;
    @BindView(R.id.textNombrePasajero)
    TextView textNombrePasajero;
    @BindView(R.id.textTelefonoPasajero)
    TextView textTelefonoPasajero;
    @BindView(R.id.textCoordOrigen)
    TextView textCoordOrigen;
    @BindView(R.id.textCoordDestino)
    TextView textCoordDestino;

    private ProgressDialog loading;
    private PreferenceActualService preferenceActualService;
    private PreferencesManager preferencesManager;
    Fragment frag;
    FragmentTransaction fragTansaction;
    UpdateTitleToolbar mCallback;


    public static ConfirmServicioDialog newInstance(ResponseNearbyService clickNearby) {
        ConfirmServicioDialog f = new ConfirmServicioDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);
        clickNearbyDialog = clickNearby;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_acept_service, container, false);

        getDialog().setTitle("Deseas Aceptar el servicio?");
        ButterKnife.bind(this, rootView);

        preferencesManager = new PreferencesManager(getContext());
        preferenceActualService = new PreferenceActualService(getContext());

        textNombrePasajero.setText(textNombrePasajero.getText() + "" + clickNearbyDialog.getPerson_nombre());
        textTelefonoPasajero.setText(textTelefonoPasajero.getText() + "" + clickNearbyDialog.getPerson_telf());

        if (clickNearbyDialog.getSer_origen() != null) {

            textCoordOrigen.setText(textCoordOrigen.getText() + "" + clickNearbyDialog.getSer_origen());
            textCoordDestino.setText(textCoordDestino.getText() + "" + clickNearbyDialog.getSer_destino());

            preferenceActualService.setKeyCoordOrigen(clickNearbyDialog.getSer_origen());
            preferenceActualService.setKeyCoordDestin(clickNearbyDialog.getSer_destino());
            preferenceActualService.setKeyTypeService("SolicitudDeServicioManual");
        }else {
            textCoordOrigen.setText(textCoordOrigen.getText() + "" + clickNearbyDialog.getSer_coordenadaOrigen());
            textCoordDestino.setText(textCoordDestino.getText() + "" + clickNearbyDialog.getSer_CoordenadaDestino());

            preferenceActualService.setKeyCoordOrigen(clickNearbyDialog.getSer_CoordenadaDestino());
            preferenceActualService.setKeyCoordDestin(clickNearbyDialog.getSer_coordenadaOrigen());
            preferenceActualService.setKeyTypeService("SolicitudDeServicioAutomatica");


        }

        return rootView;
    }


    public void aceptarServicio() {
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);

        Log.d("EVATAXI_D", "aceptarServicio: idServicio=" + String.valueOf(clickNearbyDialog.getSer_id()));
        Log.d("EVATAXI_D", "aceptarServicio: idChofer=" + preferencesManager.getUserId());

        RequestAcceptService requestAcceptService = new RequestAcceptService
                (String.valueOf(clickNearbyDialog.getSer_id()), preferencesManager.getUserId());
        servicioRetrofit().acceptService(requestAcceptService).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                Log.e("on Response", response.message());
                if (response.code() == 200) {
                    respuestaServicio("Ha aceptado el servicio");
                    preferenceActualService.setKeyNameUser(clickNearbyDialog.getPerson_nombre());
                    preferenceActualService.setKeyTelephoneUser(clickNearbyDialog.getPerson_telf());
                    preferenceActualService.setKeyServiceId(clickNearbyDialog.getSer_id().toString());
                    preferenceActualService.setKeyIsFavorito("No tiene chofer Seleccionado");
                    preferenceActualService.setKeyAcceptService("true");

                    if (clickNearbyDialog.getSer_origen() != null) {
                        preferenceActualService.setKeyCoordOrigen(clickNearbyDialog.getSer_origen());
                        preferenceActualService.setKeyCoordDestin(clickNearbyDialog.getSer_destino());
                        preferenceActualService.setKeyTypeService("SolicitudDeServicioManual");

                    }else {

                        preferenceActualService.setKeyCoordOrigen(clickNearbyDialog.getSer_CoordenadaDestino());
                        preferenceActualService.setKeyCoordDestin(clickNearbyDialog.getSer_coordenadaOrigen());
                        preferenceActualService.setKeyTypeService("SolicitudDeServicioAutomatica");


                    }
                    cambiarStatusConductor();

                } else {
                    preferenceActualService.clearPreferences();
                    loading.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("EVATAXI_D", "onResponse: code=" + response.code());
                        Log.d("EVATAXI_D", "onResponse: " + response.errorBody().string());
                        Log.d("EVATAXI_D", "onResponse: " + response.message());
                        Log.d("EVATAXI_D", "onResponse: " + response.raw().body());
                        respuestaServicio(String.valueOf(jObjError.get("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                loading.dismiss();
                Log.e("onFailure", t.getMessage());
                Log.e("onFailure", t.getCause().toString());
            }
        });
    }

    private void cambiarStatusConductor() {
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        final RequestChangeStatus requestChangeStatus = new RequestChangeStatus(Integer.parseInt(preferencesManager.getPersonID()), 4);
        servicioRetrofit().changeStatusDriver(requestChangeStatus).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    respuestaServicio(response.body().getAsJsonObject().get("mensaje").getAsString());
                    llamarFragmento();
                } else {
                    preferenceActualService.clearPreferences();
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

    private void llamarFragmento() {
        mCallback.onUpdateTitleToolbar("Servicio Actual");
        frag = new ServicioActualFragment();
        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();
        getDialog().dismiss();
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

    private void respuestaServicio(String message) {
        loading.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

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

    @OnClick({R.id.btnCancelar, R.id.btnAceptar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelar:
                preferenceActualService.clearPreferences();
                getDialog().cancel();
                break;
            case R.id.btnAceptar:
                aceptarServicio();
                break;
        }
    }
}