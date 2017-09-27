package com.ismcenter.evataxiapp.Actividades.Pasajeros;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ismcenter.evataxiapp.Adaptador.PlaceAutocompleteAdapter;
import com.ismcenter.evataxiapp.R;

/**
 * Created by Rahul Juneja on 30-10-2015.
 */
public class AlertDialogSolicitud extends Dialog implements View.OnClickListener {

    private AppCompatAutoCompleteTextView locationET;
    private TextInputLayout locationTIL;
    private AppCompatTextView cancelTV;
    private AppCompatTextView okTV;
    private String titulo, subTitulo;
    private TextView title, subTitle;
    private int tipoSolicitud;
    private ImageView iv_icon;

    public GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_WORLD = new LatLngBounds(
            new LatLng(-85, 180), new LatLng(85, -180));

    LocationNameListener locationNameListener;
    Context context;
    private String TAG = "PlaceSearchDialog";


    public AlertDialogSolicitud(String titulo,String subTitulo,int tipoSolicitud,Context context, LocationNameListener locationNameListener) {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.solicitud_picker_dialog);
        this.context = context;
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.tipoSolicitud = tipoSolicitud;
        this.locationNameListener = locationNameListener;
    }

    public interface LocationNameListener {
        public void locationName(String locationName);
    }


    public void init() {

        locationET = (AppCompatAutoCompleteTextView) findViewById(R.id.location_ET);

        cancelTV = (AppCompatTextView) findViewById(R.id.cancelTV);
        okTV = (AppCompatTextView) findViewById(R.id.okTV);

        okTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(locationET, InputMethodManager.SHOW_IMPLICIT);

        locationET.setOnItemClickListener(mAutocompleteClickListener);
        mAdapter = new PlaceAutocompleteAdapter(context, mGoogleApiClient, BOUNDS_WORLD, null);
        locationET.setThreshold(3);
        locationET.setAdapter(mAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            //Hide Keyboard
            InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);
        }
    };


    private void ok() {
        dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (TextView) findViewById(R.id.tv_titulo);
        title.setText(titulo);
        subTitle = (TextView) findViewById(R.id.tv_subTtitle);
        subTitle.setText(subTitulo);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        cancelTV = (AppCompatTextView) findViewById(R.id.cancelTV);
        okTV = (AppCompatTextView) findViewById(R.id.okTV1);

        okTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);

        switch (tipoSolicitud){
            case 0:
                iv_icon.setImageResource(R.drawable.ic_checked);//Aceptacion
                break;
            case 1:
                iv_icon.setImageResource(R.drawable.ic_car_on_driver);//En camino
                break;
            case 2:
                iv_icon.setImageResource(R.drawable.ic_car_arriver);//ha llegado a destino
                break;
            case 3:
                iv_icon.setImageResource(R.drawable.ic_check_sol);//Solicitud hecha
                break;
        }

    }

    @Override
    protected void onStart() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.okTV1) {
            Log.i(TAG, "onClick: ");
            dismiss();
        } else if (view.getId() == R.id.cancelTV) {
            dismiss();
        }
    }

}
