package com.ismcenter.evataxiapp.Interfaces;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Modelos.ChoferModel;
import com.ismcenter.evataxiapp.Modelos.Request.RequestAcceptService;
import com.ismcenter.evataxiapp.Modelos.Request.RequestChangeStatus;
import com.ismcenter.evataxiapp.Modelos.Request.RequestIdSolicitud;
import com.ismcenter.evataxiapp.Modelos.Request.RequestIdZona;
import com.ismcenter.evataxiapp.Modelos.Request.RequestRateDriver;
import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseDriverProfileModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryAcceptService;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseHistoryService;
import com.ismcenter.evataxiapp.Modelos.Response.ResponsePaymentsMade;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DriverInterface {

    @Headers({
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI1YjUyYTE4MjNlYmQ1NjMxOGY2MTI0ZWQ3NjRmYWEzNmEwZTM2NmU1ZmNjY2FkZTgyNDg0N2NhMzVlYmQ3OTM3NTJkMDg5ZGY4NGJjYTQwIn0.eyJhdWQiOiIxIiwianRpIjoiMjViNTJhMTgyM2ViZDU2MzE4ZjYxMjRlZDc2NGZhYTM2YTBlMzY2ZTVmY2NjYWRlODI0ODQ3Y2EzNWViZDc5Mzc1MmQwODlkZjg0YmNhNDAiLCJpYXQiOjE0NzY0OTIzNzIsIm5iZiI6MTQ3NjQ5MjM3MiwiZXhwIjo0NjMyMTY1OTcyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.VS-nAO96Y4boUwEvWPCW5xRp-hfoTdj93o6hKmYQylvPOIiVH8GfXiyYQGKLovUCpYy73Y6zhEIOu0g3xif5KlRIikJxtiBYgWnspSXY9tURig1SJ6zCmyycb0egoH-JiRahF9P_HJ-dMiSfpnD7MNE5m8XY2beVO0Qy2jys7OSOVIbAqez3pz5ZzDl8OimSoNkL0QEzxQclUxuUAXBq--ffsBMzKTagsV-CZ44ZG9D0syKNNHYCYyhHwCcuakEC7oGCCyUV_3ylPO0kT7iNgk50vOXLuPzTPFiqopPiycVt_hcKgX5tsl6ey-uBzgQ-C2OPvXovnd_TvAxV4hYaLtj8Rm6IzUdxH6Fsyzk_wo6IRRQfH7S1vszd8OdE8Q9bnUDgzkQ73pnCBzSNAqgJiwKmA__O2RPA0EpNOvrPwq0qJ5fHhsiDsjof6KGBPxkeNTS3KMdtQEWPCwm8g7-t3rU1QuJZVwxV4oE5imbfpRZRQ82tjDFsfxdvEvgEA6kEUGwq0C0ZSUIFybPyxWNaGOaI-3k7WXKf2EYiikJPknssdZyIaP7PhXG9T09pCAzodY-cEWHPDEkKEBIz2eATCn2was_DvQMNn466NktK5261QFWroVKgMTp_gfaAh04_Qu8svP4kw9NcsByuyu9ZP0klNH2Wl95cerS7vlgguBI",
            "Accept: application/json:"
    })

    //Obtener choferes
    @GET("drivers")
    Call<ChoferModel> getDrivers();

    //Obtener estatus del conductor
    @GET("drivers/status/{idPersona}")
    Call<JsonElement> getDriverStatus(@Path("idPersona") int idPersona);

    //Cambiar estatus del conductor
    @POST("drivers/cambiar_status")
    Call<JsonElement> changeStatusDriver(@Body RequestChangeStatus requestChangeStatus);

    //Perfil del conductor
    @GET("usuario/perfil_conductor/{usr_id}")
    Call<ResponseDriverProfileModel> driverProfile(@Path("usr_id") int usr_id);

    //Aceptar Solicitud del servicio
    @POST("servicio/aceptar_solicitud")
    Call<JsonElement> acceptService(@Body RequestAcceptService requestAcceptService);

    //Rechazar Solicitud del servicio
    @POST("servicio/rechazar_solicitud")
    Call<JsonElement> declineService(@Body RequestIdSolicitud id_solicitud);

    //Historial de Servicios Realizados
    @GET("drivers/historial_servicios_aceptados/{usr_id}")
    Call<ArrayList<ResponseHistoryAcceptService>> consultHistoryService(@Path("usr_id") int idUsuario);

    //Cancelar Solicitud del servicio
    @POST("servicio/cancelar_solicitud")
    Call<JsonElement> cancelService(@Body RequestIdSolicitud id_solicitud);

    //Cancelar Solicitud del servicio
    @POST("servicio/conductor_esta_en_origen")
    Call<JsonElement> driveInOrigin(@Body RequestIdSolicitud id_solicitud);

    //Calificacion del Chofer
    @POST("servicios/calificar_con_id_servicio")
    Call<JsonElement> rateDriver(@Body RequestRateDriver requestRateDriver);

    //Lista servicios cercanos al chofer
    @POST("servicios/listar_cercanos_coord")
    Call<ArrayList<ResponseNearbyService>> ListNearbyServices(@Body RequestIdZona requestIdZona);

    //Historial de Pagos Realizados
    @GET("pagos/pagos_realizados/{usr_id}")
    Call<ResponsePaymentsMade> paymentsMade(@Path("usr_id") int idUsuario);

    //Historial de Pagos Pendientes
    @GET("pagos/pendientes/{usr_id}")
    Call<ResponsePaymentsMade>pendingPayments(@Path("usr_id") int idUsuario);

}
