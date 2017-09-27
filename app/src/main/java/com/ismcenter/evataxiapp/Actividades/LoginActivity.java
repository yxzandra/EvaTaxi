package com.ismcenter.evataxiapp.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ismcenter.evataxiapp.Actividades.Choferes.MainConductorActivity;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.MainPasajeroActivity;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.ismcenter.evataxiapp.Modelos.Request.LoginUserModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseLoginUserModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Services.EvataxiFirebaseInstanceIDService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Debe ingresar su nombre o su razon social")
    @BindView(R.id.editNombreUsuarioLogin)
    EditText editNombreUsuarioLogin;
    @NotEmpty(message = "Debe ingresar su clave")
    @BindView(R.id.editClaveLogin)
    EditText editClaveLogin;
    @BindView(R.id.btnIniciarSesionLogin)
    Button btnIniciarSesionLogin;

    private ProgressDialog loading;
    Validator validator;
    Intent intent;
    String tipoPasajero;
    PreferencesManager preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tipoPasajero = getIntent().getExtras().getString("TipoPasajero");
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void onValidationSucceeded() {
        loading= ProgressDialog.show(LoginActivity.this,"Cargando","Por favor espere", false, false);
        login();
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

    @OnClick({R.id.btnIniciarSesionLogin, R.id.textRecuperarClave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIniciarSesionLogin:
                validator.validate();
                break;
            case R.id.textRecuperarClave:
                intent = new Intent(this, RecuperarClaveActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void login(){
        LoginUserModel loginUserModel = new LoginUserModel(editNombreUsuarioLogin.getText().toString(),editClaveLogin.getText().toString(),tipoPasajero);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);
        servicio.loginUser(loginUserModel).enqueue(new Callback<ResponseLoginUserModel>() {
            @Override
            public void onResponse(Call<ResponseLoginUserModel> call, Response<ResponseLoginUserModel> response) {
                loading.dismiss();
                if (response.code() == 200) {
                    ejecucionLogueo(response.body());
                    Log.e("200", response.message());

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                        Log.e("try", jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage());
                        Log.e("catch", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginUserModel> call, Throwable t) {
                loading.dismiss();
                respuestaServicio("No hubo respuesta del servidor");
            }
        });

    }

    public void ejecucionLogueo(ResponseLoginUserModel loginUserModel){
        if (loginUserModel.getSuccess()){

            String firebase_token = FirebaseInstanceId.getInstance().getToken();
            EvataxiFirebaseInstanceIDService.registerToken(firebase_token,loginUserModel.getIdUsuario().toString());

            preferences = new PreferencesManager(this);
            preferences.setUserId(loginUserModel.getIdUsuario().toString());
            preferences.setServerToken(loginUserModel.getToken());
            preferences.setPersonID(loginUserModel.getIdPersona().toString());

            //tipo pasajero = 1 es el pasajero y tipo 2 es el conductor

            Log.e("getPrimerLogin", loginUserModel.toString() );

            if (loginUserModel.getPrimerLogin()){
                intent = new Intent(this, CambiarClaveTemporalActivity.class);
                intent.putExtra("clave", editClaveLogin.getText().toString());
                intent.putExtra("usu_usuario", editNombreUsuarioLogin.getText().toString());
                intent.putExtra("tipoPasajero", tipoPasajero);
                startActivity(intent);
                finish();
            }else {
                switch (tipoPasajero) {
                    case "2": //Conductor
                        preferences.setUserType("2");
                        intent = new Intent(this, MainConductorActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    case "1": //Pasajero
                        preferences.setUserType("1");
                        respuestaServicio(loginUserModel.getMessage());
                        intent = new Intent(this, MainPasajeroActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("idPersona", loginUserModel.getIdPersona().toString());
                        intent.putExtra("idUsuario", loginUserModel.getIdUsuario().toString());
                        intent.putExtra("token", loginUserModel.getToken());
                        startActivity(intent);
                        finish();
                        break;
                }
            }

        }else {
            respuestaServicio("Datos incorrectos");
        }
    }
    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
