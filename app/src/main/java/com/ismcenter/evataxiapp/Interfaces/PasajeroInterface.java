package com.ismcenter.evataxiapp.Interfaces;

import com.ismcenter.evataxiapp.Modelos.DataAddFavoritos;
import com.ismcenter.evataxiapp.Modelos.DataChoferCandidato;
import com.ismcenter.evataxiapp.Modelos.DataPago;
import com.ismcenter.evataxiapp.Modelos.DataZona;
import com.ismcenter.evataxiapp.Modelos.DatosBancariosModel;
import com.ismcenter.evataxiapp.Modelos.FavoritosAddModel;
import com.ismcenter.evataxiapp.Modelos.FavoritosQuitModel;
import com.ismcenter.evataxiapp.Modelos.PagoModel;
import com.ismcenter.evataxiapp.Modelos.Request.RequestCancelarSolicitud;
import com.ismcenter.evataxiapp.Modelos.Request.RequestDeposito;
import com.ismcenter.evataxiapp.Modelos.Request.RequestInstaPago;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudAutomatica;
import com.ismcenter.evataxiapp.Modelos.Request.RequestSolicitudManual;
import com.ismcenter.evataxiapp.Modelos.Request.RequestTransferencia;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseAutocompletar;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseBancos;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseChoferModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseChoferesElejidos;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDestinoByOrigen;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseFavoritosModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryService;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseListQualifiers;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseOrigenByZona;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePasajeroProfileModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseTarifa;
import com.ismcenter.evataxiapp.Modelos.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface PasajeroInterface {

    //obtener perfil pasajero
    @GET("usuario/perfil_pasajero/{usr_id}")
    Call<ResponsePasajeroProfileModel> getPasajeroProfile(@Path("usr_id") int usr_id);

    //lisar favoritos
    @GET("favoritos/listar/{usr_id}")
    Call<List<ResponseFavoritosModel>> getFavoritos(@Path("usr_id") int usr_id);

    //listar choferes disponibles
    @GET("drivers")
    Call<ResponseChoferModel> getChoferes();

    //listar choferes disponibles para agregarlos como favoritos
    @GET("favoritos/listar_candidatos/{usr_id}")
    Call<List<DataChoferCandidato>> getChoferesCandidatos(@Path("usr_id") String usr_id);

    //lisar un chofer
    @GET("drivers/{usr_id}")
    Call<List<ResponseChoferModel>> getChofer(@Path("usr_id") int usr_id);

    //Obtener tarifa actual
    @GET("tarifas/calcular_general")
    Call<ResponseTarifa> getTarifa(@Query("kms") String kms, @Query("hora") String hora, @Query("estado") String estado);//, @Query("id_conductor=") String estatus);

    //Obtener autocompletar zonas
    @GET("zonas/nombre/{inicial}")
    Call<ResponseAutocompletar>getAutocompletar(@Path("inicial") String inicial);

    /* //Obtener destinos dependiendo de una zona
     @GET("zonas/by_origen/{zonaId}")
     Call<List<ResponseDestinos>> getDestinos(@Path("zonaId") int zonaId);*/

    //Obtener choferes dispobibles para calificar
    @GET("drivers/que_han_prestado_servicio_a_un_usuario/{usr_id}")
    Call<List<ResponseChoferesElejidos>> getChoferesDisponiblesACalificar(@Path("usr_id") int usr_id);

    //Obtener historial de solicitudes hechar por un usuario
    @GET("usuario/consultar_historial/{usr_id}")
    Call<List<ResponseHistoryService>> getHistorialSolicitudes(@Path("usr_id") String usr_id);

    //Obtener todas las zonas
    @GET("zonas")
    Call<DataZona> getZonas();

    //Obtener origen a partir de una zona
    @GET("zonas/origen_by_zona/{id_zona}/{inicial}")
    Call<List<ResponseOrigenByZona>> getOrigenByZona(@Path("id_zona") String id_zona, @Path("inicial") String inicial);

    //Obtener destinos a partir de un origen
    @GET("zonas/destino_by_origen")
    Call<List<ResponseDestinoByOrigen>> getDestinoByOrigen(@Query("origen") String origen, @Query("zona_id") String zona_id);

    //Obtenemos los bancos disponibles
    @GET("bancos")
    Call<List<ResponseBancos>> getBancos();


    //Generar solicitud automatica
    @POST("servicio/solicitud_automatica")
    Call<RequestSolicitudAutomatica> setSolicitud(@Body RequestSolicitudAutomatica solicitud);

    //Generar solicitud Mnual
    @POST("servicio/solicitud_manual")
    Call<RequestSolicitudManual> setSolicitudManual(@Body RequestSolicitudManual solicitud);

    //Cancelar solicitud
    @POST("servicio/cancelar_solicitud")
    Call<RequestCancelarSolicitud> setCancelarSolicitud(@Body RequestCancelarSolicitud solicitud);

    //Agregar chofer como favorito
    @POST("favoritos/agregar")
    Call<FavoritosAddModel> setFavoritos(@Body DataAddFavoritos favoritos);

    //Generar deposito
    @POST("pagos/recargar_x_deposito")
    Call<RequestDeposito> setDeposito(@Body RequestDeposito deposito);

    //Generar transferencia
    @POST("pagos/recargar_x_transfencia")
    Call<RequestTransferencia> setTransferencia(@Body RequestTransferencia transferencia);

    //Generar pago por InstaPago
    @POST("payment")
    Call<RequestInstaPago> setInstatPago(@Body RequestInstaPago transferencia);


    //Eliminar un favorito de un usuario
    @DELETE("favoritos/eliminar")
    Call<FavoritosQuitModel> quitarFavoritos(@Query("id_usuario") String idfavoritos, @Query("id_conductor") String id_conductor);//, @Query("id_conductor=") String estatus);

    //Obtener lista por calificar
    @GET("servicio/pendientes_x_calificar/{usr_id}")
    Call<ArrayList<ResponseListQualifiers>> getListarPorCalificar(@Path("usr_id") String usr_id);




    ////////viejos////////////////////
    @Headers({
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI1YjUyYTE4MjNlYmQ1NjMxOGY2MTI0ZWQ3NjRmYWEzNmEwZTM2NmU1ZmNjY2FkZTgyNDg0N2NhMzVlYmQ3OTM3NTJkMDg5ZGY4NGJjYTQwIn0.eyJhdWQiOiIxIiwianRpIjoiMjViNTJhMTgyM2ViZDU2MzE4ZjYxMjRlZDc2NGZhYTM2YTBlMzY2ZTVmY2NjYWRlODI0ODQ3Y2EzNWViZDc5Mzc1MmQwODlkZjg0YmNhNDAiLCJpYXQiOjE0NzY0OTIzNzIsIm5iZiI6MTQ3NjQ5MjM3MiwiZXhwIjo0NjMyMTY1OTcyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.VS-nAO96Y4boUwEvWPCW5xRp-hfoTdj93o6hKmYQylvPOIiVH8GfXiyYQGKLovUCpYy73Y6zhEIOu0g3xif5KlRIikJxtiBYgWnspSXY9tURig1SJ6zCmyycb0egoH-JiRahF9P_HJ-dMiSfpnD7MNE5m8XY2beVO0Qy2jys7OSOVIbAqez3pz5ZzDl8OimSoNkL0QEzxQclUxuUAXBq--ffsBMzKTagsV-CZ44ZG9D0syKNNHYCYyhHwCcuakEC7oGCCyUV_3ylPO0kT7iNgk50vOXLuPzTPFiqopPiycVt_hcKgX5tsl6ey-uBzgQ-C2OPvXovnd_TvAxV4hYaLtj8Rm6IzUdxH6Fsyzk_wo6IRRQfH7S1vszd8OdE8Q9bnUDgzkQ73pnCBzSNAqgJiwKmA__O2RPA0EpNOvrPwq0qJ5fHhsiDsjof6KGBPxkeNTS3KMdtQEWPCwm8g7-t3rU1QuJZVwxV4oE5imbfpRZRQ82tjDFsfxdvEvgEA6kEUGwq0C0ZSUIFybPyxWNaGOaI-3k7WXKf2EYiikJPknssdZyIaP7PhXG9T09pCAzodY-cEWHPDEkKEBIz2eATCn2was_DvQMNn466NktK5261QFWroVKgMTp_gfaAh04_Qu8svP4kw9NcsByuyu9ZP0klNH2Wl95cerS7vlgguBI",
            "Accept: application/jsonDER:"
    })
    //Obtener usuarios
    @GET("users/{person_id}")
    Call<User> getUsers(@Path("person_id") String person_id);
    //Pago - Deposito
    @POST("recargapagos")
    Call<PagoModel> setDeposito(@Body DataPago datoPago);
    //Obtener zonas
    //  @GET("zonas")
    //Call<ZonaModel> getZonas();
    //Obtener datos bancarios
    @Headers({
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI1YjUyYTE4MjNlYmQ1NjMxOGY2MTI0ZWQ3NjRmYWEzNmEwZTM2NmU1ZmNjY2FkZTgyNDg0N2NhMzVlYmQ3OTM3NTJkMDg5ZGY4NGJjYTQwIn0.eyJhdWQiOiIxIiwianRpIjoiMjViNTJhMTgyM2ViZDU2MzE4ZjYxMjRlZDc2NGZhYTM2YTBlMzY2ZTVmY2NjYWRlODI0ODQ3Y2EzNWViZDc5Mzc1MmQwODlkZjg0YmNhNDAiLCJpYXQiOjE0NzY0OTIzNzIsIm5iZiI6MTQ3NjQ5MjM3MiwiZXhwIjo0NjMyMTY1OTcyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.VS-nAO96Y4boUwEvWPCW5xRp-hfoTdj93o6hKmYQylvPOIiVH8GfXiyYQGKLovUCpYy73Y6zhEIOu0g3xif5KlRIikJxtiBYgWnspSXY9tURig1SJ6zCmyycb0egoH-JiRahF9P_HJ-dMiSfpnD7MNE5m8XY2beVO0Qy2jys7OSOVIbAqez3pz5ZzDl8OimSoNkL0QEzxQclUxuUAXBq--ffsBMzKTagsV-CZ44ZG9D0syKNNHYCYyhHwCcuakEC7oGCCyUV_3ylPO0kT7iNgk50vOXLuPzTPFiqopPiycVt_hcKgX5tsl6ey-uBzgQ-C2OPvXovnd_TvAxV4hYaLtj8Rm6IzUdxH6Fsyzk_wo6IRRQfH7S1vszd8OdE8Q9bnUDgzkQ73pnCBzSNAqgJiwKmA__O2RPA0EpNOvrPwq0qJ5fHhsiDsjof6KGBPxkeNTS3KMdtQEWPCwm8g7-t3rU1QuJZVwxV4oE5imbfpRZRQ82tjDFsfxdvEvgEA6kEUGwq0C0ZSUIFybPyxWNaGOaI-3k7WXKf2EYiikJPknssdZyIaP7PhXG9T09pCAzodY-cEWHPDEkKEBIz2eATCn2was_DvQMNn466NktK5261QFWroVKgMTp_gfaAh04_Qu8svP4kw9NcsByuyu9ZP0klNH2Wl95cerS7vlgguBI",
            "Accept: application/jsonDER:"
    })
    @GET("mibancos")
    Call<DatosBancariosModel> getDatosBancarios(@Query("search") String id, @Query("searchFields") String id2);
}