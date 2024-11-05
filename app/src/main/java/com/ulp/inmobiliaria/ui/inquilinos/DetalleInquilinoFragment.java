package com.ulp.inmobiliaria.ui.inquilinos;

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
import com.ulp.inmobiliaria.databinding.FragmentDetalleInquilinoBinding;
import com.ulp.inmobiliaria.model.Inquilino;

public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel viewModel;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvDetalleDni.setText(inquilino.getDni());
                binding.tvDetalleNombre.setText(inquilino.getNombre());
                binding.tvDetalleApellido.setText(inquilino.getApellido());
                binding.tvDetalleTelefono.setText(inquilino.getTelefono());
                binding.tvDetalleEmail.setText(inquilino.getEmail());
            }
        });

        viewModel.rescatarDatos(getArguments());

        return root;
    }

}