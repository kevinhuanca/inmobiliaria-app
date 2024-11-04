package com.ulp.inmobiliaria.ui.inmuebles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.model.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public InmuebleAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_inmueble, parent, false);
        return new ViewHolderInmueble(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.tvDireccion.setText("Direccion:\n"+inmueble.getDireccion());
        holder.tvTipo.setText("Tipo:\n"+inmueble.getTipo());
        holder.tvPrecio.setText("Precio:\n$"+inmueble.getPrecio()+"");
        Glide.with(holder.itemView)
                .load("http://192.168.0.14:5285/in/"+inmueble.getImagen())
                .placeholder(R.drawable.avatar_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImagen);

        // ACA VA UN LISTENER PARA IR A DETALLES..
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvTipo, tvPrecio;
        ImageView ivImagen;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvCardDireccion);
            tvTipo = itemView.findViewById(R.id.tvCardTipo);
            tvPrecio = itemView.findViewById(R.id.tvCardPrecio);
            ivImagen = itemView.findViewById(R.id.ivCardImagen);
        }
    }

}
