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
import com.ismcenter.evataxiapp.Actividades.CalificarFragment;
import com.ismcenter.evataxiapp.Dialog.MapServiceDriveDialog;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
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
public class ServicioActualFragment extends Fragment {
    PreferenceActualService preferenceActualService;
    PreferencesManager preferencesManager;
    RequestIdSolicitud requestIdSolicitud;
    UpdateTitleToolbar mCallback;
    Fragment frag;
    FragmentTransaction fragTansaction;
    MapServiceDriveDialog mapServiceDriveDialog;
    @BindView(R.id.btnChoferOrigen)
    Button btnChoferOrigen;
    @BindView(R.id.btnChoferDestino)
    Button btnChoferDestino;
    @BindView(R.id.btnVerMapaAccept)
    Button btnVerMapaAccept;
    private ProgressDialog loading;

    @BindView(R.id.textIdServicio)
    TextView textIdServicio;
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

    public ServicioActualFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicio_actual, container, false);
        ButterKnife.bind(this, view);
        preferenceActualService = new PreferenceActualService(getContext());
        preferencesManager = new PreferencesManager(getContext());

        requestIdSolicitud = new RequestIdSolicitud(preferenceActualService.getKeyServiceId());

        textIdServicio.setText(textIdServicio.getText().toString() + " " + preferenceActualService.getKeyServiceId());
        editNombrePasajero.setText(editNombrePasajero.getText().toString() + " " + preferenceActualService.getKeyNameUser());
        editTelefono.setText(editTelefono.getText().toString() + " " + preferenceActualService.getKeyTelephoneUser());
        editOrigen.setText(editOrigen.getText().toString() + " " + preferenceActualService.getKeyCoordOrigen());
        editDestino.setText(editDestino.getText().toString() + " " + preferenceActualService.getKeyCoordDestin());
        editFavorito.setText(preferenceActualService.getKeyIsFavorito());

        if (preferenceActualService.getKeyTypeService().equals("SolicitudDeServicioManual")) {
            btnVerMapaAccept.setVisibility(View.GONE);
        } else {
            btnVerMapaAccept.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void serviceCancel() {

        servicioRetrofit().cancelService(requestIdSolicitud).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                habilitarStatusChofer();
                loading.dismiss();
                if (response.code() == 200) {
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("message")));
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

    private void serviceChoferOrigen() {
        servicioRetrofit().driveInOrigin(requestIdSolicitud).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();
                Log.e("serviceChoferOrigen", response.message());
                if (response.code() == 200) {
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString());
                    btnChoferOrigen.setVisibility(View.GONE);
                    btnChoferDestino.setVisibility(View.VISIBLE);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("serviceChoferOrigenF", t.getCause().toString());
                Log.e("serviceChoferOrigenF", t.getMessage());
            }
        });
    }

    private void calificarServicio() {
        mCallback.onUpdateTitleToolbar("Califica al Pasajero");
        frag = new CalificarFragment();

        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();
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

    @OnClick({R.id.btnVerMapaAccept, R.id.btnCancelarSolicitud, R.id.btnChoferOrigen, R.id.btnChoferDestino})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerMapaAccept:
                mapServiceDriveDialog = new MapServiceDriveDialog();
                MapServiceDriveDialog.newInstance(preferenceActualService.getKeyCoordOrigen(),
                        preferenceActualService.getKeyCoordDestin());
                showDialogFragment(mapServiceDriveDialog);
                break;
            case R.id.btnCancelarSolicitud:
                loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
                serviceCancel();
                break;
            case R.id.btnChoferOrigen:
                loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
                serviceChoferOrigen();
                break;
            case R.id.btnChoferDestino:
                calificarServicio();
                break;
        }
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

}
