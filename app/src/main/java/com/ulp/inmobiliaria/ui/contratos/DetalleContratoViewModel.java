package com.ulp.inmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria.model.Contrato;

public class DetalleContratoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Contrato> mContrato;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getMContrato() {
        if (mContrato == null) {
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }

    public void rescatarDatos(Bundle bundle) {
        Contrato c = bundle.getSerializable("contrato", Contrato.class);
        if (c != null) {
            mContrato.setValue(c);
        }
    }

}