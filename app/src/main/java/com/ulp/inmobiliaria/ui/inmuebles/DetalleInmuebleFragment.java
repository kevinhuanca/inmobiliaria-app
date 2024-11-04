package com.ulp.inmobiliaria.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.ulp.inmobiliaria.model.Inmueble;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel viewModel;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Glide.with(getContext())
                        .load("http://192.168.0.14:5285/in/"+inmueble.getImagen())
                        .placeholder(R.drawable.avatar_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivDetalleImagen);
                binding.tvDetalleDireccion.setText(inmueble.getDireccion());
                binding.tvDetalleAmbientes.setText(inmueble.getAmbientes()+"");
                binding.tvDetalleTipo.setText(inmueble.getTipo());
                binding.tvDetalleUso.setText(inmueble.getUso());
                binding.tvDetallePrecio.setText(inmueble.getPrecio()+"");
                binding.swDetalleDisponible.setChecked(inmueble.isDisponible());
            }
        });

        binding.swDetalleDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.cambiarDisponible();
            }
        });

        viewModel.rescatarDatos(getArguments());

        return root;
    }

}