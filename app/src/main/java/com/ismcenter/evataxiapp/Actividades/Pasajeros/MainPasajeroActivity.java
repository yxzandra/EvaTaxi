package com.ismcenter.evataxiapp.Actividades.Pasajeros;

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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ismcenter.evataxiapp.Actividades.ActivityArchivo;
import com.ismcenter.evataxiapp.Actividades.DatosBancariosFragment;
import com.ismcenter.evataxiapp.Actividades.ListarCalificarFragment;
import com.ismcenter.evataxiapp.Actividades.PrincipalActivity;
import com.ismcenter.evataxiapp.Interfaces.PasajeroInterface;
import com.ismcenter.evataxiapp.Interfaces.UpdateTitleToolbar;
import com.ismcenter.evataxiapp.Modelos.Notifications.Notificacion;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePasajeroProfileModel;
import com.ismcenter.evataxiapp.R;
import com.ismcenter.evataxiapp.Utils.PreferencesManager;
import com.splunk.mint.Mint;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPasajeroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UpdateTitleToolbar {
    private static final String TAG = "MainPasajeroActivity";
    Fragment frag;
    FragmentTransaction fragTansaction;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private String drawerTitle;
    private TextView costoKm;
    private TextView tvnombre, tvEmail;
    private Switch myswitch;
    ImageView imMap;
    private boolean on = false, respuestaBotonBack = false;
    Notificacion notificacion;

    private NavigationView navigationView;

    String idPersona, idUsuario, token;
    PreferencesManager preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(MainPasajeroActivity.this, "28d7577c");
        setContentView(R.layout.activity_main_pasajero);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intn = getIntent();

        if (intn.hasExtra("AceptacionDeSolicitudDeServicioAutomatica")){
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            notificacion= (Notificacion) bundle.getSerializable("AceptacionDeSolicitudDeServicioAutomatica");
            assert notificacion != null;
            Log.i(TAG, "onCreate: ");
            Log.e("notificacion",notificacion.toString());
        }
        else
        if (intn.hasExtra("ConductorLlegoAOrigen")){
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            notificacion= (Notificacion) bundle.getSerializable("ConductorLlegoAOrigen");
            assert notificacion != null;
            Log.i(TAG, "onCreate: ");
            Log.e("notificacion",notificacion.toString());
        }


        // Si es una llamada de otro activity
        if (intn.hasExtra("idPersona") && intn.hasExtra("idUsuario") && intn.hasExtra("token")) {
            idPersona = getIntent().getExtras().getString("idPersona");
            idUsuario = getIntent().getExtras().getString("idUsuario");
            token = getIntent().getExtras().getString("token");

        } else {
            // Si es una llamada de una notificación
            PreferencesManager pm = new PreferencesManager(this);
            idPersona = pm.getPersonID();
            idUsuario = pm.getUserId();
            token = pm.getServerToken();
        }
        Log.i(TAG, "onCreate: " +idPersona+idUsuario);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pasajero);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        service();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Solicitud");
        frag = new SolicitudAutomaticaFragment();
        frag = SolicitudAutomaticaFragment.newInstance(idPersona, token);
        fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
        fragTansaction.commit();

        if (navigationView != null) {
            setupDrawerContent(navigationView);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");

        if (notificacion != null && notificacion.getTipo().equals("AceptacionDeSolicitudDeServicioAutomatica")){
            showPlacePickerDialog("Solicitud aceptada con éxito","Espere mientras llega el conductor",0);
        }
        else
        if (notificacion != null && notificacion.getTipo().equals("ConductorLlegoAOrigen")) {
            showPlacePickerDialog("Conductor ha llegado al destino", "presione Ok", 2);
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

    private void showPlacePickerDialog(String titulo, String suTitulo, int tipo) {
        AlertDialogSolicitud placeSearchDialog = new AlertDialogSolicitud(titulo,suTitulo,tipo,this, new AlertDialogSolicitud.LocationNameListener() {
            @Override
            public void locationName(String locationName) {

            }
        });
        placeSearchDialog.show();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.nav_validacion) {
//            Intent i = new Intent(MainPasajeroActivity.this, ActivityValidacion.class);
//            startActivity(i);
//        }
        if (id == R.id.nav_perfil) {
            toolbar.setTitle(" Perfil ");
            frag = new PerfilPasajeroFragment();
            frag = PerfilPasajeroFragment.newInstance(idPersona, token);
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();
        }
        if (id == R.id.nav_recarga) {
            Intent i = new Intent(MainPasajeroActivity.this, MetodoPagoFragment.class);
            startActivity(i);
        }
        if (id == R.id.nav_tomarImagen) {
            Intent i = new Intent(MainPasajeroActivity.this, ActivityArchivo.class);
            startActivity(i);
        }
        if (id == R.id.nav_favoritos) {
            Intent i = new Intent(MainPasajeroActivity.this, FavoritosFragment.class);
            i.putExtra("key", 0);
            startActivity(i);

           /* toolbar.setTitle("Mis favoritos");
            frag = new FavoritosFragment();
            frag = FavoritosFragment.newInstance(token,idPersona,0);
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();*/
        }
        if (id == R.id.nav_historiales) {
            Intent i = new Intent(MainPasajeroActivity.this, ActivityHistorial.class);
            startActivity(i);
        } else if (id == R.id.nav_log_out) {
            logout();
        } else if (id == R.id.nav_calificar) {
            toolbar.setTitle("Pasajeros por Calificar");
            frag = new ListarCalificarFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();
        } else if (id == R.id.nav_banc) {

            frag = new DatosBancariosFragment();
            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
            fragTansaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pasajero);
        drawer.closeDrawer(GravityCompat.END);

        return true;
    }


    //se solicita el servicio
    public void service() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
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

        PasajeroInterface servicio = retrofit.create(PasajeroInterface.class);
        servicio.getPasajeroProfile(Integer.parseInt(idPersona)).enqueue(new Callback<ResponsePasajeroProfileModel>() {
            @Override
            public void onResponse(Call<ResponsePasajeroProfileModel> call, Response<ResponsePasajeroProfileModel> response) {
                Log.i(TAG, "onResponse: " + response.body().getPersonCorreo());
                if (response.code() == 200) {

                    String nombre = response.body().getPersonNombre();
                    String correo = response.body().getPersonCorreo();

                    View header = navigationView.getHeaderView(0);
                    tvnombre = (TextView) header.findViewById(R.id.username);
                    tvEmail = (TextView) header.findViewById(R.id.email);
                    tvnombre.setText(nombre);
                    tvEmail.setText(correo);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        respuestaServicio(jObjError.getString("message"));
                        Log.e("respuestaServicio", jObjError.getString("message"));
                    } catch (Exception e) {
                        respuestaServicio(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePasajeroProfileModel> call, Throwable t) {

                Log.e("onFailure", t.getMessage());

            }
        });

    }

 /*   protected void agregarServicio(String serFecha, Integer serSolicitante, String serOrigen, String serDestino, Integer serEstado){

        DataAddServicio servicio = new DataAddServicio(serFecha,serSolicitante,serOrigen,serDestino,serEstado,1);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //declaromos la interfaz

        FavoritosInterface interfaceFav = retrofit.create(FavoritosInterface.class);
        interfaceFav.setServio(servicio).enqueue(new Callback<ServicioAddModel>() {
            @Override
            public void onResponse(Call<ServicioAddModel> call, Response<ServicioAddModel> response) {
                if (response.isSuccessful()){

                    Log.i(TAG, "onResponse: ");
                }
                else
                    Log.i(TAG, "NoonResponse: ");
            }

            @Override
            public void onFailure(Call<ServicioAddModel> call, Throwable t) {

                Log.i(TAG, "onFailure: ");

            }
        });


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Solicitud");
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }*/

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();
//                        if (id == R.id.nav_validacion) {
//                            Intent i = new Intent(MainPasajeroActivity.this, ActivityValidacion.class);
//                            startActivity(i);
//                        }
                        if (id == R.id.nav_perfil) {
                            toolbar.setTitle("Perfil");
                            frag = new PerfilPasajeroFragment();
                            frag = PerfilPasajeroFragment.newInstance(idPersona, token);
                            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                            fragTansaction.commit();
                        }
                        if (id == R.id.nav_recarga) {
                            toolbar.setTitle("Recarga de Saldo");
                            frag = new MetodoPagoFragment();
                            frag = MetodoPagoFragment.newInstance(idPersona, token);
                            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                            fragTansaction.commit();
                        }
                        if (id == R.id.nav_tomarImagen) {
                            Intent i = new Intent(MainPasajeroActivity.this, ActivityArchivo.class);
                            startActivity(i);
                        }
                        if (id == R.id.nav_favoritos) {
                            Intent i = new Intent(MainPasajeroActivity.this, FavoritosFragment.class);
                            i.putExtra("key", 0);
                            i.putExtra("token",token);
                            i.putExtra("idUsuario",idPersona);
                            startActivity(i);
                        }
                        if (id == R.id.nav_historiales) {
                            Intent i = new Intent(MainPasajeroActivity.this, ActivityHistorial.class);
                            startActivity(i);
                        } else if (id == R.id.nav_log_out) {
                            logout();
                        } else if (id == R.id.nav_calificar) {
                            toolbar.setTitle("Pasajeros por Calificar");
                            frag = new ListarCalificarFragment();
                            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                            fragTansaction.commit();
                        } else if (id == R.id.nav_banc) {

                            frag = new DatosBancariosFragment();
                            fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                            fragTansaction.commit();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pasajero);
                        drawer.closeDrawer(GravityCompat.START);


                        return true;
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
               // Intent i = new Intent(MainPasajeroActivity.this, SolicitudManualFragment.class);
               // startActivity(i);
               // return true;
                toolbar.setTitle("Solicitud Manual");
                frag = new SolicitudManualFragment();
                frag = SolicitudManualFragment.newInstance();
                fragTansaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipalFragmento, frag);
                fragTansaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(String title) {


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logoutDialog();
            if (respuestaBotonBack) {
                return super.onKeyDown(keyCode, event);
            }
            //logout();
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
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout();
                        respuestaBotonBack = true;
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private void respuestaServicio(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateTitleToolbar(String titleToolbar) {

    }
}
