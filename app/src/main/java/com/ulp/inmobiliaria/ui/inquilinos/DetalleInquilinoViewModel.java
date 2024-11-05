package com.ulp.inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria.model.Inquilino;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Inquilino> mInquilino;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inquilino> getMInquilino() {
        if (mInquilino == null) {
            mInquilino = new MutableLiveData<>();
        }
        return mInquilino;
    }

    public void rescatarDatos(Bundle bundle) {
        Inquilino i = bundle.getSerializable("inquilino", Inquilino.class);
        if (i != null) {
            mInquilino.setValue(i);
        }
    }

}