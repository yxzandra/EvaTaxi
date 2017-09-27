package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.DataDeposito;
import com.ismcenter.evataxiapp.Modelos.Request.RequestDeposito;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseBancos;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.splunk.mint.Mint;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class DepositoSaldoFragment extends Fragment implements Validator.ValidationListener{

    private static final String TAG = "Recargadesaldo" ;
    Spinner spinner;
    private int id_banco;
    private String idPersona, token,select;
    PreferencesManager preferences;


    @NotEmpty(message = "Debe ingresar un número de operación")
    @BindView(R.id.et_nro_operacion)
    EditText et_nro_operacion;

    @NotEmpty(message = "Debe ingresar un nombre")
    @BindView(R.id.et_nombre)
    EditText et_nombre;


    @NotEmpty(message = "Debe ingresar su CI o Rif")
    @BindView(R.id.et_cedula)
    EditText et_cedula;

    @NotEmpty(message = "Debe ingresar una fecha")
    @BindView(R.id.et_fecha)
    EditText et_fecha;

    @NotEmpty(message = "Debe ingresar un monto")
    @BindView(R.id.et_monto)
    EditText et_monto;

    @BindView(R.id.btValidar)
    Button btValidar;

    @BindView(R.id.sp_banco)
    Spinner sp_banco;

    Validator validator;
    private ProgressDialog loading;
    Calendar myCalendar = Calendar.getInstance();
    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/";

    public static Fragment newInstance(String idPersona, String token) {
        Log.i(TAG, "newInstance: " +idPersona + token);
        return new DepositoSaldoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(getActivity(), "28d7577c");

        View view = inflater.inflate(R.layout.fragment_deposito, container, false);
        ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        PreferencesManager pm = new PreferencesManager(getActivity());
        idPersona = pm.getPersonID();
        token = pm.getServerToken();

        service();
        return view;
    }


    @OnClick(R.id.btValidar)
    public void solicitar(View view) {

            validator.validate();
            setDeposito(et_nro_operacion.getText().toString(), et_fecha.getText().toString(),Integer.toString(id_banco),et_monto.getText().toString(), idPersona);
           // finish();

    }

    protected void setDeposito(String trans_comprobante, String trans_fecha, String trans_banco, String trans_monto, String trans_personid)
    {

        RequestDeposito dataDeposito = new RequestDeposito(trans_comprobante, trans_fecha,trans_banco, trans_monto, trans_personid);

        DataDeposito dataD = new DataDeposito("",dataDeposito,null);
        
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
        servicio.setDeposito(dataDeposito).enqueue(new Callback<RequestDeposito>() {
            @Override
            public void onResponse(Call<RequestDeposito> call, Response<RequestDeposito> response) {
             //   Log.i(TAG, "onResponse: " + response.body().getPago().getPagos_fecha().toString());


             //   DetalleRecargaSaldo mapServiceDriveDialog = new DetalleRecargaSaldo();
             //   DetalleRecargaSaldo.newInstance(et_nro_operacion.getText().toString(),et_fecha.getText().toString(),"Juleimis","Abache",et_monto.getText().toString());
             //   showDialogFragment(mapServiceDriveDialog);
                showPlacePickerDialog("Solicitud procesada con éxito", "Deposito Bancario ", 3);
                Toast.makeText(getActivity(), "Pago exitoso" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<RequestDeposito> call, Throwable t) {

            }
        });
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }



    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo, suTitulo, tipo, getActivity(), new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }


    //sobtenemos bancos
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


        servicio.getBancos().enqueue(new Callback<List<ResponseBancos>>() {
            @Override
            public void onResponse(Call<List<ResponseBancos>> call, Response<List<ResponseBancos>> response) {

                if(response.isSuccessful()) {
                    final ArrayAdapter<String> adapter;
                    List<String> data;
                    data = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++)

                        data.add(response.body().get(i).ban_nombre.toString());
                    adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp_banco.setAdapter(adapter);
                    sp_banco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            select = adapterView.getItemAtPosition(i).toString();
                            ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                            id_banco = i+1;
                            Log.i(TAG, "onItemSelected: " + i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                   // Log.i(TAG, "onResponse: " + response.body().getData().get(0).getBanNombre().toString());
                }
                else
                    Log.i(TAG, "onResponse: ");

            }

            @Override
            public void onFailure(Call<List<ResponseBancos>> call, Throwable t) {

                Log.i(TAG, "onFailure: ");

            }
        });


    }



    @Override
    public void onValidationSucceeded() {

        String dtStart = et_fecha.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

        }

    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };



    @OnClick({R.id.et_fecha})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_fecha:
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_fecha.setText(sdf.format(myCalendar.getTime()));
    }



}
