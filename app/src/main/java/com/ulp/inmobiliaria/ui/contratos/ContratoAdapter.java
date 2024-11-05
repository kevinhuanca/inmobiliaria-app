package com.ulp.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.model.Contrato;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderContrato> {
    List<Contrato> contratos;
    LayoutInflater inflater;

    public ContratoAdapter(List<Contrato> contratos, LayoutInflater inflater) {
        this.contratos = contratos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_inmueble, parent, false);
        return new ViewHolderContrato(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContrato holder, int position) {
        Contrato c = contratos.get(position);
        holder.tvDireccion.setText("Direccion:\n"+c.getInmueble().getDireccion());
        holder.tvTipo.setText("Fecha de inicio:\n"+c.getFechaInicio());
        holder.tvPrecio.setText("Precio:\n$"+c.getMonto()+"");
        Glide.with(holder.itemView)
                .load("http://192.168.0.14:5285/in/"+c.getInmueble().getImagen())
                .placeholder(R.drawable.avatar_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagen);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", c);
                Navigation.findNavController(view).navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public class ViewHolderContrato extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvTipo, tvPrecio;
        ImageView ivImagen;

        public ViewHolderContrato(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvCardDireccion);
            tvTipo = itemView.findViewById(R.id.tvCardTipo);
            tvPrecio = itemView.findViewById(R.id.tvCardPrecio);
            ivImagen = itemView.findViewById(R.id.ivCardImagen);
        }
    }

}