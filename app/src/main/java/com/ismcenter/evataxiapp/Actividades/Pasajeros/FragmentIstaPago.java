package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Modelos.Request.RequestInstaPago;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.splunk.mint.Mint;

import java.io.IOException;
import java.util.Calendar;
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

public class FragmentIstaPago extends Fragment implements Validator.ValidationListener , AdapterView.OnItemSelectedListener{

    private static final String TAG = "Recargadesaldo" ;
    Spinner spinner;
    private int id_banco;
    private String idPersona, token;
    PreferencesManager preferences;
    private String KeyId = "6B41C915-BF50-451B-BF48-0EC1CEBBD4EC";
    private String PublicKeyId = "90c60271f87111514a827372774b53c6";
    private String StatusId = "2";
    private String IP = "192.168.1.2";
    private String OrderNumber= "12";
    private String Address = "Casa 10";
    private String City = "Bolivar";
    private String ZipCode = "8050";
    private String State = "Bolivar";
    private String  fecha;

    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;


    @NotEmpty(message = "Debe ingresar un nombre")
    @BindView(R.id.et_nombre)
    EditText et_nombre;


    @NotEmpty(message = "Debe ingresar su CI o Rif")
    @BindView(R.id.et_cedula)
    EditText et_cedula;

    @NotEmpty(message = "Debe ingresar su número de tarjeta")
    @BindView(R.id.et_tarjeta)
    EditText et_tarjeta;


    @NotEmpty(message = "Debe ingresar una fecha")
    @BindView(R.id.et_fecha)
    EditText et_fecha;


    @NotEmpty(message = "Debe ingresar un código de seguridad")
    @BindView(R.id.et_codigo)
    EditText et_codigo;

    @NotEmpty(message = "Debe ingresar un monto")
    @BindView(R.id.et_monto)
    EditText et_monto;

    @NotEmpty(message = "Debe ingresar una dscripción")
    @BindView(R.id.et_descripcion)
    EditText et_descripcion;

    @BindView(R.id.btValidar)
    Button btValidar;


    Validator validator;
    private ProgressDialog loading;
    Calendar myCalendar = Calendar.getInstance();

    public static Fragment newInstance(String idPersona, String token) {
        Log.i(TAG, "newInstance: " +idPersona + token);
        return new FragmentIstaPago();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(getActivity(), "28d7577c");

        View view = inflater.inflate(R.layout.fragment_instapago ,container, false);
        ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        PreferencesManager pm = new PreferencesManager(getActivity());
        idPersona = pm.getPersonID();
        token = pm.getServerToken();

        //Spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.meses, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.years, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //service();
        return view;
    }


    @OnClick(R.id.btValidar)
    public void solicitar(View view) {

        validator.validate();
        setDeposito(KeyId,PublicKeyId,StatusId,et_monto.getText().toString(),et_descripcion.getText().toString(),
                et_nombre.getText().toString(),et_cedula.getText().toString(),et_tarjeta.getText().toString(),
                et_codigo.getText().toString(),"12/12/12",IP,OrderNumber,Address,City,ZipCode,State,idPersona);

    }

    protected void setDeposito(String keyId, String publicKeyId, String statusId, String amount,
                               String description, String cardHolder, String cardHolderID,
                               String cardNumber, String CVC, String expirationDate, String IP,
                               String orderNumber, String address, String city, String zipCode,
                               String state, String person_id)
    {

        RequestInstaPago dataInstaPago = new RequestInstaPago(keyId, publicKeyId,statusId, amount,
                description,description,cardHolder,cardHolderID,
                cardNumber,CVC,expirationDate,IP,orderNumber,address,city,zipCode,state,person_id);


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
        servicio.setInstatPago(dataInstaPago).enqueue(new Callback<RequestInstaPago>() {
            @Override
            public void onResponse(Call<RequestInstaPago> call, Response<RequestInstaPago> response) {
                //   Log.i(TAG, "onResponse: " + response.body().getPago().getPagos_fecha().toString());

                Log.i(TAG, "onResponse: ");

                Toast.makeText(getActivity(), "Pago exitoso" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<RequestInstaPago> call, Throwable t) {

            }
        });
    }

    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onValidationSucceeded() {

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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fecha = adapterView.getItemAtPosition(i).toString().concat(adapterView.getItemAtPosition(i).toString());
        Log.i(TAG, "adapterView: " + fecha);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
