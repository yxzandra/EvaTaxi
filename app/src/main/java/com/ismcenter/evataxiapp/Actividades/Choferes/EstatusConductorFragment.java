package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferenceActualService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstatusConductorFragment extends Fragment {

    @BindView(R.id.circleImagePerfil)
    CircleImageView circleImagePerfil;
    @BindView(R.id.textTituloNombre)
    TextView textTituloNombre;
    @BindView(R.id.textCambioTipoCarro)
    TextView textCambioTipoCarro;
    @BindView(R.id.textCambioPlaca)
    TextView textCambioPlaca;
    @BindView(R.id.switchEstado)
    Switch switchEstado;
    private ProgressDialog loading;
    private int primerStatus = 0;
    private static String idPersonaFragment,idTokenFragment,nombreConductorFragment,tipoCarroFragment,placaCarroFragment;

    public static Fragment newInstance(String nombreConductor, String tipoCarro, String placaCarro) {
        EstatusConductorFragment.nombreConductorFragment = nombreConductor;
        EstatusConductorFragment.tipoCarroFragment = tipoCarro;
        EstatusConductorFragment.placaCarroFragment = placaCarro;
        return new EstatusConductorFragment();
    }

    public EstatusConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estatus_conductor, container, false);
        ButterKnife.bind(this, view);
        PreferencesManager pm = new PreferencesManager(getContext());
        PreferenceActualService preferenceActualService = new PreferenceActualService(getContext());
        idTokenFragment = pm.getServerToken();
        Log.e("PersonID",pm.getPersonID());
        idPersonaFragment = pm.getPersonID();

        textTituloNombre.setText(nombreConductorFragment);
        textCambioTipoCarro.setText(tipoCarroFragment);
        textCambioPlaca.setText(placaCarroFragment);
        serviceGetStatus();

        //Si el chofer ha aceptado algun servicio no puede cambiar el estado a disponible hasta que lo
        //haya terminado

        if (preferenceActualService.getKeyServiceId().equals("null")){
            switchEstado.setEnabled(true);
        }else{
            switchEstado.setEnabled(false);
        }


        switchEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceChangeStatus(idPersonaFragment, 3);
                } else {
                    serviceChangeStatus(idPersonaFragment, 4);
                }
            }
        });
        return view;
    }
    private DriverInterface servicioRetrofit() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+idTokenFragment)
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

    private void serviceChangeStatus(String id_person, int status) {
        if (primerStatus == 2) {
            loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
            final RequestChangeStatus requestChangeStatus = new RequestChangeStatus(Integer.parseInt(id_person), status);
            servicioRetrofit().changeStatusDriver(requestChangeStatus).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    loading.dismiss();
                    if (response.code() == 200) {
                        respuestaServicio(response.body().getAsJsonObject().get("mensaje").getAsString());

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
        }else {
            primerStatus = 2;
        }

    }



    public void serviceGetStatus() {
        loading= ProgressDialog.show(getContext(),"Cargando","Por favor espere", false, false);
        servicioRetrofit().getDriverStatus(Integer.parseInt(idPersonaFragment)).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               loading.dismiss();
                if (response.code()==200){
                    recibirStatus(response.body().getAsJsonObject().get("status").getAsInt());

                }else {
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
                respuestaServicio("No hubo respuesta del servidor");
            }
        });
    }

    private void recibirStatus(int status) {
        if (status == 4){ //no disponible
            primerStatus = 1;
            switchEstado.setChecked(false);
        }if (status == 3){ //Disponible
            primerStatus = 2;
            switchEstado.setChecked(true);
        }
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
