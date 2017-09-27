package com.ismcenter.evataxiapp.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Actividades.Choferes.MainConductorActivity;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.MainPasajeroActivity;
import com.ismcenter.evataxiapp.Modelos.Request.ChangePasswordModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseChangePasswordModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;

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

/**
 * Created by yxzan on 03/12/2016.
 */


public class CambiarClaveTemporalActivity extends AppCompatActivity implements Validator.ValidationListener {

    private ProgressDialog loading;
    Validator validator;
    Intent intent;
    String tipoPasajero;
    String idUsuario, idPersona,token,clave,usu_usuario;

    @Password(min = 4, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Debe contener numeros y letras")
    @BindView(R.id.editCambiarClave)
    EditText editCambiarClave;
    @ConfirmPassword(message = "Las claves no coinciden")
    @BindView(R.id.editCambiarClaveConfirmar)
    EditText editCambiarClaveConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_clave_temporal);
        ButterKnife.bind(this);

        PreferencesManager pm = new PreferencesManager(this);
        idUsuario = pm.getUserId();
        idPersona = pm.getPersonID();
        token = pm.getServerToken();

        clave = getIntent().getExtras().getString("clave");
        usu_usuario = getIntent().getExtras().getString("usu_usuario");
        tipoPasajero = getIntent().getExtras().getString("tipoPasajero");

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        loading= ProgressDialog.show(CambiarClaveTemporalActivity.this,"Cargando","Por favor espere", false, false);
        changePassword();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    private void changePassword() {
        ChangePasswordModel changePasswordModel = new ChangePasswordModel(usu_usuario,clave,editCambiarClave.getText().toString());

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
        //declaromos la interfaz

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);
        servicio.changePassword(changePasswordModel).enqueue(new Callback<ResponseChangePasswordModel>() {
            @Override
            public void onResponse(Call<ResponseChangePasswordModel> call, Response<ResponseChangePasswordModel> response) {
                loading.dismiss();
                if (response.code()==200){
                   respuestaServicio(response.body().getMessage(), response.code());
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"), response.code());
                        Log.e("respuestaServicio", jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseChangePasswordModel> call, Throwable t) {
                loading.dismiss();
                respuestaServicio("No hubo respuesta del servidor",400);
            }
        });

    }

    @OnClick(R.id.btnCambiarClave)
    public void onClick() {
        validator.validate();
    }

    private void respuestaServicio(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if (code == 200){
            if (tipoPasajero.equals("2")){
                intent = new Intent(this, MainConductorActivity.class);
            }else {
                intent = new Intent(this, MainPasajeroActivity.class);
            }
            intent.putExtra("idPersona",idPersona);
            intent.putExtra("idUsuario",idUsuario);
            intent.putExtra("token",token);
            startActivity(intent);
            finish();
        }
    }
}
