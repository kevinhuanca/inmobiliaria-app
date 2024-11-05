package com.ulp.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.FragmentContratosBinding;
import com.ulp.inmobiliaria.model.Contrato;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel viewModel;
    private FragmentContratosBinding binding;

    public static ContratosFragment newInstance() {
        return new ContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override
            public void onChanged(List<Contrato> contratos) {
                ContratoAdapter contratoAdapter = new ContratoAdapter(contratos, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvListaContratos.setAdapter(contratoAdapter);
                binding.rvListaContratos.setLayoutManager(gridLayoutManager);
            }
        });

        viewModel.datosContratos();

        return root;
    }

}