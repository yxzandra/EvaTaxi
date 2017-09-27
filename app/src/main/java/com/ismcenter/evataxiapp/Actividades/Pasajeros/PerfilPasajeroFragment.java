package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePasajeroProfileModel;
import com.ismcenter.evataxiapp.R;

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


public class PerfilPasajeroFragment extends Fragment {

    private static final String TAG = "PerfilPasajeroFragment";
    private static String idPersona,token;

    @BindView(R.id.tvNombre)
    TextView tvN;

    @BindView(R.id.tvTipo)
    TextView tvTipo;

    @BindView(R.id.tvCedula)
    TextView tvCedula;

    @BindView(R.id.tvCorreo)
    TextView tvCorreo;

    @BindView(R.id.tvTelefono)
    TextView tvTelefono;

    @BindView(R.id.tvDireccion)
    TextView tvDireccion;

    @BindView(R.id.tvCodigo)
    TextView tvNombre;

    @BindView(R.id.tvFechaNac)
    TextView tvFechaNac;

    @BindView(R.id.tvEstatus)
    TextView tvEstatus;

    @BindView(R.id.perfilPasajero)
    CoordinatorLayout recyclerContainer;

    public static Fragment newInstance(String idPersona, String token) {
        PerfilPasajeroFragment.idPersona = idPersona;
        PerfilPasajeroFragment.token = token;
        Log.i(TAG, "newInstance: " +idPersona + token);
        return new PerfilPasajeroFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_perfil_pasajero, container, false);
        ButterKnife.bind(this, view);
        service();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void service() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+token)
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
        servicio.getPasajeroProfile(Integer.parseInt(idPersona)).enqueue(new Callback<ResponsePasajeroProfileModel>() {
            @Override
            public void onResponse(Call<ResponsePasajeroProfileModel> call, Response<ResponsePasajeroProfileModel> response) {
                Log.i(TAG, "onResponse: " + response.body().getPersonCorreo());
                if (response.code() == 200){

                    String nombre = response.body().getPersonNombre();
                    String cedula = response.body().getPersonCedula();
                    String correo = response.body().getPersonCorreo();
                    String telefono = response.body().getPersonTelf();
                    String direccion = response.body().getPersonDireccion();
                    String fecha = response.body().getPersonFechaNacimiento();
                    int tipo = response.body().getPersonTipo();
                    int estatus = response.body().getPersonEstatus();

                    tvN.setText(nombre);
                    tvCedula.setText(cedula);
                    tvCorreo.setText(correo);
                    tvTelefono.setText(telefono);
                    tvDireccion.setText(direccion);
                    tvFechaNac.setText(String.valueOf(fecha));
                    tvTipo.setText(String.valueOf(tipo));

                    if(estatus==1) {
                        tvEstatus.setText(R.string.activo);
                    }
                    else
                        tvEstatus.setText(R.string.inactivo);

                    if(tipo==1) {
                        tvTipo.setText("Pasajero");
                    }
                    else
                        tvTipo.setText("Chofer");

                }
                else {
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
            public void onFailure(Call<ResponsePasajeroProfileModel> call, Throwable t) {

                Log.e("onFailure", t.getMessage());

            }
        });

    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }







  /*  public void service() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.getUsers(idPersona).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String nombre = response.body().getData().getPersonNombre();
                String cedula = response.body().getData().getPersonCedula();
                String correo = response.body().getData().getPersonCorreo();
                String telefono = response.body().getData().getPersonTelf();
                String direccion = response.body().getData().getPersonDireccion();
                String fecha = response.body().getData().getPersonFechaNacimiento();
                int estatus = response.body().getData().getPersonEstatus();
                String tipo = response.body().getData().getPersonTipo();

                tvN.setText(nombre);
                tvCedula.setText(cedula);
                tvCorreo.setText(correo);
                tvTelefono.setText(telefono);
                tvDireccion.setText(direccion);
                tvFechaNac.setText(fecha);


                if(estatus==1) {
                    tvEstatus.setText(R.string.activo);
                }
                else
                    tvEstatus.setText(R.string.inactivo);

                Log.i(TAG, "onResponse: " + tipo);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }


        });
    }*/


}
