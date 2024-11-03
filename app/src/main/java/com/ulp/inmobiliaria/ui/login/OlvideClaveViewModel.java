package com.ulp.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ulp.inmobiliaria.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlvideClaveViewModel extends AndroidViewModel {

    private Context context;

    public OlvideClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void recuperarClave(String email) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Escribi tu email..", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.email(email);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Te enviamos un correo!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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

            }
        });
    }

}
