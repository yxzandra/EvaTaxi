package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDriverProfileModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

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

/**
 * Created by Desarrollo on 23/11/2016.
 */
public class PerfilConductorFragment extends Fragment {
    private ProgressDialog loading;
    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/";
    private static String idPersona,token;
    @BindView(R.id.imagePerfilConductor)
    CircleImageView imagePerfilConductor;
    @BindView(R.id.textTituloNombre)
    TextView textTituloNombre;
    @BindView(R.id.editNombre)
    EditText editNombre;
    @BindView(R.id.editCorreo)
    EditText editCorreo;
    @BindView(R.id.editTelefono)
    EditText editTelefono;
    @BindView(R.id.editDireccion)
    EditText editDireccion;
    @BindView(R.id.editModeloCarro)
    EditText editModeloCarro;
    @BindView(R.id.editPlacaCarro)
    EditText editPlacaCarro;
    @BindView(R.id.editColorCarro)
    EditText editColorCarro;

    public PerfilConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_conductor, container, false);
        ButterKnife.bind(this, view);

        PreferencesManager pm = new PreferencesManager(getContext());
        token = pm.getServerToken();
        idPersona = pm.getPersonID();

        loading= ProgressDialog.show(getContext(),"Cargando","Por favor espere", false, false);
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
        //declaromos la interfaz
        //Integer.parseInt(idUsuario)

        DriverInterface servicio = retrofit.create(DriverInterface.class);
        servicio.driverProfile(Integer.parseInt(idPersona)).enqueue(new Callback<ResponseDriverProfileModel>() {
            @Override
            public void onResponse(Call<ResponseDriverProfileModel> call, Response<ResponseDriverProfileModel> response) {
                loading.dismiss();
                if (response.code() == 200){
                    editCorreo.setText(response.body().getPerson_correo());
                    editDireccion.setText(response.body().getPerson_direccion());
                    textTituloNombre.setText(response.body().getPerson_nombre());
                    editNombre.setText(response.body().getPerson_nombre());
                    editModeloCarro.setText(response.body().getCar_modelo());
                    editPlacaCarro.setText(response.body().getCar_placa());
                    editColorCarro.setText(response.body().getCar_color());
                    editTelefono.setText(response.body().getPerson_telf());

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
            public void onFailure(Call<ResponseDriverProfileModel> call, Throwable t) {
                loading.dismiss();
                respuestaServicio("No hubo respuesta del servidor");

            }
        });
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }
}
