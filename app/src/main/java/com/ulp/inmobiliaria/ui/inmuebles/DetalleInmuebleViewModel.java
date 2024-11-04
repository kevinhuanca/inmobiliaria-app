package com.ulp.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.model.Inmueble;
import com.ulp.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Inmueble> mInmueble;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getMInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void rescatarDatos(Bundle bundle) {
        Inmueble i = bundle.getSerializable("inmueble", Inmueble.class);
        if (i != null) {
            mInmueble.setValue(i);
        }
    }

    public void cambiarDisponible() {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.disponible(
                "Bearer "+ApiClient.getToken(context),
                mInmueble.getValue().getId()
        );

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se pudo cambiar disponible", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}