package com.ulp.inmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.model.Inmueble;
import com.ulp.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Inmueble>> mInmuebles;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getMInmuebles() {
        if (mInmuebles == null) {
            mInmuebles = new MutableLiveData<>();
        }
        return mInmuebles;
    }

    public void datosInmuebles() {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.todos("Bearer "+ApiClient.getToken(context));

        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    mInmuebles.postValue(response.body());
                } else {
                    Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}