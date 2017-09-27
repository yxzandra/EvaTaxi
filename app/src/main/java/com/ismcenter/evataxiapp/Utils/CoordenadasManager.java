package com.ismcenter.evataxiapp.Utils;

import com.ismcenter.evataxiapp.Modelos.Coordenada;

/**
 * Created by hector on 25/12/16.
 */

public class CoordenadasManager {

    /**
     * Recibe un string y retorna un objeto de tipo Coordenada.
     *
     * @param latitude_longitude
     * @return *                            [LATITUDE ; LONGITUDE]
     * Ej: Si latitude_longitude == "40.712784;-74.005941"
     * Entonces se retorna un objeto Coordenada con
     * latitude  = 40.712784
     * longitude = -74.005941
     */
    public static Coordenada fromString(String latitude_longitude) {
        String[] strCoordList = latitude_longitude.split(";");
        Double lat = Double.parseDouble(strCoordList[0]);
        Double lon = Double.parseDouble(strCoordList[1]);
        return new Coordenada(lat,lon);
    }

    /**
     * Recibe una Latitud y una longitud y retorna un string con el
     * formato "latitud;longitud"
     *
     * @return
     *                              [LATITUDE ; LONGITUDE]
     * Ej: Si latitude=40.712784 y longitude=-74.005941
     * Entonces se retorna un String == "40.712784;-74.005941"
     */
    public static String fromLatLon(Double lat,Double lon){
        return new Coordenada(lat,lon).toString();
    }

    /**
     * Recibe una coordenada y retorna un string con el
     * formato "latitud;longitud"
     *
     * @return
     *                              [LATITUDE ; LONGITUDE]
     * Ej: Si coordenada es un objeto con latitude=40.712784
     *      y longitude=-74.00594
     *      Entonces se retorna un String == "40.712784;-74.005941"
     */
    public static String fromCoord(Coordenada coordenada){
        return coordenada.toString();
    }
}
