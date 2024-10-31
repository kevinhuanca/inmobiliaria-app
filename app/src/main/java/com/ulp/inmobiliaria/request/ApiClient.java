package com.ulp.inmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliaria.model.Propietario;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {

    public static final String URLBASE = "http://192.168.0.14:5285/api/";
    private static SharedPreferences sp;

    public static InmobiliariaService getApiInmobiliaria() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);
    }

    private static SharedPreferences getSharedPreference(Context context){
        if(sp == null){
            sp = context.getSharedPreferences("usuario",0);
        }
        return sp;
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = getSharedPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getString("token", null);
    }

    public interface InmobiliariaService {

        @FormUrlEncoded
        @POST("propietarios/login")
        Call<String> login(@Field("Email") String email, @Field("Clave") String clave);

        @GET("propietarios/perfil")
        Call<Propietario> perfil(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("propietarios/modificar")
        Call<String> modificar(
                @Header("Authorization") String token,
                @Field("Dni") String dni,
                @Field("Nombre") String nombre,
                @Field("Apellido") String apellido,
                @Field("Telefono") String telefono,
                @Field("Email") String email
        );

        @FormUrlEncoded
        @PUT("propietarios/clave")
        Call<String> clave(
                @Header("Authorization") String token,
                @Field("Actual") String actual,
                @Field("Nueva") String nueva,
                @Field("Repetida") String repetida
        );

    }

}
