package com.ulp.inmobiliaria.ui.perfil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.FragmentPerfilBinding;
import com.ulp.inmobiliaria.model.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        abrirGaleria();

        viewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
                Glide.with(getContext())
                        .load("http://192.168.0.14:5285/av/"+propietario.getAvatar())
                        .placeholder(R.drawable.avatar_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAvatar);
            }
        });

        viewModel.getMAvatar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.ivAvatar.setImageURI(Uri.parse(s));
            }
        });

        viewModel.getMHabilitar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                binding.etDni.setEnabled(b);
                binding.etNombre.setEnabled(b);
                binding.etApellido.setEnabled(b);
                binding.etEmail.setEnabled(b);
                binding.etTelefono.setEnabled(b);
                binding.btGuardarPerfil.setVisibility(b?View.VISIBLE:View.INVISIBLE);
                binding.btEditarPerfil.setVisibility(b?View.INVISIBLE:View.VISIBLE);
            }
        });

        binding.btEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.habilitarBotones();
            }
        });

        binding.btGuardarAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.cambiarAvatar();
            }
        });

        binding.btGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.modificarPropietario(
                        binding.etDni.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString()
                );
            }
        });

        binding.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.cambiarClaveFragment);
            }
        });

        binding.btCambiarAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        viewModel.datosPropietario();

        return root;
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result);
            }
        });
    }

}