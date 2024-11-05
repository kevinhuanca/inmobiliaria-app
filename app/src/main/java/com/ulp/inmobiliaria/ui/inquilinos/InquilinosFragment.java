package com.ulp.inmobiliaria.ui.inquilinos;

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
import com.ulp.inmobiliaria.databinding.FragmentInquilinosBinding;
import com.ulp.inmobiliaria.model.Contrato;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel viewModel;
    private FragmentInquilinosBinding binding;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override
            public void onChanged(List<Contrato> contratos) {
                InquilinoAdapter inquilinoAdapter = new InquilinoAdapter(contratos, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvListaInquilinos.setAdapter(inquilinoAdapter);
                binding.rvListaInquilinos.setLayoutManager(gridLayoutManager);
            }
        });

        viewModel.datosContratos();

        return root;
    }

}