package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Request.RequestTransferencia;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class TransferenciaFragment extends Fragment implements Validator.ValidationListener  {

    private static final String TAG = "FragTrans" ;
    Validator validator;
    private String idPersona, token;
    Calendar myCalendar = Calendar.getInstance();


    @NotEmpty(message = "Debe ingresar un número de operación")
    @BindView(R.id.nro_op)
    EditText nro_op;


    @NotEmpty(message = "Debe ingresar un banco origen")
    @BindView(R.id.banco_org)
    EditText banco_org;


    @NotEmpty(message = "Debe ingresar un banco destino")
    @BindView(R.id.banco_des)
    EditText banco_des;

    @NotEmpty(message = "Debe ingresar un nombre")
    @BindView(R.id.nombre)
    EditText nombre;


    @NotEmpty(message = "Debe ingresar su CI o Rif")
    @BindView(R.id.cedula)
    EditText cedula;

    @NotEmpty(message = "Debe ingresar una fecha")
    @BindView(R.id.et_fecha)
    EditText et_fecha;

    @NotEmpty(message = "Debe ingresar un monto")
    @BindView(R.id.monto)
    EditText monto;

    @BindView(R.id.btValidar)
    Button btValidar;



    public static Fragment newInstance(String idPersona, String token) {
        return new TransferenciaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(getActivity(), "28d7577c");

        View view = inflater.inflate(R.layout.fragment_transferencia, container, false);
        ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        PreferencesManager pm = new PreferencesManager(getActivity());
        idPersona = pm.getPersonID();
        token = pm.getServerToken();

        Log.i(TAG, "onCreateView: " + idPersona);


        //service();
        return view;
    }


    @OnClick(R.id.btValidar)
    public void solicitar(View view) {

        validator.validate();
        setTransferencia(nro_op.getText().toString(),et_fecha.getText().toString(), "1", monto.getText().toString(),idPersona);
        // finish();

    }

    protected void setTransferencia(String trans_comprobante, String trans_fecha, String trans_banco, String trans_monto, String  trans_personid)
    {

        RequestTransferencia dataTrans = new RequestTransferencia(trans_comprobante, trans_fecha,trans_banco,trans_monto,trans_personid);

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
        servicio.setTransferencia(dataTrans).enqueue(new Callback<RequestTransferencia>() {
            @Override
            public void onResponse(Call<RequestTransferencia> call, Response<RequestTransferencia> response) {
                if (response.code() == 200){

                    Log.i(TAG, "onResponse 200: ");
                    Toast.makeText(getActivity(), "Pago exitoso", Toast.LENGTH_LONG).show();
                    showPlacePickerDialog("Solicitud procesada con éxito", "Transferencia Bancaria", 3);

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
            public void onFailure(Call<RequestTransferencia> call, Throwable t) {

            }
        });
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
