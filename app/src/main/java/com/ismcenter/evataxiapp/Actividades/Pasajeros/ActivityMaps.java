package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Coordenada;
import com.ismcenter.evataxiapp.Modelos.Request.RequestCancelarSolicitud;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudAutomatica;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseTarifa;
import com.ismcenter.evataxiapp.Modules.DirectionFinder;
import com.ismcenter.evataxiapp.Modules.DirectionFinderListener;
import com.ismcenter.evataxiapp.Modules.Route;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener{
    private static final String TAG = "ActivityMaps";
    private GoogleMap mapa;
    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/"; // esto no va aca cambiar
    private String destino, origen, estado;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int contador = 0;
    private String longitud;
    private String latitude;
    private String distancia;
    private Double latOrig, longOrig, latDest, longDest;
    Coordenada coodOrigen ;
    Coordenada coodDestino;

    private String idPersona;
    private String idUsuario;
    private String token;
    private String idSolicitud;
    PreferencesManager preferences;
    public Location location;

    @BindView(R.id.btSolicitar)
    Button btSolicitar;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.tvTarifa)
    TextView tvTarifa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(ActivityMaps.this, "28d7577c");
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        preferences = new PreferencesManager(getApplicationContext());
        PreferencesManager pm = new PreferencesManager(this);
        idPersona = pm.getPersonID();
        idUsuario = pm.getUserId();
        token = pm.getServerToken();
        origen = getIntent().getExtras().getString("origen");
        destino = getIntent().getExtras().getString("destino");
        estado = getIntent().getExtras().getString("estado");

        Log.i(TAG, "onCreate: " + idUsuario + "--" + idPersona + "--" + token);

        //Fragment para el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sendRequest();

    }

    //Enviamos origen y ubicacion
    private void sendRequest() {

        try {
            new DirectionFinder(this, origen.concat(" ").concat(estado), destino.concat(" ").concat(estado)).execute();
            Log.i(TAG, "sendRequest: " + origen.concat(estado).concat("Venezuela") + destino.concat(estado).concat("Venezuela"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        LatLng hcmus = new LatLng(8.375261, -62.609362);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mapa.addMarker(new MarkerOptions()
                .title("Mi posición actual")
                .position(hcmus)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setMyLocationButtonEnabled(true);

        //Mi posicion actual

        if (contador == 0) {
            mapa.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location location) {
                    contador = 2;
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    //  mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 18));

                   // Log.i(TAG, "onMyLocationChange: " + location.getLatitude() + "-" + location.getLongitude());

                    latitude = Double.toString(location.getLatitude());
                    longitud = Double.toString(location.getLongitude());
                     //  Toast.makeText(getApplicationContext(), Double.toString(location.getLatitude())+ "-" +Double.toString(location.getLongitude()), Toast.LENGTH_LONG ).show();

                    //  mapa.addMarker(new MarkerOptions()
                    //        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    //      .title("Aquí estoy!"));
                }
            });
        }
    }

    @OnClick(R.id.btSolicitar)
    public void solicitarServicio(View view) {
        if (btSolicitar.getText() == "Cancelar Solicitud") {
            Log.i(TAG, "solicitarServicio: " + btSolicitar.getText());
            PreferencesManager pm = new PreferencesManager(this);
            idSolicitud = pm.getSolicitudId();
            Log.i(TAG, "solicitarServicio sol: " + idSolicitud);
            alertDialogoCnacelar("¿Está seguro de cancelar esta solicitud?");

        } else
            alertDialogo("¿Desea seleccionar un chofer para esta solicitud?");
    }

    /**
     * etodo para cancelar el servicio
     */
    private void cancelarSolicitudService(String idSolicitud) {
        RequestCancelarSolicitud solicitud = new RequestCancelarSolicitud(idSolicitud);

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
        servicio.setCancelarSolicitud(solicitud).enqueue(new Callback<RequestCancelarSolicitud>() {
            @Override
            public void onResponse(Call<RequestCancelarSolicitud> call, Response<RequestCancelarSolicitud> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse cancelar: ");
                    btSolicitar.setText("Solicitud Cancelada");
                    Toast.makeText(getApplicationContext(), "Solicitud cancelada", Toast.LENGTH_LONG).show();
                    onBackPressed();
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
            public void onFailure(Call<RequestCancelarSolicitud> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Por favor, espere",
                "Buscando dirección...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }
        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }
        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) throws IOException {

        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        Log.i(TAG, "destinemarkes: " + destinationMarkers);

        for (Route route : routes) {
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 13));

            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);



            latOrig = route.startLocation.latitude;
            longOrig = route.startLocation.longitude;

            latDest =  route.endLocation.latitude;
            longDest =  route.endLocation.longitude;

            coodOrigen = new Coordenada(latOrig,longOrig);
            coodDestino= new Coordenada(latDest,longDest);

            Log.i(TAG, "onDirectionFinderSuccess: " +"MiLat:"+ route.startLocation + "Mi Long:" + route.endLocation + "mis"+ coodOrigen+ "mis"+ coodDestino);

            distancia = route.distance.text;
            getTarifa(distancia);
            originMarkers.add(mapa.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder_origen))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mapa.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            //Marcar recorrido
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(8);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mapa.addPolyline(polylineOptions));
        }
    }

    public void getMyLocation() throws IOException {

    }

    //Obtenemos fecha actual
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    //Clase para el servicio
    protected void solicitud() {

        RequestSolicitudAutomatica solicitud = new RequestSolicitudAutomatica(coodOrigen.toString(),coodDestino.toString(), idUsuario, null, null);
        Log.i(TAG, "solicitud: " + idPersona);

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
                    Log.i(TAG, "onResponse: " + response.body().getId_solicitud().toString());
                    preferences = new PreferencesManager(getApplication());
                    preferences.setSolicitudId(response.body().getId_solicitud().toString());
                    btSolicitar.setText("Cancelar Solicitud");

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

    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    //Alert dialogo
    public void alertDialogo(String message) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage(message);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(ActivityMaps.this, FavoritosFragment.class);
                intent.putExtra("key", 1);
                intent.putExtra("token", token);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitud", location);

                startActivity(intent);

            }
        });

        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showPlacePickerDialog("Solicitud procesada con éxito", "Esperando confirmación del conductor", 3);
                solicitud();

            }
        });

        dialogo1.show();
    }

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo, suTitulo, tipo, this, new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }


    public void alertDialogoCnacelar(String message) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage(message);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btSolicitar.setEnabled(false);
                cancelarSolicitudService(idSolicitud);
            }
        });

        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                solicitud();


            }
        });

        dialogo1.show();
    }

    //Obtnemos la tarifa
    public void getTarifa(String distance) {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
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

        PasajeroInterface service = retrofit.create(PasajeroInterface.class);
        Log.i("url", String.valueOf(service.getTarifa(distance, "12", "1").request().url()));

        service.getTarifa(distance, "12:00:00", "7").enqueue(new Callback<ResponseTarifa>() {
            @Override
            public void onResponse(Call<ResponseTarifa> call, Response<ResponseTarifa> response) {

                Log.i(TAG, "onResponse tarifa: ");
                if (response.code() == 200) {
                    tvTarifa.setText(response.body().getIncrementos().toString() + " BsF");
                    Log.i(TAG, "onResponse tarifa: " + response.body().getTarifa() + "-" + response.body().getIncrementos());

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
            public void onFailure(Call<ResponseTarifa> call, Throwable t) {

            }
        });

    }


}
