package com.ismcenter.evataxiapp.Dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.AlertDialogSolicitud;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudManual;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

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


public class DetalleRecargaSaldo extends DialogFragment {

    private static final String TAG = "FragmentDetalle";
    private SupportMapFragment mapFragment;
    private static View view;
    static String nombre, fecha, cedula,comprobante,monto;
    PreferencesManager preferences;
    private String token, idPersona,idUsuario;

    @BindView(R.id.tv_comprobante)
    TextView tv_comprobante;

    @BindView(R.id.tv_fecha)
    TextView tv_fecha;

    @BindView(R.id.tv_nombre)
    TextView tv_nombre;

    @BindView(R.id.tv_cedula)
    TextView tv_cedula;

    @BindView(R.id.tv_monto)
    TextView tv_monto;

    @BindView(R.id.btSolicitar)
    Button btSolicitar;

    public DetalleRecargaSaldo() {
        mapFragment = new SupportMapFragment();
    }

    public static DetalleRecargaSaldo newInstance(String comprobante, String fecha, String nombre, String cedula, String monto) {

        DetalleRecargaSaldo f = new DetalleRecargaSaldo();
        comprobante = comprobante;
        fecha = fecha;
        nombre = nombre;
        cedula = cedula;
        monto = monto;


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.dialog_detalle_recarga_saldo, container, false);
            //Fragment para el mapa

            getDialog().setTitle("Detalle de Recarga");

        } catch (InflateException e) {
            //Fragment para el mapa

            getDialog().setTitle("Detalle de Recarga");
        }

        ButterKnife.bind(this, view);
        preferences = new PreferencesManager(getContext());
        PreferencesManager pm = new PreferencesManager(getContext());
        token = pm.getServerToken();
        idPersona = pm.getPersonID();
        idUsuario = pm.getUserId();
        Log.i(TAG, "onCreate: " +token + idPersona + idUsuario);
        tv_comprobante.setText("Comprobante nro: " + comprobante);
        tv_fecha.setText("Fecha: "+ fecha);
        tv_nombre.setText("Nombre: "+ nombre);
        tv_cedula.setText("Cédula: "+ cedula);
        tv_monto.setText("Monto: "+ monto + " BsF");



        return view;

    }

    @OnClick(R.id.btSolicitar)
    public void solicitarServicio(View view) {

        dismiss();
    }
    //generar solicitud manual
    public void serviceManual() {
        RequestSolicitudManual solicitud = new RequestSolicitudManual(idUsuario,"3","5","6", null);
        Toast.makeText(getContext(), idUsuario, Toast.LENGTH_LONG).show();

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
        Log.i("url", String.valueOf(servicio.setSolicitudManual(solicitud).request().url()));

        servicio.setSolicitudManual(solicitud).enqueue(new Callback<RequestSolicitudManual>() {
            @Override
            public void onResponse(Call<RequestSolicitudManual> call, Response<RequestSolicitudManual> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse 200 manual: " );
                    dismiss();
                    showPlacePickerDialog("Solicitud procesada con éxito", "Esperando confirmación del conductor",3);
                  //  Toast.makeText(getContext(), "Solicitud generada", Toast.LENGTH_SHORT).show();
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
                Log.e("onFailure", t.getMessage());

            }
        });
    }

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo,suTitulo,tipo,getContext(), new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }

    /**
     * Respuesta al servicio
     * @param message
     */
    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }
}