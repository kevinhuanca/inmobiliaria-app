package com.ulp.inmobiliaria.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<MapaActual> mapaActual;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<MapaActual> getMapaActual() {
        if (mapaActual == null) {
            mapaActual = new MutableLiveData<>();
        }
        return mapaActual;
    }

    public void obtenerMapa() {
        MapaActual mp = new MapaActual();
        mapaActual.setValue(mp);
    }

    public class MapaActual implements OnMapReadyCallback {
        LatLng INMOBILIARIA = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(INMOBILIARIA).title("Inmobiliaria"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INMOBILIARIA, 18));
        }
    }

}