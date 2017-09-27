package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.FavoritosAdapter;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.FavoritosQuitModel;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudAutomatica;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudManual;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseFavoritosModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

public class FavoritosFragment extends AppCompatActivity {
    private int key;

    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/"; // esto no va aca cambiar
    private static final String TAG = "ActivityFavoritos";
    private FavoritosAdapter adapter;
    private RecyclerView recyclerView;
    private String latitude;
    private String longitud;
    private String idUsuario ;
    private String idFavorito, destino, origen, token, idPersona;
    PreferencesManager preferences;


    @BindView(R.id.tv_fav)
    TextView tv_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(FavoritosFragment.this, "28d7577c");
        setContentView(R.layout.fragment_favoritos);
        ButterKnife.bind(this);
        setToolbar();

        preferences = new PreferencesManager(getApplicationContext());
        PreferencesManager pm = new PreferencesManager(this);
        idUsuario = pm.getUserId();
        idPersona = pm.getPersonID();
        token = pm.getServerToken();

        key = getIntent().getExtras().getInt("key");
        origen = getIntent().getExtras().getString("origen");
        destino = getIntent().getExtras().getString("destino");
        latitude = getIntent().getExtras().getString("latitude");
        longitud = getIntent().getExtras().getString("longitud");

        Log.i(TAG, "onCreate: " + token +"key" + key + idUsuario);
        initViews();

    }

    //Inicializamos la vista
    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.choferes_list_favoritos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        service();
    }

    //se solicita el servicio
    public void service() {
        Log.i(TAG, "service: ");
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+token)
                        .build();
                return chain.proceed(request);
            }
        };

        Log.i(TAG, "service: " + token);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.getFavoritos(Integer.parseInt(idUsuario)).enqueue(new Callback<List<ResponseFavoritosModel>>() {
            @Override
            public void onResponse(Call<List<ResponseFavoritosModel>> call, Response<List<ResponseFavoritosModel>> response) {
                Log.i(TAG, "onResponse: ");
                if (response.code() == 200){
                    Log.i(TAG, "onResponse2: ");
                    if(response.body().size()==0) {
                        tv_fav.setVisibility(View.VISIBLE);
                        adapter = new FavoritosAdapter(getApplicationContext(), response.body(), new FavoritosAdapter.BrokerItemClick() {
                            @Override
                            public void onBrokerClick(ResponseFavoritosModel clickedBroker) {
                                Log.i(TAG, "onBrokerClick: ");
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        tv_fav.setVisibility(View.INVISIBLE);
                        adapter = new FavoritosAdapter(getApplicationContext(), response.body(), new FavoritosAdapter.BrokerItemClick() {
                            @Override
                            public void onBrokerClick(ResponseFavoritosModel clickedBroker) {
                                idFavorito = clickedBroker.getPersonId().toString();
                                Log.i(TAG, "onBrokerClick: ");
                                switch (key){
                                    case 0:
                                        alertDialogo("¿Está seguro de quitar este chofer de su lista de favoritos?",0);
                                        break;
                                    case 1:
                                        alertDialogo("¿Seguro desea seleccionar este chofer?", 1);
                                        break;
                                    case 2:
                                        alertDialogo("¿Seguro desea seleccionar este chofer?", 2);
                                        break;
                                }

                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }

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
            public void onFailure(Call<List<ResponseFavoritosModel>> call, Throwable t) {
                Log.i(TAG, "onFailure: ");

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        service();
    }


    //Alert Dialogo
    public void alertDialogo(String message, final int tipo){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage(message);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (tipo){
                    case 0:
                        aceptar();
                        break;
                    case 1:
                        Log.i(TAG, "enviar solicitud automatica con favorito: ");
                        solicitud(idFavorito);
                        break;
                    case 2:
                        Log.i(TAG, "enviar solicitud manual con favorito: ");
                        solicitudManual(idFavorito);
                        break;
                }

            }
        });

        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                cancelar();
            }
        });

        dialogo1.show();
    }


    /**
     * Generar la solicitud manual con favoritos
     * @param idFavorito
     */

    private void solicitudManual(String idFavorito) {

        RequestSolicitudManual solicitud = new RequestSolicitudManual(idUsuario,"5", "5","8",idFavorito );
        Log.i(TAG, "parmetrosolicitud con favorito: " + latitude+longitud+"--"+idUsuario+"-"+idFavorito);

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

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.setSolicitudManual(solicitud).enqueue(new Callback<RequestSolicitudManual>() {
            @Override
            public void onResponse(Call<RequestSolicitudManual> call, Response<RequestSolicitudManual> response) {
                if (response.code() == 200){
                    Log.i(TAG, "onResponse 200 favoritos manual: ");
                    Toast.makeText(getApplicationContext(), "Solicitud generada", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RequestSolicitudManual> call, Throwable t) {

            }
        });
    }

    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    /**
     * Quitar un chofer como favorito
     */
    private void aceptar() {

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

        PasajeroInterface service = retrofit.create(PasajeroInterface.class);
        Log.i("url", String.valueOf(service.quitarFavoritos(idUsuario,idFavorito).request().url()));
        service.quitarFavoritos(idUsuario,idFavorito).enqueue(new Callback<FavoritosQuitModel>() {
            @Override
            public void onResponse(Call<FavoritosQuitModel> call, Response<FavoritosQuitModel> response) {
                Log.i(TAG, "onResponse elim: " + response.code());
                if (response.code() == 200){
                    service();
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onResponse2 elim: ");
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
            public void onFailure(Call<FavoritosQuitModel> call, Throwable t) {
                Log.i(TAG, "onFailure elim: ");

            }
        });

    }

    private void cancelar() {
    }

    //Obtenemos fecha actual
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    //Clase para el servicio
    protected void solicitud(String favorito) {

        RequestSolicitudAutomatica solicitud = new RequestSolicitudAutomatica(latitude,longitud, idUsuario,favorito,null );
        Log.i(TAG, "parmetrosolicitud con favorito: " + latitude+longitud+"--"+idUsuario+"-"+favorito);

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
        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.setSolicitud(solicitud).enqueue(new Callback<RequestSolicitudAutomatica>() {
            @Override
            public void onResponse(Call<RequestSolicitudAutomatica> call, Response<RequestSolicitudAutomatica> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: ");
                    Toast.makeText(getApplicationContext(), "Solicitud generada", Toast.LENGTH_SHORT).show();
                    showPlacePickerDialog("Solicitud procesada con éxito", "Esperando confirmación del conductor",3);

                } else {
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
            public void onFailure(Call<RequestSolicitudAutomatica> call, Throwable t) {

                Log.e("onFailure", t.getMessage());

            }
        });
    }

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo,suTitulo,tipo,this, new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add);
       // toolbar.setOverflowIcon(drawable);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addfavoritos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_addfavoritos:
                    Intent i = new Intent(this, ActivityAddFavoritos.class);
                    startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
