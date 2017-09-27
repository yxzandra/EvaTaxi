package com.ismcenter.evataxiapp.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Modelos.Request.RequestOlvidoClave;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecuperarClaveActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Email(message = "Debe ingresar un correo valido")
    @BindView(R.id.editRecuperarEmail)
    EditText editRecuperarEmail;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @OnClick(R.id.btnEnviar)
    public void onClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        solicitarClaveTemporal();
    }

    private void solicitarClaveTemporal() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);

        RequestOlvidoClave requestOlvidoClave = new RequestOlvidoClave(editRecuperarEmail.getText().toString());

        servicio.forgetKey(requestOlvidoClave).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.code()==200){
                    respuestaServicio(response.body().getAsJsonObject().get("message").getAsString(),response.code());
                    //JSONObject respuesta = response.body();

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("message")), response.code());
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                respuestaServicio("No hubo respuesta del servidor", 400);
            }
        });
    }

    private void respuestaServicio(String message, int code) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if (code==200) {
            Intent intent = new Intent(this, PrincipalActivity.class);
            startActivity(intent);
            finish();
        }
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
}
