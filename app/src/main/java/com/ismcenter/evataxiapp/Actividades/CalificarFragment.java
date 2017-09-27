package com.ismcenter.evataxiapp.Actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Actividades.Choferes.ServiciosDisponiblesFragment;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.PerfilPasajeroFragment;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.Modelos.Request.RequestRateDriver;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDriverProfileModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseListQualifiers;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePasajeroProfileModel;
import com.ismcenter.evataxiapp.Modelos.User;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Utils.PreferenceActualService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
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
public class CalificarFragment extends Fragment {
    PreferencesManager preferencesManager;
    PreferenceActualService preferenceActualService;
    UpdateTitleToolbar mCallback;
    Fragment frag;
    FragmentTransaction fragTansaction;
    private static ResponseListQualifiers clickDriverFragment;

    @BindView(R.id.tituloTipoUsuario)
    TextView tituloTipoUsuario;
    @BindView(R.id.imageUsuario)
    CircleImageView imageUsuario;
    @BindView(R.id.textNombreUsuario)
    TextView textNombreUsuario;
    @BindView(R.id.textCarro)
    TextView textCarro;
    @BindView(R.id.texPlaca)
    TextView texPlaca;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.editComentarios)
    EditText editComentarios;
    @BindView(R.id.calificarContainer)
    CoordinatorLayout calificarContainer;
    private ProgressDialog loading;

    public CalificarFragment() {
    }

    public static Fragment newInstance(ResponseListQualifiers clickDriver) {
        CalificarFragment.clickDriverFragment = clickDriver;

        return new CalificarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calificar, container, false);
        ButterKnife.bind(this, view);
        preferencesManager = new PreferencesManager(getContext());
        preferenceActualService = new PreferenceActualService(getContext());

        Log.e("tipo Usuario", preferencesManager.getUserType());

        if (preferencesManager.getUserType().equals("2")){
            //obtenerConductor();
            textNombreUsuario.setText(preferenceActualService.getKeyNameUser());
            tituloTipoUsuario.setText("Califica a tu pasajero");
            textCarro.setText(preferenceActualService.getKeyServiceId());
            texPlaca.setText("");
        }else {
            textNombreUsuario.setText(clickDriverFragment.getPerson_nombre());
            tituloTipoUsuario.setText("Califica a tu chofer");
            textCarro.setText(clickDriverFragment.getPerson_correo());
            texPlaca.setText("Numero del servicio:"+" "+clickDriverFragment.getSer_id());
            preferenceActualService.setKeyServiceId(clickDriverFragment.getSer_id());
        }


        return view;
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


    @OnClick(R.id.button)
    public void onClick() {
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
        calificarServicio();
    }

    private void calificarServicio() {

        RequestRateDriver requestRateDriver = new RequestRateDriver(editComentarios.getText().toString(),
                String.valueOf(Math.round(ratingBar.getRating())) ,preferenceActualService.getKeyServiceId());
        Log.e("calificar servicio", requestRateDriver.toString());
        servicioRetrofit().rateDriver(requestRateDriver).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                loading.dismiss();

                if (response.code() == 200){
                    Log.e("response",response.body().toString());
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString());
                    preferenceActualService.clearPreferences();
                    if (preferencesManager.getUserType().equals("2")){
                        cambiarStatusConductor();
                    }else {
                        llamarFragmento();
                    }

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                        Log.e("respuestaServicio", jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void cambiarStatusConductor() {
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);
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

    public void llamarFragmento() {
        if (preferencesManager.getUserType().equals("2")) {
            mCallback.onUpdateTitleToolbar("Servicios");
            frag = new ServiciosDisponiblesFragment();
        }else {
            mCallback.onUpdateTitleToolbar("Servicios");
            frag = new ListarCalificarFragment();
        }

        fragTansaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();
    }


    private void respuestaServicio(String message) {
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


}
