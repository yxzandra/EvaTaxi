package com.ismcenter.evataxiapp.Interfaces;

import com.google.gson.JsonElement;
import com.ismcenter.evataxiapp.Modelos.DataBanco;
import com.ismcenter.evataxiapp.Modelos.DataDatosBancarios;
import com.ismcenter.evataxiapp.Modelos.Request.ChangePasswordModel;
import com.ismcenter.evataxiapp.Modelos.Request.LoginUserModel;
import com.ismcenter.evataxiapp.Modelos.Request.RequestOlvidoClave;
import com.ismcenter.evataxiapp.Modelos.Request.RequestRegisterUserModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseBankAccounts;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseChangePasswordModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseLoginUserModel;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserMagnagementIterface {

    //Registrar usuario
    @Headers({
            "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWN0b3JAZ21haWwuY29tIiwiaXNzIjoiaHR0cDpcL1wvMTkyLjE2OC4xLjEwXC9ldmF0YXhpXC9hcGlcL3B1YmxpY1wvbG9naW4iLCJpYXQiOjE0Nzk4NzA4MTgsImV4cCI6MTQ3OTg3NDQxOCwibmJmIjoxNDc5ODcwODE4LCJqdGkiOiIwNzZjN2IzMmRjODQyYjAwMTYxY2VhNWY5NzgzOTkxMSJ9.JGm7KaPfrA_50NcezZh9Tv_NORedeHApXTSTplqGE4s"
    })
    @POST("users")
    Call<ResponseUserModel> createUser(@Body RequestRegisterUserModel user);

    //Loguear usuario
    @POST("login")
    Call<ResponseLoginUserModel> loginUser(@Body LoginUserModel loginUserModeluser);

    //Cambiar Clave
    @POST("users/changepassword")
    Call<ResponseChangePasswordModel> changePassword(@Body ChangePasswordModel changePasswordModel);

    //Enviar correo al usuario con contraseña temporal
    @Headers({
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWN0b3JAZ21haWwuY29tIiwiaXNzIjoiaHR0cDpcL1wvMTkyLjE2OC4xLjEwXC9ldmF0YXhpXC9hcGlcL3B1YmxpY1wvbG9naW4iLCJpYXQiOjE0Nzk4NzA4MTgsImV4cCI6MTQ3OTg3NDQxOCwibmJmIjoxNDc5ODcwODE4LCJqdGkiOiIwNzZjN2IzMmRjODQyYjAwMTYxY2VhNWY5NzgzOTkxMSJ9.JGm7KaPfrA_50NcezZh9Tv_NORedeHApXTSTplqGE4s"
    })
    @POST("usuario/olvido_clave")
    Call<JsonElement>forgetKey(@Body RequestOlvidoClave requestOlvidoClave);

    //Lista los datos bancarios por usuario
    @GET("datosbancarios/{usr_id}")
    Call<List<ResponseBankAccounts>>bankAccounts(@Path("usr_id") int usr_id);

    //registra los datos bancarios
    @POST("datosbancarios")
    Call<JsonElement>createBankAccounts(@Body DataDatosBancarios dataDatosBancarios);

    //Obtiene todos los bancos de eva taxi
    @GET("bancos")
    Call<List<DataBanco>>getAllBank();

    //Elimina un dato dancario
    @DELETE("datosbancarios/{usr_id}/{dab_id}")
    Call<JsonElement>deleteBankAccounts(@Path("usr_id") int usr_id, @Path("dab_id") String dab_id);

    //Actualiza un dato bancario
    @PUT("datosbancarios")
    Call<JsonElement> modifyBankAccounts(@Query("dab_id") int dab_id,
                                         @Query("dab_banco") String dab_banco,
                                         @Query("dab_cuenta") String dab_cuenta,
                                         @Query("dab_tipoCuenta") int dab_tipoCuenta,
                                         @Query("dab_nombre") String dab_nombre);
}
