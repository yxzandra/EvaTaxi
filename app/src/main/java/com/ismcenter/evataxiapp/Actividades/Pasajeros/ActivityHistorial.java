package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.HistorialSolicitudesAdapter;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryService;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityHistorial extends AppCompatActivity {

    private static final String TAG = "Historial";
    private HistorialSolicitudesAdapter adapter;
    private RecyclerView recyclerView;
    PreferencesManager preferences;
    private String id_usuario,token;
    private TextView info;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(ActivityHistorial.this, "28d7577c");
        setContentView(R.layout.activity_historial);

        preferences = new PreferencesManager(getApplicationContext());
        PreferencesManager pm = new PreferencesManager(this);
        token = pm.getServerToken();
        id_usuario = pm.getUserId();
        Log.i(TAG, "onCreate: " + id_usuario);

        setToolbar();
        initViews();
    }

    /**
     * Inicializar las vistas
     */
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_historialServicio);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        info = (TextView) findViewById(R.id.tv_historial);
        service();

    }

    /**
     * Conexion con la API para historial de lassolicitudes hechas por un isuario
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
        servicio.getHistorialSolicitudes(id_usuario).enqueue(new Callback<List<ResponseHistoryService>>() {
            @Override
            public void onResponse(Call<List<ResponseHistoryService>> call, Response<List<ResponseHistoryService>> response) {

                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: ");
                    if(response.body().size()>=0) {
                        info.setVisibility(View.INVISIBLE);
                        adapter = new HistorialSolicitudesAdapter(response.body(), new HistorialSolicitudesAdapter.BrokerItemClick() {
                            @Override
                            public void onBrokerClick(ResponseHistoryService clickedBroker) {
                                Log.i(TAG, "onBrokerClick: ");

                            }

                        });
                        recyclerView.setAdapter(adapter);
                    }
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
            public void onFailure(Call<List<ResponseHistoryService>> call, Throwable t) {

            }
        });
    }

    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }




    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
