package com.ismcenter.evataxiapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yxzan on 10/12/2016.
 */

public class PreferenceActualService {

    private static final String KEY_USER_SERVICE = "USER_ID_SERVICE";
    private static final String KEY_PERSON_SERVICE = "PERSON_ID_SERVICE";
    private static final String KEY_SERVICE_SERVICE = "SERVICE_ID_SERVICE";
    private static final String KEY_NAME_USER = "NAME_USER_SERVICE";
    private static final String KEY_TELEPHONE_USER = "TELEPHONE_USER_SERVICE";
    private static final String KEY_COORD_ORIGEN = "COORD_ORIGEN_SERVICE";
    private static final String KEY_COORD_DESTIN = "COORD_DESTIN_SERVICE";
    private static final String KEY_IS_FAVORITO = "IS_FAVORITO_SERVICE";
    private static final String KEY_ACCEPT_SERVICE = "ACCEPT_SERVICE";
    private static final String KEY_TYPE_SERVICE = "TYPE_SERVICE";

    private final String TAG = PreferenceActualService.class.getSimpleName();

    public SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String PREFER_NAME = "ValidarNotificacionesPush";


    // Constructor
    public PreferenceActualService(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void clearPreferences(){
        setUserServicePassenger("null");
        setPersonServicePassenger("null");
        setKeyServiceId("null");
        setKeyNameUser("null");
        setKeyTelephoneUser("null");
        setKeyCoordOrigen("null");
        setKeyCoordDestin("null");
        setKeyIsFavorito("null");
        setKeyAcceptService("null");
        setKeyTypeService("null");
    }

    public String getUserServicePassenger() {return pref.getString(KEY_USER_SERVICE, "null");}

    public void setUserServicePassenger(String userId){
        editor.putString(KEY_USER_SERVICE, userId);
        editor.apply();
        editor.commit();
    }

    public String getPersonServicePassenger() { return pref.getString(KEY_PERSON_SERVICE, "null");  }

    public void setPersonServicePassenger(String personID) {
        editor.putString(KEY_PERSON_SERVICE, personID);
        editor.apply();
        editor.commit();
    }

    public String getKeyServiceId() {return pref.getString(KEY_SERVICE_SERVICE, "null");}

    public void setKeyServiceId(String serviceId){
        editor.putString(KEY_SERVICE_SERVICE, serviceId);
        editor.apply();
        editor.commit();
    }

    public String getKeyNameUser() { return pref.getString(KEY_NAME_USER, "null");  }

    public void setKeyNameUser(String nameUser) {
        editor.putString(KEY_NAME_USER, nameUser);
        editor.apply();
        editor.commit();
    }

    public String getKeyTelephoneUser() {return pref.getString(KEY_TELEPHONE_USER, "null");}

    public void setKeyTelephoneUser(String telephoneUser){
        editor.putString(KEY_TELEPHONE_USER, telephoneUser);
        editor.apply();
        editor.commit();
    }

    public String getKeyCoordOrigen() {return pref.getString(KEY_COORD_ORIGEN, "null");}

    public void setKeyCoordOrigen(String coordOrigen){
        editor.putString(KEY_COORD_ORIGEN, coordOrigen);
        editor.apply();
        editor.commit();
    }
    public String getKeyCoordDestin() {return pref.getString(KEY_COORD_DESTIN, "null");}

    public void setKeyCoordDestin(String coordDestin){
        editor.putString(KEY_COORD_DESTIN, coordDestin);
        editor.apply();
        editor.commit();
    }

    public String getKeyIsFavorito() {return pref.getString(KEY_IS_FAVORITO, "null");}

    public void setKeyIsFavorito(String favoritoId){
        editor.putString(KEY_IS_FAVORITO, favoritoId);
        editor.apply();
        editor.commit();
    }

    public String getKeyAcceptService() {return pref.getString(KEY_ACCEPT_SERVICE, "null");}

    public void setKeyAcceptService(String aceptService){
        editor.putString(KEY_ACCEPT_SERVICE, aceptService);
        editor.apply();
        editor.commit();
    }

    public String getKeyTypeService() {return pref.getString(KEY_TYPE_SERVICE, "null");}

    public void setKeyTypeService(String typeService){
        editor.putString(KEY_TYPE_SERVICE, typeService);
        editor.apply();
        editor.commit();
    }

}
