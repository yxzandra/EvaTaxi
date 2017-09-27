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
import com.ismcenter.evataxiapp.Modelos.ArregloDatosBancarios;
import com.ismcenter.evataxiapp.Modelos.DataDatosBancarios;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
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


public class ModifyAccountBankDialog extends DialogFragment implements Validator.ValidationListener {
    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/";

    static String tokenDialog;
    static List<String> nombresBancos, tipo_cuenta;
    static int idBanco, idtipoCuenta;
    static DataDatosBancarios dataDatosBancariosDialog;
    static ArregloDatosBancarios nombreBancoActualDialog;

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
    @BindView(R.id.btnModificar)
    Button btnModificar;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    private ProgressDialog loading;
    Validator validator;


    public static ModifyAccountBankDialog newInstance(List<String> bancoRecieved, String token,
                                                      DataDatosBancarios dataDatosBancarios,
                                                      ArregloDatosBancarios nombreBancoActual) {
        ModifyAccountBankDialog f = new ModifyAccountBankDialog();

        nombresBancos = bancoRecieved;
        tokenDialog = token;
        dataDatosBancariosDialog = dataDatosBancarios;
        nombreBancoActualDialog = nombreBancoActual;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_modify_account_bank, container, false);

        getDialog().setTitle("Modificar Banco");
        ButterKnife.bind(this, rootView);
        validator = new Validator(this);
        validator.setValidationListener(this);

        for (int i = 0; i<nombresBancos.size();i++){

            if (nombresBancos.get(i).equals(nombreBancoActualDialog.getNombreBanco())){
                idBanco=i;
            }
        }
        tipo_cuenta = Arrays.asList(getResources().getStringArray(R.array.tipo_cuenta));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresBancos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNombreBanco.setAdapter(dataAdapter);
        spinnerNombreBanco.setSelection(idBanco);
        spinnerNombreBanco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idBanco = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ArrayAdapter spinner_adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.tipo_cuenta, android.R.layout.simple_spinner_item);
        spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoCuenta.setAdapter(spinner_adapter2);
        spinnerTipoCuenta.setSelection(dataDatosBancariosDialog.getDabTipoCuenta());
        idtipoCuenta = dataDatosBancariosDialog.getDabTipoCuenta();
        spinnerTipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idtipoCuenta = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editNombrePersona.setText(dataDatosBancariosDialog.getDabNombre());
        editNumeroCuenta.setText(dataDatosBancariosDialog.getDabCuenta());
        return rootView;
    }


    @Override
    public void onValidationSucceeded() {
        if (idtipoCuenta == 0) {
            Toast.makeText(getContext(), "Escoga un tipo de cuenta valido", Toast.LENGTH_SHORT).show();
        }if (editNumeroCuenta.getText().toString().length() < 20){
            Toast.makeText(getContext(), "Le faltan numeros en su cuenta", Toast.LENGTH_SHORT).show();
        }else {
            modificarCuentaBancaria();
        }
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

    private UserMagnagementIterface llamarRetrofit() {
        loading = ProgressDialog.show(getContext(), "Cargando", "Por favor espere", false, false);

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + tokenDialog)
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


        return retrofit.create(UserMagnagementIterface.class);
    }

    private void modificarCuentaBancaria() {

        llamarRetrofit().modifyBankAccounts(nombreBancoActualDialog.getDab_id(),
                                            nombresBancos.get(idBanco),
                                            editNumeroCuenta.getText().toString(),
                                            idtipoCuenta,
                                            editNombrePersona.getText().toString())
                .enqueue(new Callback<JsonElement>() {
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

            }
        });
    }

    private void eliminarDatoBancario() {

        llamarRetrofit().deleteBankAccounts(dataDatosBancariosDialog.getUsuId(),dataDatosBancariosDialog.getDabBanco())
                .enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.code() == 200) {
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString());
                    //JSONObject respuesta = response.body();

                } else {
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

    @OnClick({R.id.btnCancelar, R.id.btnModificar, R.id.btnEliminar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelar:
                getDialog().cancel();
                break;
            case R.id.btnModificar:
                validator.validate();
                break;
            case R.id.btnEliminar:
                eliminarDatoBancario();
                break;

        }
    }

}