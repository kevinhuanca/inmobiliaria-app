package com.ulp.inmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mImagen;
    private MutableLiveData<Boolean> mVolver;

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMImagen(){
        if(mImagen == null){
            mImagen = new MutableLiveData<>();
        }
        return mImagen;
    }

    public LiveData<Boolean> getMVolver(){
        if(mVolver == null){
            mVolver = new MutableLiveData<>();
        }
        return mVolver;
    }

    public void guardarInmueble(String direccion, String ambientes, String tipo, String uso, String precio) {
        if (!mImagen.isInitialized()) {
            Toast.makeText(context, "¡Elegi una imagen!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (direccion.isEmpty() || ambientes.isEmpty() || tipo.isEmpty() || uso.isEmpty() || precio.isEmpty()){
            Toast.makeText(context, "¡Hay campos vacios!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.agregar(
                "Bearer "+ApiClient.getToken(context),
                direccion, Integer.parseInt(ambientes), tipo, uso, Integer.parseInt(precio), false
        );

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    guardarImagen(Integer.parseInt(response.body()));
                    mVolver.setValue(true);
                    Toast.makeText(context, "Inmueble agregado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No se pudo guardar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarImagen(int id) {
        Uri imagenUri = Uri.parse(mImagen.getValue());
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imagenUri);
            File file = new File(context.getCacheDir(), "IMAGEN.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id));
            MultipartBody.Part imagen = MultipartBody.Part.createFormData("imagen", file.getName(), requestBody);

            ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
            Call<String> llamada = api.imagen("Bearer "+ApiClient.getToken(context), imagen, requestId);

            llamada.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        // Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
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

        } catch (Exception e) {
            Toast.makeText(context, "Error: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void recibirFoto(ActivityResult result) {
        if(result.getResultCode() == RESULT_OK){
            Intent data =result.getData();
            Uri uri = data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mImagen.setValue(uri.toString());
        }
    }

}