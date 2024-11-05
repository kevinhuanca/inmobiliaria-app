package com.ulp.inmobiliaria.ui.contratos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria.R;
import com.ulp.inmobiliaria.model.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolderPago> {
    private List<Pago> pagos;
    private LayoutInflater inflater;

    public PagoAdapter(List<Pago> pagos, LayoutInflater inflater) {
        this.pagos = pagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_pago, parent, false);
        return new ViewHolderPago(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {
        Pago p = pagos.get(position);
        holder.tvNumero.setText(p.getNumero()+"");
        holder.tvFecha.setText(p.getFecha());
        holder.tvImporte.setText(p.getImporte()+"");
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder {
        TextView tvNumero, tvFecha, tvImporte;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);
            tvNumero = itemView.findViewById(R.id.tvCardNumero);
            tvFecha = itemView.findViewById(R.id.tvCardFecha);
            tvImporte = itemView.findViewById(R.id.tvCardImporte);
        }
    }
}
