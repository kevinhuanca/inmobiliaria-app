package com.ulp.inmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.databinding.FragmentDetalleContratoBinding;
import com.ulp.inmobiliaria.model.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel viewModel;
    private FragmentDetalleContratoBinding binding;
    private int id;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                binding.tvDetalleFechaInicio.setText(contrato.getFechaInicio());
                binding.tvDetalleFechaFin.setText(contrato.getFechaFin());
                binding.tvDetalleMonto.setText(contrato.getMonto()+"");
                binding.tvDetalleInquilino.setText(contrato.getInquilino().getNombre()+" "+contrato.getInquilino().getApellido());
                binding.tvDetalleInmueble.setText(contrato.getInmueble().getDireccion());
                id = contrato.getId();
            }
        });

        binding.btVerPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Navigation.findNavController(view).navigate(R.id.pagosContratoFragment, bundle);
            }
        });

        viewModel.rescatarDatos(getArguments());

        return root;
    }

}