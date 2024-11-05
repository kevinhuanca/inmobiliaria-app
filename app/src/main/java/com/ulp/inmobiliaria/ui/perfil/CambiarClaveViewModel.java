package com.ulp.inmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mActual;
    private MutableLiveData<String> mNueva;
    private MutableLiveData<String> mRepetida;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMActual() {
        if (mActual == null) {
            mActual = new MutableLiveData<>();
        }
        return mActual;
    }

    public LiveData<String> getMNueva() {
        if (mNueva == null) {
            mNueva = new MutableLiveData<>();
        }
        return mNueva;
    }

    public LiveData<String> getMRepetida() {
        if (mRepetida == null) {
            mRepetida = new MutableLiveData<>();
        }
        return mRepetida;
    }

    public void cambiarClave(String actual, String nueva, String repetida) {
        if (actual.isEmpty() || nueva.isEmpty() || repetida.isEmpty()){
            Toast.makeText(context, "¡Hay campos vacios!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.clave(
                "Bearer "+ApiClient.getToken(context),
                actual, nueva, repetida
        );

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mActual.setValue("");
                    mNueva.setValue("");
                    mRepetida.setValue("");
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}