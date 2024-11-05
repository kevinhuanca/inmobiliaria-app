package com.ulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.model.Contrato;
import com.ulp.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Contrato>> mContratos;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Contrato>> getMContrato() {
        if (mContratos == null) {
            mContratos = new MutableLiveData<>();
        }
        return mContratos;
    }

    public void datosContratos() {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<List<Contrato>> llamada = api.alquilado("Bearer "+ApiClient.getToken(context));

        llamada.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()) {
                    mContratos.postValue(response.body());
                } else {
                    Toast.makeText(context, "No hay inmuebles alquilados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}