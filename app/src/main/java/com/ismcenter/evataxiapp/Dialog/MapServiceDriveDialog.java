package com.ismcenter.evataxiapp.Dialog;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ismcenter.evataxiapp.R;

import java.util.ArrayList;
import java.util.List;


public class MapServiceDriveDialog extends DialogFragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private List<Marker> originMarkers = new ArrayList<>();
    private GoogleMap mapa;
    static String coordOrigenDialog, coordDestinoDialog;
    private static View view;

    public MapServiceDriveDialog() {
        mapFragment = new SupportMapFragment();
    }

    public static MapServiceDriveDialog newInstance(String coordOrigen, String coordDestino) {
        MapServiceDriveDialog f = new MapServiceDriveDialog();

        coordOrigenDialog = coordOrigen;
        coordDestinoDialog = coordDestino;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.dialog_map_service_drive, container, false);
            //Fragment para el mapa
            SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.mapDialogServiceDrive);
            mapFragment.getMapAsync(this);

            getDialog().setTitle("Servicio Solicitado");

        } catch (InflateException e) {
                    //Fragment para el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.mapDialogServiceDrive);
        mapFragment.getMapAsync(this);

        getDialog().setTitle("Servicio Solicitado");
        }

        return view;




    }

    public SupportMapFragment getFragment() {
        return mapFragment;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        LatLng hcmus = new LatLng(Double.parseDouble(coordDestinoDialog), Double.parseDouble(coordOrigenDialog));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mapa.addMarker(new MarkerOptions()
                .title("Mi posición actual")
                .position(hcmus)));

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
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setMyLocationButtonEnabled(true);


    }

}