package com.gabrielt.f21.ui.misreservas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielt.f21.R;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.MisReservasView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ViewHolderMisReservas>{


    private List<MisReservasView.Reserva> misReservasLista;
    private LayoutInflater li;
    private FechaConverter fechaConverter;

    public MisReservasAdapter(List<MisReservasView.Reserva> misReservasLista, LayoutInflater li) {
        this.misReservasLista = misReservasLista;
        this.li = li;
        fechaConverter = new FechaConverter();
    }

    @NonNull
    @Override
    public MisReservasAdapter.ViewHolderMisReservas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.mis_reservas_card, parent, false);
        return new ViewHolderMisReservas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisReservasAdapter.ViewHolderMisReservas holder, int position) {

        //Si es un usuario nuevo al no tener reservas anteriores, mostrar card personalizada:
        if (misReservasLista.isEmpty()) {
            // Obtener la fecha actual en formato yyyy-MM-dd
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fechaHoy = formatoFecha.format(new Date());
            // Agregar T00:00:00 para cumplir con el formato ISO 8601
            fechaHoy += "T00:00:00";
            // Convertir la fecha actual a formato legible corto usando fechaConverter
            fechaHoy = fechaConverter.convertirFechaLegibleCorta(fechaHoy);
            // Mensaje cuando no hay cuotas
            holder.tv_fecha_hora_reserva.setText("");
            holder.tv_clase_descripcion.setText("Aún no tenés registros de reservas.");
            holder.tv_estado_reserva.setText("");
            return;
        }

        MisReservasView.Reserva reserva = misReservasLista.get(position);

        holder.tv_fecha_hora_reserva.setText( fechaConverter.convertirFechaLegibleCorta(reserva.getFechaClase()));
        holder.tv_clase_descripcion.setText(reserva.getTipoDeClaseDescripcion());
        holder.tv_estado_reserva.setText(reserva.getEstadoReserva());


        switch (reserva.getEstadoReserva().toLowerCase().trim()) {
            case "presente":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.verde, holder.itemView.getContext().getTheme()));
                break;
            case "reservada":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.gris, holder.itemView.getContext().getTheme()));
                break;
            case "ausente":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.white, holder.itemView.getContext().getTheme()));
                break;
            case "cancelada":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.rojo, holder.itemView.getContext().getTheme()));
                break;
            default:
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.gris, holder.itemView.getContext().getTheme()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        //Minimo se va mostrar una card:
        return Math.max(1, misReservasLista.size());
    }

    public class ViewHolderMisReservas extends RecyclerView.ViewHolder{
        TextView tv_fecha_hora_reserva, tv_clase_descripcion, tv_estado_reserva;
        public ViewHolderMisReservas(@NonNull View itemView) {
            super(itemView);
            tv_fecha_hora_reserva = itemView.findViewById(R.id.tv_fecha_nueva_reserva);
            tv_clase_descripcion = itemView.findViewById(R.id.tv_clase_desc_nueva_reserva);
            tv_estado_reserva = itemView.findViewById(R.id.tv_disponibles_nueva_reserva);
        }
    }
}
