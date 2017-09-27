package com.ismcenter.evataxiapp.Services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismcenter.evataxiapp.Actividades.Choferes.MainConductorActivity;
import com.ismcenter.evataxiapp.Actividades.Pasajeros.MainPasajeroActivity;
import com.ismcenter.evataxiapp.Modelos.Notifications.AceptacionDeSolicitudDeServicioAutomatica;
import com.ismcenter.evataxiapp.Modelos.Notifications.CancelacionDeSolicitud;
import com.ismcenter.evataxiapp.Modelos.Notifications.ConductorLlegoAOrigen;
import com.ismcenter.evataxiapp.Modelos.Notifications.Notificacion;
import com.ismcenter.evataxiapp.Modelos.Notifications.SolicitudDeServicioAutomatica;
import com.ismcenter.evataxiapp.Modelos.Notifications.SolicitudDeServicioManual;
import com.ismcenter.evataxiapp.R;

import java.lang.reflect.Type;

/**
 * Created by hector on 10/12/16.
 */
public class EvataxiFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("EVATAXI_D", "From: " + remoteMessage.getFrom());
        Log.d("EVATAXI_D", "Message data payload: " + remoteMessage.getData().getClass());

        String tipoNotif = remoteMessage.getData().get("tipo_de_notificacion");
        Gson gson = new Gson();
        Type type = null;

        switch (tipoNotif) {
            case "SolicitudDeServicioAutomatica":
                type = new TypeToken<SolicitudDeServicioAutomatica>() {
                }.getType();
                break;
            case "SolicitudDeServicioManual":
                type = new TypeToken<SolicitudDeServicioManual>() {
                }.getType();
                break;
            case "AceptacionDeSolicitudDeServicioAutomatica":
                type = new TypeToken<AceptacionDeSolicitudDeServicioAutomatica>() {
                }.getType();
                break;
            case "CancelacionDeSolicitud":
                type = new TypeToken<CancelacionDeSolicitud>() {
                }.getType();
                break;
            case "ConductorLlegoAOrigen":
                type = new TypeToken<ConductorLlegoAOrigen>() {
                }.getType();
                break;
        }

        Notificacion notificacion = (Notificacion) gson.fromJson(gson.toJson(remoteMessage.getData()), type);
        Log.d("EVATAXI_D", "Serializado: " + notificacion.toString());
        showNotification(notificacion);

    }
    private void showNotification(Notificacion notificacion) {
        Intent i = null;
        Log.e("tipo solicitud", notificacion.getTipo());
//        if (notificacion.getTipo().equals("SolicitudDeServicioAutomatica")){
//            i = new Intent(this, MainConductorActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("SolicitudDeServicioAutomatica", notificacion);
//            i.putExtras(bundle);
//
//        }if (notificacion.getTipo().equals("SolicitudDeServicioManual")){
//            i = new Intent(this, MainConductorActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("SolicitudDeServicioManual", notificacion);
//            i.putExtras(bundle);
//        }

        if (notificacion.getTipo().equals("AceptacionDeSolicitudDeServicioAutomatica")){
            i = new Intent(this, MainPasajeroActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("AceptacionDeSolicitudDeServicioAutomatica", notificacion);
            i.putExtras(bundle);

       }
        else
        if (notificacion.getTipo().equals("ConductorLlegoAOrigen")){
            i = new Intent(this, MainPasajeroActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ConductorLlegoAOrigen", notificacion);
            i.putExtras(bundle);

        }
        else{
            i = new Intent(this, MainConductorActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(notificacion.getTipo(), notificacion);
            i.putExtras(bundle);
        }

        if (i!=null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(notificacion.getTitle())
                .setContentText(notificacion.getSubTitle())
                .setSmallIcon(R.drawable.icon_16x16)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_64x64))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}