package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Adaptador.DestinoAdapter;
import com.ismcenter.evataxiapp.Adaptador.OrigenAdapter;
import com.ismcenter.evataxiapp.Adaptador.ZonaAdapter;
import com.ismcenter.evataxiapp.Dialog.DetalleSolicitudManualDialog;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.DataAutocompletar;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudManual;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseAutocompletar;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDestinoByOrigen;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseOrigenByZona;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferenceActualService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
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

public class SolicitudManualFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "solicitudmanual";

    private DestinoAdapter adapterDestino;
    private ZonaAdapter adapterZone;
    private OrigenAdapter adapterOrigen;
    PreferencesManager preferences;
    FragmentTransaction fragTansaction;
    private PreferenceActualService preferenceActualService;

    private String token, idPersona, idUsuario, zona, origen, destino, punto, tarifa, tipo_tarifa;
    private EditText etZonas;
    private int idZona, tab_zonaid;
    private double latitude;
    private double longitud;
    @BindView(R.id.editTextZona)
    TextInputEditText editTextZona;

    @BindView(R.id.editTextOrigen)
    TextInputEditText editTextOrigen;

    @BindView(R.id.editTextDestino)
    TextInputEditText editTextDestino;

    @BindView(R.id.editTextPunto)
    TextInputEditText editTextPunto;

    @BindView(R.id.rv_autocompletar)
    RecyclerView rv_autocompletar;

    @BindView(R.id.rv_autocompletar2)
    RecyclerView rv_autocompletar2;

    @BindView(R.id.rv_autocompletar3)
    RecyclerView rv_autocompletar3;

    @BindView(R.id.btSolicitar)
    Button btSolicitar;

    public static SolicitudManualFragment newInstance() {
        return new SolicitudManualFragment();
    }

    public SolicitudManualFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {

        Log.i(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach: ");
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: ");
        //  showPlacePickerDialog("Solicitud procesada con éxito", "Esperando confirmación        del conductor",3);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(getActivity(), "28d7577c");
        View view = inflater.inflate(R.layout.fragment_solicitud_manual, container, false);
        ButterKnife.bind(this, view);
        preferences = new PreferencesManager(getContext());
        PreferencesManager pm = new PreferencesManager(getContext());
        token = pm.getServerToken();
        idPersona = pm.getPersonID();
        idUsuario = pm.getUserId();
        Log.i(TAG, "onCreate: " + token + idPersona);

        //Vistas
        initViews();

        // Deectamos cambio de texto en LA ZONA
        editTextZona.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged editTextZona: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextZona.getText().toString().equals("")) {
                    Log.i(TAG, "onTextChanged editTextZona 1: " + editTextZona.getText());
                    getZonas(editTextZona.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.i(TAG, "afterTextChanged editTextZona: " + editTextZona.getText().toString());
                if (editTextZona.getText().toString() != " ") {

                    Log.i(TAG, "onTextChanged: 2 " + editTextZona.getText());
                }
            }
        });

        // Deectamos cambio de texto EN EL ORIGEN
        editTextOrigen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged editTextOrigen: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextOrigen.getText().toString().equals("")) {
                    Log.i(TAG, "onTextChanged editTextZona 1: " + editTextOrigen.getText());
                    getOrigenByZona(editTextOrigen.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.i(TAG, "afterTextChanged editTextOrigen: " + editTextOrigen.getText().toString());
                if (editTextOrigen.getText().toString() != " ") {
                    Log.i(TAG, "onTextChanged: editTextOrigen " + editTextOrigen.getText());
                }
            }
        });

        // Deectamos cambio de texto EN EL DESTINO
        editTextDestino.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "beforeTextChanged editTextDestino: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!editTextDestino.getText().toString().equals("")) {
                    Log.i(TAG, "onTextChanged editTextZona 1: " + editTextDestino.getText());
                    getDestinoByOrigen(editTextDestino.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.i(TAG, "afterTextChanged editTextDestino: " + editTextDestino.getText().toString());
                if (editTextDestino.getText().toString() != " ") {
                    Log.i(TAG, "onTextChanged: editTextDestino " + editTextDestino.getText());

                }
            }
        });

        return view;
    }

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo, suTitulo, tipo, getContext(), new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }


    private void initViews() {
        //  recyclerView = (RecyclerView) findViewById(R.id.rv_autocompletar);
        rv_autocompletar.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_autocompletar.setLayoutManager(layoutManager);

        rv_autocompletar2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        rv_autocompletar2.setLayoutManager(layoutManager2);

        rv_autocompletar3.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        rv_autocompletar3.setLayoutManager(layoutManager3);

    }


    //Alert Dialogo
    public void alertDialogo(String message) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Generar Solicitud");
        dialogo1.setMessage(message);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(TAG, "enviar solicitud manual con favorito: ");
                alertDialogoFavoritos("¿Desea seleccionar un chofer para esta solicitud?");

            }
        });

        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                serviceManual();
            }
        });

        dialogo1.show();
    }

    //generar solicitud manual
    public void serviceManual() {
        RequestSolicitudManual solicitud = new RequestSolicitudManual(idUsuario, "3", "5", "6", null);

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
        Log.i("url", String.valueOf(servicio.setSolicitudManual(solicitud).request().url()));

        servicio.setSolicitudManual(solicitud).enqueue(new Callback<RequestSolicitudManual>() {
            @Override
            public void onResponse(Call<RequestSolicitudManual> call, Response<RequestSolicitudManual> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse 200 manual: ");
                    Toast.makeText(getContext(), "Solicitud generada", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RequestSolicitudManual> call, Throwable t) {
                Log.e("onFailure", t.getMessage());

            }
        });

    }

    /**
     * Servicio para obtener las zonas
     */
    public void getZonas(final String iniciales) {
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

        Log.i(TAG, "serviceZone: ");

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        Log.i("url", String.valueOf(servicio.getAutocompletar(iniciales).request().url()));

        servicio.getAutocompletar(iniciales).enqueue(new Callback<ResponseAutocompletar>() {
            @Override
            public void onResponse(Call<ResponseAutocompletar> call, Response<ResponseAutocompletar> response) {
                Log.i(TAG, "onResponse TEX: ");
                if (response.code() == 200) {

                    adapterZone = new ZonaAdapter(response.body().getData(), new ZonaAdapter.BrokerItemClick() {
                        @Override
                        public void onBrokerClick(DataAutocompletar clickedBroker) {
                            editTextOrigen.setVisibility(View.VISIBLE);
                            rv_autocompletar.setVisibility(View.INVISIBLE);
                            origen = editTextOrigen.getText().toString();

                            //Obtnemos idZona para pasarla al getOrigenByZona
                            idZona = clickedBroker.getZon_id();
                            Log.i(TAG, "onBrokerClick: " + idZona);
                            rv_autocompletar2.setVisibility(View.VISIBLE);
                            editTextOrigen.setVisibility(View.VISIBLE);
                            editTextZona.setVisibility(View.INVISIBLE);
                            editTextZona.setText(clickedBroker.getZon_nombre());
                        }
                    });

                    rv_autocompletar.setAdapter(adapterZone);

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
            public void onFailure(Call<ResponseAutocompletar> call, Throwable t) {
                Log.e("onFailure  zone", t.getMessage());

            }
        });

    }


    /////////////////

    //Obtner origen desde una zona
    public void getOrigenByZona(String inicial) {
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
        Log.i("url", String.valueOf(servicio.getOrigenByZona(Integer.toString(idZona), inicial).request().url()));

        // Utilizar idZona
        servicio.getOrigenByZona(Integer.toString(idZona), inicial).enqueue(new Callback<List<ResponseOrigenByZona>>() {
            @Override
            public void onResponse(Call<List<ResponseOrigenByZona>> call, Response<List<ResponseOrigenByZona>> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse 200 getOrigenByZona: ");
                    adapterOrigen = new OrigenAdapter(getContext(), response.body(), new OrigenAdapter.BrokerItemClick() {
                        @Override
                        public void onBrokerClick(ResponseOrigenByZona clickedBroker) {
                            Log.i(TAG, "onBrokerClick: ");

                            //Obtenemos el tab zona ID
                            tab_zonaid = clickedBroker.tab_zonaid;
                            //Ocultamos
                            editTextOrigen.setVisibility(View.INVISIBLE);
                            rv_autocompletar3.setVisibility(View.VISIBLE);
                            editTextDestino.setVisibility(View.VISIBLE);
                            rv_autocompletar.setVisibility(View.INVISIBLE);
                            rv_autocompletar2.setVisibility(View.INVISIBLE);
                            editTextZona.setVisibility(View.INVISIBLE);
                            editTextOrigen.setText(clickedBroker.getTab_origen());


                        }
                    });

                    rv_autocompletar2.setAdapter(adapterOrigen);

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
            public void onFailure(Call<List<ResponseOrigenByZona>> call, Throwable t) {

            }
        });

    }


    //Obtener destino by origen
    public void getDestinoByOrigen(String inicial) {


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
        Log.i(TAG, "getDestinoByOrigen: " + inicial + "/" + tab_zonaid);
        Log.i("url", String.valueOf(servicio.getDestinoByOrigen(inicial, Integer.toString(tab_zonaid)).request().url()));

        servicio.getDestinoByOrigen(inicial, Integer.toString(tab_zonaid)).enqueue(new Callback<List<ResponseDestinoByOrigen>>() {
            @Override
            public void onResponse(Call<List<ResponseDestinoByOrigen>> call, Response<List<ResponseDestinoByOrigen>> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse 200 getDestinoByOrigen: ");

                    adapterDestino = new DestinoAdapter(getContext(), response.body(), new DestinoAdapter.BrokerItemClick() {
                        @Override
                        public void onBrokerClick(ResponseDestinoByOrigen clickedBroker) {
                            Log.i(TAG, "onBrokerClick: ");

                            editTextPunto.setVisibility(View.VISIBLE);
                            btSolicitar.setVisibility(View.VISIBLE);
                            rv_autocompletar.setVisibility(View.INVISIBLE);
                            rv_autocompletar2.setVisibility(View.INVISIBLE);
                            rv_autocompletar3.setVisibility(View.INVISIBLE);
                            editTextDestino.setVisibility(View.INVISIBLE);
                            tarifa = clickedBroker.getTarifa().toString();
                            editTextDestino.setText(clickedBroker.zon_nombre);
                            zona = clickedBroker.getZon_nombre();
                            tipo_tarifa = clickedBroker.tipo_tarifa.toString();
                            origen = editTextOrigen.getText().toString();
                            destino = editTextDestino.getText().toString();
                        }
                    });

                    rv_autocompletar3.setAdapter(adapterDestino);
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
            public void onFailure(Call<List<ResponseDestinoByOrigen>> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.btSolicitar)
    public void solicitarServicio(View view) {
        punto = editTextPunto.getText().toString();
        DetalleSolicitudManualDialog mapServiceDriveDialog = new DetalleSolicitudManualDialog();
        DetalleSolicitudManualDialog.newInstance(tarifa, zona, tipo_tarifa, origen, destino, punto);
        showDialogFragment(mapServiceDriveDialog);

    }


    /**
     * Dialogopara seccionar un favorito
     *
     * @param message
     */
    public void alertDialogoFavoritos(String message) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Importante");
        dialogo1.setMessage(message);
        dialogo1.setCancelable(false);

        //ocultamos el teclado
        //  closeSoftKeyBoard();
        //eliminamos lo precargado
        //  etZonas.getText().clear();

        dialogo1.setPositiveButton("Solicitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //   getMyLocation();
                Log.i(TAG, "enviar solicitud manual con favorito: ");
                Intent intent = new Intent(getActivity(), FavoritosFragment.class);
                intent.putExtra("key", 2);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitud", longitud);
                startActivity(intent);
            }
        });

        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                serviceManual();
            }
        });

        dialogo1.show();
    }


    /**
     * Respuesta al servicio
     *
     * @param message
     */
    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    //Metodo para ocultar el teclado
    //  public void closeSoftKeyBoard() {
    //      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    //      imm.hideSoftInputFromWindow(etZonas.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    //  }



  /*  private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //   onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }


}
