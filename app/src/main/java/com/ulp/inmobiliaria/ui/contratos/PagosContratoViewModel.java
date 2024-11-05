package com.ulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.model.Pago;
import com.ulp.inmobiliaria.request.ApiClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosContratoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Pago>> mPagos;

    public PagosContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getMPagos() {
        if (mPagos == null) {
            mPagos = new MutableLiveData<>();
        }
        return mPagos;
    }

    public void rescatarDatos(Bundle bundle) {
        int id = bundle.getInt("id");

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Pago>> llamada = api.pagos("Bearer "+ApiClient.getToken(context), id);

        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    mPagos.postValue(response.body());
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
            public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}