package com.ulp.inmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria.model.Propietario;
import com.ulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> mPropietario;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public void datosPropietario() {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<Propietario> llamada = api.perfil("Bearer "+ApiClient.getToken(context));

        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.setValue(response.body());
                } else {
                    Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modificarPropietario(String dni, String nombre, String apellido, String telefono, String email) {
        if (dni.isEmpty() || apellido.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()){
            Toast.makeText(context, "¡Hay campos vacios!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.modificar(
                "Bearer "+ApiClient.getToken(context),
                dni, nombre, apellido, telefono, email
        );

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se modifico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }



}