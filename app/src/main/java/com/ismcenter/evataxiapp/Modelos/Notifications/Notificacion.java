package com.ismcenter.evataxiapp.Modelos.Notifications;

import java.io.Serializable;

/**
 * Created by hector on 15/12/16.
 */

public interface Notificacion extends Serializable {
    String getTipo();
    String getSubTitle();
    String getTitle();
}
