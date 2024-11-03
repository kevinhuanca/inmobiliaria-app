package com.ulp.inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.llamarLogin(
                        binding.etEmail.getText().toString(),
                        binding.etClave.getText().toString()
                );
            }
        });

        binding.btOlvideClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), OlvideClaveActivity.class);
                startActivity(intent);
            }
        });

    }
}