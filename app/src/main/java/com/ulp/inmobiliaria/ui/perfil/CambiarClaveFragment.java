package com.ulp.inmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel viewModel;
    private FragmentCambiarClaveBinding binding;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        binding = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.cambiarClave(
                        binding.etActual.getText().toString(),
                        binding.etNueva.getText().toString(),
                        binding.etRepetida.getText().toString()
                );
            }
        });

        return root;
    }

}