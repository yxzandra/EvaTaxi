package com.ismcenter.evataxiapp.Utils;

import android.support.v4.app.ActivityCompat;

import com.ismcenter.evataxiapp.Actividades.PrincipalActivity;

import permissions.dispatcher.PermissionUtils;

public class PermissionsDispatcher  {

    private static final int REQUEST = 1;

    private static final String[] PERMISSIONS = new String[]  {"android.permission.ACCESS_FINE_LOCATION"};

    private PermissionsDispatcher() {}

    public static void showDialogPermissions(PrincipalActivity target) {
        if (!PermissionUtils.hasSelfPermissions(target, PERMISSIONS)) {
            ActivityCompat.requestPermissions(target, PERMISSIONS, REQUEST);
        }
    }

    public static void onRequestPermissionsResult(PrincipalActivity target, int requestCode) {
        switch (requestCode) {
            case REQUEST:
                if (!PermissionUtils.hasSelfPermissions(target, PERMISSIONS)) {
                    target.finish();
                }
                break;
            default:
                break;
        }
    }
}