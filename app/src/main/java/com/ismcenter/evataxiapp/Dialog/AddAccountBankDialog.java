package com.ismcenter.evataxiapp.Dialog;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Actividades.DatosBancariosFragment;
import com.ismcenter.evataxiapp.Modelos.DataDatosBancarios;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
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


public class AddAccountBankDialog extends DialogFragment implements Validator.ValidationListener {

    static String tokenDialog;
    static List<String> nombresBancos;
    static int idBanco, idUsuarioDialog, idtipoCuenta, idPersonaFragment;

    @NotEmpty(message = "Escriba su nombre")
    @BindView(R.id.editNombrePersona)
    EditText editNombrePersona;
    @NotEmpty(message = "Escriba su numero de cuenta")
    @BindView(R.id.editNumeroCuenta)
    EditText editNumeroCuenta;
    @BindView(R.id.spinnerNombreBanco)
    Spinner spinnerNombreBanco;
    @BindView(R.id.spinnerTipoCuenta)
    Spinner spinnerTipoCuenta;
    @BindView(R.id.btnAceptar)
    Button btnAceptar;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    private ProgressDialog loading;
    Validator validator;


    public static AddAccountBankDialog newInstance(String idUsuario, List<String>  bancoRecieved, String token, String idPersona) {
        AddAccountBankDialog f = new AddAccountBankDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("idUsuario", idUsuario);
        f.setArguments(args);
        Log.e("AlertdFragment",idUsuario);
        idUsuarioDialog= Integer.parseInt(idUsuario);
        nombresBancos = bancoRecieved;
        tokenDialog = token;
        idPersonaFragment = Integer.parseInt(idPersona);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_account_bank, container, false);

        getDialog().setTitle("Agregar Banco");
        ButterKnife.bind(this, rootView);
        validator = new Validator(this);
        validator.setValidationListener(this);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,nombresBancos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNombreBanco.setAdapter(dataAdapter);
        spinnerNombreBanco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idBanco = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter spinner_adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.tipo_cuenta, android.R.layout.simple_spinner_item);
        spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoCuenta.setAdapter(spinner_adapter2);
        spinnerTipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idtipoCuenta = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }


    @Override
    public void onValidationSucceeded() {
        if (idtipoCuenta == 0){
            Toast.makeText(getContext(), "Escoga un tipo de cuenta valido", Toast.LENGTH_SHORT).show();
        }if (editNumeroCuenta.getText().toString().length() < 20){
            Toast.makeText(getContext(), "Le faltan numeros en su cuenta", Toast.LENGTH_SHORT).show();
        }else
         {
            registrarCuentaBancaria();
        }
    }

    private void registrarCuentaBancaria() {
        loading= ProgressDialog.show(getContext(),"Cargando","Por favor espere", false, false);

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer "+tokenDialog)
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

        DataDatosBancarios dataDatosBancarios = new DataDatosBancarios(idUsuarioDialog,nombresBancos.get(idBanco),
                editNumeroCuenta.getText().toString(),idtipoCuenta,editNombrePersona.getText().toString(),idPersonaFragment);

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);
        servicio.createBankAccounts(dataDatosBancarios).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.code()==200){
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString());
                    //JSONObject respuesta = response.body();

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                loading.dismiss();
                Log.e("Pase por aqui on Fail", t.getCause().toString());

            }
        });
    }

    private void respuestaServicio(String message) {
        loading.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        DatosBancariosFragment mf = (DatosBancariosFragment) getFragmentManager().findFragmentById(R.id.contenedorPrincipalFragmento);
        mf.actualizarLista();
        getDialog().dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());


            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }


    @OnClick({R.id.btnCancelar, R.id.btnAceptar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelar:
                getDialog().cancel();
                break;
            case R.id.btnAceptar:
                validator.validate();
                break;
        }
    }
}