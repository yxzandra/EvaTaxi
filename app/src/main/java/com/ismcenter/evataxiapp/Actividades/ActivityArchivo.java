package com.ismcenter.evataxiapp.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ismcenter.evataxiapp.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityArchivo extends AppCompatActivity {

    private boolean checkIm, checkCd = false;
    private static int TAKE_PICTURE = 1;
    private String name = "";

    @BindView(R.id.rb_cedula)
    RadioButton rbCedula;

    @BindView(R.id.rb_fotografia)
    RadioButton rbFotografia;

    @BindView(R.id.btAceptar)
    Button btAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);
        ButterKnife.bind(this);

        setToolbar();

    }

    @OnClick(R.id.btAceptar)
    public void submit(View view) {
        if (rbCedula.isChecked()) {
            Log.i("hola1", "submit: ");
            checkCd = true;
        }
        if (rbFotografia.isChecked()) {
            Log.i("hola2", "submit: ");

            checkIm = true;
        } else if (checkCd == false || checkIm == false)
            Toast.makeText(getApplicationContext(), "Seleccione una opción", Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.imageButton)
    public void submitImage(View view) {
        takePhoto();

    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRecarga);
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
    /**
     * Metodo para iniciar la camara
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int code = TAKE_PICTURE;
        Uri output = Uri.fromFile(new File(name));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, code);
    }

    /**
     * metodo para solicitar la imagen
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            /**
             * Si se reciben datos en el intent tenemos una vista previa (thumbnail)
             */
            if (data != null) {
                /**
                 * En el caso de una vista previa, obtenemos el extra ÒdataÓ del intent y
                 * lo mostramos en el ImageView
                 */
                if (data.hasExtra("data")) {
                    Log.i("hola5", "onActivityResult: ");
                    ImageView iv = (ImageView)findViewById(R.id.imageButton);
                      iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                }
                /**
                 * De lo contrario es una imagen completa
                 */
            } else {
                /**
                 * A partir del nombre del archivo ya definido lo buscamos y creamos el bitmap
                 * para el ImageView
                 */
                Log.i("hola4", "onActivityResult: ");
                  ImageView iv = (ImageView)findViewById(R.id.imageButton);
                  iv.setImageBitmap(BitmapFactory.decodeFile(name));
                /**
                 * Para guardar la imagen en la galer’a, utilizamos una conexi—n a un MediaScanner
                 */
              /*  new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;

                    {
                        msc = new MediaScannerConnection(getApplicationContext(), this);
                        msc.connect();
                    }

                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };*/
            }
        }
    }
}



