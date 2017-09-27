package com.ismcenter.evataxiapp.Actividades;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismcenter.evataxiapp.Actividades.Choferes.MainConductorActivity;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.MainPasajeroActivity;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.RegistroActivity;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PermissionsDispatcher;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        Mint.initAndStartSession(this.getApplication(), "5fa73dde");

        /****** Verificación de usuario logueado ************/

        PreferencesManager preferences;
        preferences = new PreferencesManager(this);

        String userID = preferences.getUserId();
        if (userID == null ||
            userID.equals("null") ||
            userID.equals("")) {
            return;
        }

        String serverToken = preferences.getServerToken();
        String personID = preferences.getPersonID();
        String userType = preferences.getUserType();

        Intent intent = null;
        switch (userType) {
            case "1": //Pasajero
                intent = new Intent(this, MainPasajeroActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case "2": //Conductor
                intent = new Intent(this, MainConductorActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                break;
        }

        if (intent == null) {
            return;
        }

        Log.e("EVATAXI_D", "PrincipalActivity-> usuario logueado" + userID);
        intent.putExtra("idPersona", personID);
        intent.putExtra("idUsuario", userID);
        intent.putExtra("token", serverToken);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionsDispatcher.showDialogPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcher.onRequestPermissionsResult(PrincipalActivity.this, requestCode);
    }

    @OnClick({R.id.btnRegistrate, R.id.btnIniciarSesion, R.id.btnGmail, R.id.btnFacebook})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnRegistrate:
                intent = new Intent(this, RegistroActivity.class);
                startActivity(intent);
                break;
            case R.id.btnIniciarSesion:
                intent = new Intent(this, TipoPasajeroActivity.class);
                startActivity(intent);
                break;
            case R.id.btnGmail:

                break;
            case R.id.btnFacebook:
                break;
        }
    }
}
