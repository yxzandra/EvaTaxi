package com.ismcenter.evataxiapp.Actividades.Choferes;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Dialog.MapServiceDriveDialog;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Notifications.Notificacion;
import com.ismcenter.evataxiapp.Modelos.Notifications.SolicitudDeServicioAutomatica;
import com.ismcenter.evataxiapp.Modelos.Notifications.SolicitudDeServicioManual;
import com.ismcenter.evataxiapp.Modelos.Request.RequestAcceptService;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.Modelos.Request.RequestIdSolicitud;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePasajeroProfileModel;
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

public class ReceptSolicitudFragment extends Fragment {
    @BindView(R.id.btnDescartar)
    Button btnDescartar;
    @BindView(R.id.btnVerMapa)
    Button btnVerMapa;
    private ProgressDialog loading;
    private PreferenceActualService preferenceActualService;
    private PreferencesManager pm;
    UpdateTitleToolbar mCallback;
    static Notificacion notificacionFragment;
    private String token, idPasajero, idChofer, idServicio, idPersona;
    SolicitudDeServicioAutomatica solicitudAutomatica;
    SolicitudDeServicioManual solicitudManual;
    Fragment frag;
    FragmentTransaction fragTansaction;
    @BindView(R.id.textTituloSolicitud)
    TextView textTituloSolicitud;
    @BindView(R.id.textSubtitulo)
    TextView textSubtitulo;
    @BindView(R.id.editNombrePasajero)
    EditText editNombrePasajero;
    @BindView(R.id.editTelefono)
    EditText editTelefono;
    @BindView(R.id.editOrigen)
    EditText editOrigen;
    @BindView(R.id.editDestino)
    EditText editDestino;
    @BindView(R.id.editFavorito)
    EditText editFavorito;
    @BindView(R.id.btnRechazar)
    Button btnRechazar;
    private String idUsuarioPassenger;

    public static ReceptSolicitudFragment newInstance(Notificacion notificacion) {
        ReceptSolicitudFragment.notificacionFragment = notificacion;
        return new ReceptSolicitudFragment();
    }

    public ReceptSolicitudFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recep_solicitud, container, false);
        ButterKnife.bind(this, view);
        pm = new PreferencesManager(getContext());
        preferenceActualService = new PreferenceActualService(getContext());
        token = pm.getServerToken();
        idPersona = pm.getPersonID();
        idChofer = pm.getUserId();

        if (notificacionFragment.getTipo().equals("SolicitudDeServicioAutomatica")) {
            solicitudAutomatica = (SolicitudDeServicioAutomatica) notificacionFragment;
            idUsuarioPassenger = solicitudAutomatica.getIdUsuario();
            idPasajero = solicitudAutomatica.getIdPasajero();
            idServicio = solicitudAutomatica.getId_solicitud();
            btnVerMapa.setVisibility(View.VISIBLE);
            preferenceActualService.setKeyTypeService(notificacionFragment.getTipo());
        }
        if (notificacionFragment.getTipo().equals("SolicitudDeServicioManual")) {
            solicitudManual = (SolicitudDeServicioManual) notificacionFragment;
            idUsuarioPassenger = solicitudManual.getIdUsuario();
            idPasajero = solicitudManual.getIdPasajero();
            idServicio = solicitudManual.getId_solicitud();
            btnVerMapa.setVisibility(View.GONE);
            preferenceActualService.setKeyTypeService(notificacionFragment.getTipo());
        }
        cargarFragmento();
       // getNombrePasajero(idPasajero);
        //loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        return view;
    }

    private void cargarFragmento() {

        if (notificacionFragment.getTipo().equals("SolicitudDeServicioAutomatica")) {
            textTituloSolicitud.setText(solicitudAutomatica.getTitle() + " Automatica");
            textSubtitulo.setText(solicitudAutomatica.getId_solicitud());
            editNombrePasajero.setText(solicitudAutomatica.getNombre());
            editOrigen.setText(solicitudAutomatica.getCoordOrig());
            editDestino.setText(solicitudAutomatica.getCoordDest());
            editTelefono.setText(solicitudAutomatica.getTelef());

            if (solicitudAutomatica.getIs_favorito().equals("true")) {
                editFavorito.setText("Esta Seleccionado por el pasajero");
                btnDescartar.setVisibility(View.GONE);

            } else {
                editFavorito.setText("No tiene chofer Seleccionado");
                btnRechazar.setVisibility(View.GONE);
            }

        }
        if (notificacionFragment.getTipo().equals("SolicitudDeServicioManual")) {
            textTituloSolicitud.setText(solicitudManual.getTitle() + " Manual");
            textSubtitulo.setText(solicitudManual.getId_solicitud());
            editNombrePasajero.setText(solicitudManual.getNombre());
            editOrigen.setText(solicitudManual.getSer_origen());
            editDestino.setText(solicitudManual.getSer_destino());
            editTelefono.setText(solicitudManual.getTelef());

            if (solicitudManual.getIs_favorito().equals("true")) {
                editFavorito.setText("Esta Seleccionado por el pasajero");
                btnDescartar.setVisibility(View.GONE);

            } else {
                editFavorito.setText("No tiene chofer Seleccionado");
                btnRechazar.setVisibility(View.GONE);
            }
        }


    }


    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }


    public void rechazarServicio() {

        RequestIdSolicitud requestIdSolicitud = new RequestIdSolicitud(idServicio);
        servicioRetrofit().declineService(requestIdSolicitud).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    respuestaServicio("Ha rechazado el servicio");
                    preferenceActualService.clearPreferences();
                    llamarFragmento();
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
                loading.dismiss();
                Log.e("onFailure", t.getMessage());
                Log.e("onFailure", t.getCause().toString());

            }
        });
    }

    public void llamarFragmento() {
        if (preferenceActualService.getKeyServiceId().equals("null")) {
            mCallback.onUpdateTitleToolbar("Servicios");
            frag = new ServiciosDisponiblesFragment();

        } else {
            mCallback.onUpdateTitleToolbar("Servicio Actual");
            frag = new ServicioActualFragment();
        }

        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();
    }

    public void aceptarServicio() {

        Log.d("EVATAXI_D", "aceptarServicio: idServicio=" + idServicio);
        Log.d("EVATAXI_D", "aceptarServicio: idChofer=" + pm.getUserId());
        RequestAcceptService requestAcceptService = new RequestAcceptService(idServicio, pm.getUserId());
        servicioRetrofit().acceptService(requestAcceptService).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                Log.e("on Response", response.message());
                if (response.code() == 200) {
                    respuestaServicio("Ha aceptado el servicio");
                    preferenceActualService.setKeyAcceptService("true");
                    preferenceActualService.setUserServicePassenger(idChofer);
                    preferenceActualService.setPersonServicePassenger(idPasajero);
                    preferenceActualService.setKeyIsFavorito(editFavorito.getText().toString());
                    preferenceActualService.setKeyTelephoneUser(editTelefono.getText().toString());
                    preferenceActualService.setKeyCoordDestin(editDestino.getText().toString());
                    preferenceActualService.setKeyCoordOrigen(editOrigen.getText().toString());
                    preferenceActualService.setKeyServiceId(idServicio);
                    preferenceActualService.setKeyNameUser(editNombrePasajero.getText().toString());
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
                        respuestaServicio(String.valueOf(jObjError.get("mensaje")));
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
        final RequestChangeStatus requestChangeStatus = new RequestChangeStatus(Integer.parseInt(idPersona), 4);
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

    private DriverInterface servicioRetrofit() {
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
        //declaramos la interfaz

        return retrofit.create(DriverInterface.class);
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

    @OnClick({R.id.btnVerMapa, R.id.btnDescartar, R.id.btnRechazar, R.id.btnAceptar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerMapa:
                MapServiceDriveDialog mapServiceDriveDialog = new MapServiceDriveDialog();
                MapServiceDriveDialog.newInstance(preferenceActualService.getKeyCoordOrigen(),
                        preferenceActualService.getKeyCoordDestin());
                showDialogFragment(mapServiceDriveDialog);
                break;
            case R.id.btnDescartar:
                preferenceActualService.clearPreferences();
                llamarFragmento();
                break;
            case R.id.btnRechazar:
                loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
                rechazarServicio();
                break;
            case R.id.btnAceptar:
                loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
                aceptarServicio();
                break;
        }
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }
}
