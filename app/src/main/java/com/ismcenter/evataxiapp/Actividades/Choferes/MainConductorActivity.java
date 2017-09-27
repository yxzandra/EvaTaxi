package com.ismcenter.evataxiapp.Actividades.Choferes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Actividades.CalificarFragment;
import com.ismcenter.evataxiapp.Actividades.DatosBancariosFragment;
import com.ismcenter.evataxiapp.Actividades.PrincipalActivity;
import com.ismcenter.evataxiapp.Interfaces.DriverInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Notifications.Notificacion;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDriverProfileModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferenceActualService;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainConductorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateTitleToolbar {
    Fragment frag;
    FragmentTransaction fragTansaction;
    Intent intent;
    String idPersona,idUsuario,token,nombreConductor,tipoCarro,placaCarro;
    Toolbar toolbar;
    private TextView tvnombre, tvEmail;
    private NavigationView navigationView;
    Boolean respuestaBotonBack = false;
    PreferencesManager preferences;
    PreferenceActualService preferenceActualService;
    Notificacion notificacion;


    public MainConductorActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_conductor);
        ButterKnife.bind(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = new PreferencesManager(getApplicationContext());
        preferenceActualService = new PreferenceActualService(getApplicationContext());

        idUsuario = preferences.getUserId();
        idPersona = preferences.getPersonID();
        token = preferences.getServerToken();


        Intent intn = getIntent();

        if (intn.hasExtra("SolicitudDeServicioAutomatica")){
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            notificacion= (Notificacion) bundle.getSerializable("SolicitudDeServicioAutomatica");
            assert notificacion != null;
            Log.e("notificacion",notificacion.toString());

        }if (intn.hasExtra("SolicitudDeServicioManual")){
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            notificacion= (Notificacion) bundle.getSerializable("SolicitudDeServicioManual");
            assert notificacion != null;
            Log.e("notificacion",notificacion.toString());

        }if (intn.hasExtra("CancelacionDeSolicitud")){
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            notificacion= (Notificacion) bundle.getSerializable("CancelacionDeSolicitud");
            assert notificacion != null;
            Log.e("notificacion",notificacion.toString());

        }else {
            // Si es una llamada de una notificación
            PreferencesManager pm = new PreferencesManager(this);
            idPersona = pm.getPersonID();
            idUsuario = pm.getUserId();
            token = pm.getServerToken();
        }

        service();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (notificacion != null && preferenceActualService.getKeyServiceId().equals("null") && !notificacion.getTipo().equals("CancelacionDeSolicitud")){
            toolbar.setTitle("Nuevo Servicio Registrado");
            frag = new ReceptSolicitudFragment();
            frag = ReceptSolicitudFragment.newInstance(notificacion);
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }if (notificacion != null && notificacion.getTipo().equals("CancelacionDeSolicitud")) {
            toolbar.setTitle("Servicio Cancelado");
            frag = new ServicioCanceladoFragment();
            frag = ServicioCanceladoFragment.newInstance(notificacion);
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }if (notificacion == null && preferenceActualService.getKeyServiceId().equals("null")) {
            toolbar.setTitle("Servicios");
            frag = new ServiciosDisponiblesFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }if (notificacion == null && preferenceActualService.getKeyAcceptService().equals("true")){
            toolbar.setTitle("Servicio Actual");
            frag = new ServicioActualFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_conductor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            toolbar.setTitle("Perfil del Conductor");
            frag = new PerfilConductorFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id == R.id.nav_estatus) {

            toolbar.setTitle("Estatus del conductor");
            frag = new EstatusConductorFragment();
            frag = EstatusConductorFragment.newInstance(nombreConductor,tipoCarro,placaCarro);
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        } else if (id == R.id.nav_servicios) {
            Log.e("nav_servicios", preferenceActualService.getKeyServiceId());
            if (preferenceActualService.getKeyServiceId().equals("null")) {
                toolbar.setTitle("Servicios");
                frag = new ServiciosDisponiblesFragment();
                fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                fragTansaction.commit();
            }else{
                toolbar.setTitle("Servicio Actual");
                frag = new ServicioActualFragment();
                fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                fragTansaction.commit();
            }

        }else if (id == R.id.nav_manage) {
            toolbar.setTitle("Pasajeros por Calificar");
            frag = new CalificarFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id == R.id.nav_historial_servicios) {
            toolbar.setTitle("Servicios Realizados");
            frag = new HistorialServiciosFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id == R.id.nav_pagos_recibidos) {
            toolbar.setTitle("Pagos Recibidos");
            frag = new PagosRealizadosFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id == R.id.nav_pagos_pendientes) {
            toolbar.setTitle("Pagos Pendientes");
            frag = new PagosPendientesFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id == R.id.nav_monto_total) {
            toolbar.setTitle("Monto por servicio");
            frag = new MontoPorServicioFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();

        }else if (id ==R.id.nav_log_out){
            logout();

        }else if (id ==R.id.nav_banc){
            toolbar.setTitle("Datos Bancarios");
            frag = new DatosBancariosFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //se solicita el servicio
    public void service() {


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

        DriverInterface servicio = retrofit.create(DriverInterface.class);
        servicio.driverProfile(Integer.parseInt(idPersona)).enqueue(new Callback<ResponseDriverProfileModel>() {
            @Override
            public void onResponse(Call<ResponseDriverProfileModel> call, Response<ResponseDriverProfileModel> response) {

                if (response.code()==200){
                    View header=navigationView.getHeaderView(0);
                    tvnombre = (TextView) header.findViewById(R.id.usernameconductor);
                    tvEmail = (TextView) header.findViewById(R.id.emailconductor);
                    tvEmail.setText(response.body().getPerson_correo());
                    tvnombre.setText(response.body().getPerson_nombre());

                    nombreConductor = response.body().getPerson_nombre();
                    tipoCarro = response.body().getCar_modelo();
                    placaCarro = response.body().getCar_placa();
                }
            }

            @Override
            public void onFailure(Call<ResponseDriverProfileModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logoutDialog();
            if (respuestaBotonBack){
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    private void logoutDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("¿Desea salir de EvaTaxi?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Si desea salir de la aplicacion presione el boton de aceptar")
                .setCancelable(false)
                .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        logout();
                        respuestaBotonBack = true;
                    }
                })
                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        respuestaBotonBack = false;
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void logout() {
        preferences = new PreferencesManager(this);
        preferences.clearPreferences();

        Toast.makeText(this, "Ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show();
        intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUpdateTitleToolbar(String titleToolbar) {
        toolbar.setTitle(titleToolbar);

    }
}
