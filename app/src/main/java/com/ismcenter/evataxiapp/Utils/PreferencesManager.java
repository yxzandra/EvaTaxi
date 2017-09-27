package com.ismcenter.evataxiapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yxzan on 10/12/2016.
 */

public class PreferencesManager {

    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_PERSON_ID = "PERSON_ID";
    private static final String KEY_SERVER_TOKEN = "SERVER_TOKEN";
    private static final String KEY_USER_TYPE = "USER_TYPE";
    private static final String KEY_SOLICITUD_TYPE = "SOLICITUD_TYPE";

    private final String TAG = PreferencesManager.class.getSimpleName();

    public SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String PREFER_NAME = "ValidarNotificacionesPush";


    // Constructor
    public PreferencesManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public void clearPreferences(){
        setUserId("");
        setPersonID("");
        setServerToken("");
        setUserType("");
        setSolicitudId("");

    }

    public String getUserType() {return pref.getString(KEY_USER_TYPE, "null");}

    public void setUserType(String userType){
        editor.putString(KEY_USER_TYPE, userType);
        editor.apply();
        editor.commit();
    }

    public String getUserId() {return pref.getString(KEY_USER_ID, "null");}

    public void setUserId(String userId){
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
        editor.commit();
    }

    public String getPersonID() { return pref.getString(KEY_PERSON_ID, "null");  }

    public void setPersonID(String personID) {
        editor.putString(KEY_PERSON_ID, personID);
        editor.apply();
        editor.commit();
    }

    public String getServerToken() {return pref.getString(KEY_SERVER_TOKEN, "null");}

    public void setServerToken(String token){
        editor.putString(KEY_SERVER_TOKEN, token);
        editor.apply();
        editor.commit();
    }

    public String getSolicitudId() {return pref.getString(KEY_SOLICITUD_TYPE, "null");}

    public void setSolicitudId(String solicitudType){
        editor.putString(KEY_SOLICITUD_TYPE, solicitudType);
        editor.apply();
        editor.commit();
    }
}
