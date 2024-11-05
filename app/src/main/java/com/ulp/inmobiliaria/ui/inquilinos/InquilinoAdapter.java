package com.ulp.inmobiliaria.ui.inquilinos;

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

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolderInquilino> {
    List<Contrato> contratos;
    LayoutInflater inflater;

    public InquilinoAdapter(List<Contrato> contratos, LayoutInflater inflater) {
        this.contratos = contratos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInquilino onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_inmueble, parent, false);
        return new ViewHolderInquilino(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInquilino holder, int position) {
        Contrato c = contratos.get(position);
        holder.tvDireccion.setText("Direccion:\n"+c.getInmueble().getDireccion());
        holder.tvTipo.setText("Tipo:\n"+c.getInmueble().getTipo());
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
                bundle.putSerializable("inquilino", c.getInquilino());
                Navigation.findNavController(view).navigate(R.id.detalleInquilinoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public class ViewHolderInquilino extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvTipo, tvPrecio;
        ImageView ivImagen;

        public ViewHolderInquilino(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvCardDireccion);
            tvTipo = itemView.findViewById(R.id.tvCardTipo);
            tvPrecio = itemView.findViewById(R.id.tvCardPrecio);
            ivImagen = itemView.findViewById(R.id.ivCardImagen);
        }
    }

}
