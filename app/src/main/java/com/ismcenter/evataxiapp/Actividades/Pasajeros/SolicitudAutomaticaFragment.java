package com.ismcenter.evataxiapp.Actividades.Pasajeros;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import com.ismcenter.evataxiapp.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class SolicitudAutomaticaFragment extends Fragment implements Validator.ValidationListener {
    private static final String TAG = "SolicituAuto";
    @BindView(R.id.ivMap)
    ImageView ivMap;
    @NotEmpty(message = "Debe ingresar un origen")
    @BindView(R.id.editTextUbi)
    TextInputEditText editTextUbi;
    @NotEmpty(message = "Debe ingresar un destino")
    @BindView(R.id.editTextDes)
    TextInputEditText editTextDes;
    @BindView(R.id.swMap)
    Switch myswitch;
    private static String idPersona;
    private static String idUsuario;
    private static String token;
    private View view;
    Validator validator;
    private String select = "1";
    private boolean opcion = false;
    private boolean on = false, respuestaBotonBack = false;
    public Location location;
    public double latitude;
    public double longitude;


    public static SolicitudAutomaticaFragment newInstance(String idPersona, String token) {
        SolicitudAutomaticaFragment.idPersona = idPersona;
        SolicitudAutomaticaFragment.token = token;
        return new SolicitudAutomaticaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_solicitud_automatica, container, false);
        validator = new Validator(this);
        validator.setValidationListener(this);
        ButterKnife.bind(this, view);

        // Destectamos cambio de la ubicacion
        editTextUbi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog(getActivity(), new PlaceSearchDialog.LocationNameListener() {
                        @Override
                        public void locationName(String locationName) {
                            editTextUbi.setText(locationName);
                            Log.i(TAG, "locationName: " + editTextUbi.getText().toString());
                        }
                    });
                    placeSearchDialog.show();
                }
                return true;
            }
        });

        // Destectamos cambio del destino
        editTextDes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog(getActivity(), new PlaceSearchDialog.LocationNameListener() {
                        @Override
                        public void locationName(String locationName) {
                            editTextDes.setText(locationName);
                            Log.i(TAG, "locationName2: " + editTextDes.getText().toString());
                        }
                    });
                    placeSearchDialog.show();
                }
                return false;
            }
        });

        //Detectamos cambio del switch
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    on = true;
                    try {
                        getLocation();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    on = false;
                    editTextUbi.getText().clear();
                }
            }
        });
        return view;
    }
    private void respuestaServicio(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    //Iniciamos el mapa
    @OnClick(R.id.ivMap)
    public void IniciarMap(View view) {
        opcion = true;
        validator.validate();
    }
    //Validaciones
    @Override
    public void onValidationSucceeded() {
        if(opcion== false) {
            Toast.makeText(getContext(), "Solicitud hecha", Toast.LENGTH_SHORT).show();
            //  agregarServicio("2016-02-02", Integer.parseInt(idUsuario.toString()), editTextUbi.getText().toString(), editTextDes.getText().toString(), 1 );
            Intent i = new Intent(getContext(), FavoritosFragment.class);
            i.putExtra("key", 1);
            startActivity(i);
        }
        else{
            String origen = editTextUbi.getText().toString();
            String destino = editTextDes.getText().toString();
            Intent i = new Intent(getContext(), ActivityMaps.class);
            i.putExtra("origen", origen);
            i.putExtra("destino", destino);
            i.putExtra("estado", select);
            Log.i(TAG, "onValidationSucceeded: " + idPersona +origen + destino );
            startActivity(i);}
    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void getLocation() throws IOException {
        //Obteneos mis coordenadas
        LocationManager locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        if(location!=null) {
            editTextUbi.setFocusable(false);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.i(TAG, "getLocation: " + latitude + longitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            editTextUbi.setText(city.concat(state).concat(address));
            Log.i(TAG, "onDirectionFinderSuccess: " + latitude + longitude +address +city +state +country +postalCode);
        }
        else{
            respuestaServicio("No hubo respuesta del servidor, intente de nuevo");
            myswitch.setChecked(true);
        }
    }
}