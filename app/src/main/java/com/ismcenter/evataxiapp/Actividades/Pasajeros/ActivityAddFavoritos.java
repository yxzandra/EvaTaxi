package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.ChoferAdapter;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.DataAddFavoritos;
import com.ismcenter.evataxiapp.Modelos.DataChoferCandidato;
import com.ismcenter.evataxiapp.Modelos.FavoritosAddModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityAddFavoritos extends AppCompatActivity {

    private static final String TAG = "activityAddFavoritos";
    private RecyclerView mRecycler;
    private ImageView imfavorito;
    private ChoferAdapter brokersAdapter;
    private int  id_chofer, key;
    private String id_usuario;
    private ChoferAdapter adapter;
    private RecyclerView recyclerView;
    private String token;
    PreferencesManager preferences;

    @BindView(R.id.tv_choferes)
    TextView tv_fav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(ActivityAddFavoritos.this, "28d7577c");
        setContentView(R.layout.activity_add_favoritos);
        setToolbar();

        preferences = new PreferencesManager(getApplicationContext());
        PreferencesManager pm = new PreferencesManager(this);

        token = pm.getServerToken();
        id_usuario = pm.getUserId();

        Log.i(TAG, "onCreate: " + token +id_usuario + key);
        initViews();
    }

    /**
     * Inicializar las vistas
     */
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.brokers_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        service();

    }

    /**
     * Conexion con la API para obtner los choferes
     */
    public void service() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+ token)
                        .build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))// url de la petición
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.getChoferesCandidatos(id_usuario).enqueue(new Callback<List<DataChoferCandidato>>() {
            @Override
            public void onResponse(Call<List<DataChoferCandidato>> call, Response<List<DataChoferCandidato>> response) {
                if (response.code() == 200) {

                     //   tv_fav.setVisibility(View.INVISIBLE);

                        Log.i(TAG, "onResponse 200: ");
                        adapter = new ChoferAdapter(response.body(), new ChoferAdapter.BrokerItemClick() {
                            @Override
                            public void onBrokerClick(DataChoferCandidato clickedBroker) {

                                    id_chofer = clickedBroker.getPersonId();//obtenemos el id del chofer
                                    agregarFavoritos(Integer.parseInt(id_usuario), id_chofer);
                                    Log.i(TAG, "onBrokerClick: " + "usu" + id_usuario + "cho" + id_chofer);

                            }
                        });
                        recyclerView.setAdapter(adapter);



                }
            }

            @Override
            public void onFailure(Call<List<DataChoferCandidato>> call, Throwable t) {

                Log.i(TAG, "onFailure: ");

            }
        });


    }

    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    /**
     * Agrega un chofer seleccionado como favorito
     * @param idpersonaprincipal
     * @param idpersonasecundaria
     */
    protected void agregarFavoritos(int idpersonaprincipal, int idpersonasecundaria){

        DataAddFavoritos favoritos = new DataAddFavoritos(idpersonaprincipal,idpersonasecundaria);
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+ token)
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

        servicio.setFavoritos(favoritos).enqueue(new Callback<FavoritosAddModel>() {
            @Override
            public void onResponse(Call<FavoritosAddModel> call, Response<FavoritosAddModel> response) {

                Log.i(TAG, "onResponse Code: " + response.code());
                if (response.code() == 200){
                    Log.i(TAG, "onResponse: ");
                    Toast.makeText(getApplicationContext(), "Agregado como favorito", Toast.LENGTH_LONG).show();
                    service();
                    recyclerView.setAdapter(adapter);

                }
                else
                {
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
            public void onFailure(Call<FavoritosAddModel> call, Throwable t) {

                Log.i(TAG, "onFailure: ");
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecarga);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add);
        toolbar.setOverflowIcon(drawable);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
