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
import com.ulp.inmobiliaria.databinding.FragmentPagosContratoBinding;
import com.ulp.inmobiliaria.model.Pago;

import java.util.List;

public class PagosContratoFragment extends Fragment {

    private PagosContratoViewModel viewModel;
    private FragmentPagosContratoBinding binding;

    public static PagosContratoFragment newInstance() {
        return new PagosContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(PagosContratoViewModel.class);
        binding = FragmentPagosContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter pagoAdapter = new PagoAdapter(pagos, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvListaPagos.setAdapter(pagoAdapter);
                binding.rvListaPagos.setLayoutManager(gridLayoutManager);
            }
        });

        viewModel.rescatarDatos(getArguments());

        return root;
    }

}