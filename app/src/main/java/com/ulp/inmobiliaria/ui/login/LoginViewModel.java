package com.ulp.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.MainActivity;
import com.ulp.inmobiliaria.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Boolean> mCerrar;
    private SensorManager sensorManager;
    private EscuchaDeLecturas escuchaDeLecturas;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        escuchaDeLecturas = new EscuchaDeLecturas();
    }

    public LiveData<Boolean> getMCerrar() {
        if (mCerrar == null) {
            mCerrar = new MutableLiveData<>();
        }
        return mCerrar;
    }

    public void llamarLogin(String email, String clave) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> llamada = api.login(email, clave);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.saveToken(context, token);
                    Log.d("tokin", token);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    mCerrar.setValue(true);
                } else {
                    Toast.makeText(context, "Email o clave incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerLecturas() {
        List<Sensor> sensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if (!sensores.isEmpty()) {
            Sensor acelerometro = sensores.get(0);
            sensorManager.registerListener(escuchaDeLecturas, acelerometro, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void pararLecturas() {
        sensorManager.unregisterListener(escuchaDeLecturas);
    }

    public class EscuchaDeLecturas implements SensorEventListener {
        private static final float SHAKE_THRESHOLD = 12.0f;  // Umbral para el movimiento
        private long lastUpdate = 0;
        private float last_x, last_y, last_z;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long currentTime = System.currentTimeMillis();
                // Para evitar que el sensor se dispare muy rápido
                if ((currentTime - lastUpdate) > 100) {
                    long diffTime = currentTime - lastUpdate;
                    lastUpdate = currentTime;

                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];

                    // Cálculo de la magnitud de la aceleración
                    float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                    if (magnitude > SHAKE_THRESHOLD) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:2664553747"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}
