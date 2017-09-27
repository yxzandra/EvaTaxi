package com.ismcenter.evataxiapp.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ismcenter.evataxiapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;



public class TipoPasajeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_pasajero);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.layoutPasajero, R.id.layoutTaxista})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layoutPasajero:
                intent = new Intent(this,LoginActivity.class);
                intent.putExtra("TipoPasajero","1");
                startActivity(intent);
                break;
            case R.id.layoutTaxista:
                intent = new Intent(this,LoginActivity.class);
                intent.putExtra("TipoPasajero","2");
                startActivity(intent);
                break;
        }
    }
}
