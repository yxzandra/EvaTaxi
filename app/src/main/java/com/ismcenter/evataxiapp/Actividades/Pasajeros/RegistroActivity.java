package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Modelos.Request.RequestRegisterUserModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseUserModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Interfaces.UserMagnagementIterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.splunk.mint.Mint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Debe ingresar su nombre o su razon social")
    @BindView(R.id.editNombre)
    EditText editNombre;
    @NotEmpty(message = "Debe ingresar su cedula")
    @BindView(R.id.editCedula)
    EditText editCedula;
    @Email(message = "Debe ingresar un correo valido")
    @BindView(R.id.editCorreo)
    EditText editCorreo;
    @NotEmpty(message = "Ingresar su fecha de nacimiento y debe tener +18")
    @BindView(R.id.editFechaNacimiento)
    EditText editFechaNacimiento;
    @NotEmpty(message = "Debe ingresar su telefono")
    @BindView(R.id.editTelefono)
    EditText editTelefono;
    @NotEmpty(message = "Debe ingresar un nombre de usuario")
    @BindView(R.id.editNombreUsuario)
    EditText editNombreUsuario;
    @Password(min = 4, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Debe contener numeros y letras")
    @BindView(R.id.editClave)
    EditText editClave;
    @ConfirmPassword(message = "Las claves no coinciden")
    @BindView(R.id.editConfirmarClave)
    EditText editConfirmarClave;
    @Checked(message = "Debe confirmar los terminos")
    @BindView(R.id.radioButtonTerminos)
    RadioButton radioButtonTerminos;
    @NotEmpty(message = "Debe ingresar su dirección")
    @BindView(R.id.editDireccion)
    EditText editDireccion;
    @BindView(R.id.spinner2)
    Spinner spinner2;

    private ProgressDialog loading;
    Validator validator;
    String posicion;
    Calendar myCalendar = Calendar.getInstance();
    int yearActitviy, monthActivity, dayActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(RegistroActivity.this, "28d7577c");
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this,R.array.cedula,android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinner_adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: posicion= "V"; break;
                    case 1: posicion= "E"; break;
                    case 2: posicion= "J"; break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void onValidationSucceeded() {
        if(mayorEdad()){
            String dtStart = editFechaNacimiento.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = format.parse(dtStart);
                System.out.println(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            loading = ProgressDialog.show(RegistroActivity.this, "Cargando", "Por favor espere", false, false);
            agregarUsuario(editCedula.getText().toString(),editNombre.getText().toString(),
                    editCorreo.getText().toString(),editDireccion.getText().toString(),editTelefono.getText().toString(),
                    editFechaNacimiento.getText().toString(),1,editNombreUsuario.getText().toString(),
                    editClave.getText().toString(),1);
        }else{
            Toast.makeText(this, "Debe ser Mayor de 18 años para registrarse en la aplicación", Toast.LENGTH_LONG).show();
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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

            yearActitviy = year;
            monthActivity = monthOfYear;
            dayActivity =  dayOfMonth;
        }

    };


    @OnClick({R.id.editFechaNacimiento, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editFechaNacimiento:
                new DatePickerDialog(RegistroActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.button:
                validator.validate();
                break;
        }
    }

    private boolean mayorEdad() {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy

        Calendar calendar = new GregorianCalendar(yearActitviy, monthActivity-1, dayActivity);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = ( hoy.getTime() - fecha.getTime() )/MILLSECS_PER_DAY;
        System.out.println(diferencia);

        return diferencia / 365 >= 18 && !editFechaNacimiento.getText().toString().equals("");

    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editFechaNacimiento.setText(sdf.format(myCalendar.getTime()));

    }

    protected void agregarUsuario(String person_cedula, String person_nombre, String person_correo,
                                  String person_direccion, String person_telf, String person_fechaNacimiento,
                                  int person_tipo, String usu_usuario, String usu_clave, int rol_id) {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        RequestRegisterUserModel registerUserModel = new RequestRegisterUserModel(person_cedula,person_nombre,person_correo,
                person_direccion,person_telf,person_fechaNacimiento,person_tipo,usu_usuario,usu_clave,rol_id);

        UserMagnagementIterface servicio = retrofit.create(UserMagnagementIterface.class);

        servicio.createUser(registerUserModel).enqueue(new Callback<ResponseUserModel>() {
            @Override
            public void onResponse(Call<ResponseUserModel> call, Response<ResponseUserModel> response) {
                loading.dismiss();

                if (response.code() == 200){
                    respuestaServicio("Usuario Guardado Exitosamente", response.code());

                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(String.valueOf(jObjError.get("message")), response.code());
                        Log.e("Registro Correcto", String.valueOf(jObjError.get("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUserModel> call, Throwable t) {
                loading.dismiss();
                respuestaServicio("No hubo respuesta del servidor",400);
            }
        });
    }

    private void respuestaServicio(String message, int code) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        if(code == 200 ){
            finish();
        }
    }
}
