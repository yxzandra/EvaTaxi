package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ismcenter.evataxiapp.Adaptador.ChoferDisponibleAdapter;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Modelos.ChoferModel;
import com.ismcenter.evataxiapp.Modelos.DataChofer;
import com.ismcenter.evataxiapp.R;
import com.splunk.mint.Mint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityChoferDisponible extends AppCompatActivity {

    public static final String BASE_URL = "http://api.evataxi.com.ve/public/api/"; // esto no va aca cambiar
    private static final String TAG = "ActivityChoferDisp";
    private ChoferDisponibleAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(ActivityChoferDisponible.this, "28d7577c");
        setContentView(R.layout.activity_chofer_disponible);
        Log.i(TAG, "onCreate: ");
        setToolbar();
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.choferes_list_disponible);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        service();

    }

    //se solicita el servicio
    public void service() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DriverInterface servicio = retrofit.create(DriverInterface.class);
        servicio.getDrivers().enqueue(new Callback<ChoferModel>() {
            @Override
            public void onResponse(Call<ChoferModel> call, Response<ChoferModel> response) {

                adapter = new ChoferDisponibleAdapter(response.body().getData(), new ChoferDisponibleAdapter.BrokerItemClick() {
                    @Override
                    public void onBrokerClick(DataChofer clickedBroker) {

                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ChoferModel> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }

        });

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
