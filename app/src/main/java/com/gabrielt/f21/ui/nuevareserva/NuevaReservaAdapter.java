package com.gabrielt.f21.ui.nuevareserva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielt.f21.R;
import com.gabrielt.f21.model.MisReservasView;
import com.gabrielt.f21.model.NuevaReservaView;
import com.gabrielt.f21.ui.misreservas.MisReservasAdapter;

import java.util.List;

public class NuevaReservaAdapter extends RecyclerView.Adapter<NuevaReservaAdapter.ViewHolderNuevaReserva>{

    private List<NuevaReservaView> nuevaReservaLista;
    private LayoutInflater li;

    public NuevaReservaAdapter(List<NuevaReservaView> nuevaReservaLista, LayoutInflater li) {
        this.nuevaReservaLista = nuevaReservaLista;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolderNuevaReserva onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.nueva_reserva_card, parent, false);
        return new NuevaReservaAdapter.ViewHolderNuevaReserva(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNuevaReserva holder, int position) {
        NuevaReservaView itemListaNuevaReserva = nuevaReservaLista.get(position);

        holder.tv_clase_desc_nueva_reserva.setText(itemListaNuevaReserva.getDescripcion());
        //aca tengo que hacer un llamado para consultar cuantas reservas actuales tiene la clase para mostrar lugares disponibles segun max participantes
        holder.tv_disponibles_nueva_reserva.setText("Disponibles: "+itemListaNuevaReserva.getMax_participantes());
        holder.tv_fecha_nueva_reserva.setText(itemListaNuevaReserva.getFecha());
        holder.tv_dia_nuevaReservaLista.setText(itemListaNuevaReserva.getDia());
        holder.tv_hora_nueva_reserva_lista.setText(itemListaNuevaReserva.getHora());


    }

    @Override
    public int getItemCount() {
        return nuevaReservaLista.size();
    }

    public class ViewHolderNuevaReserva extends RecyclerView.ViewHolder{

        TextView tv_clase_desc_nueva_reserva, tv_disponibles_nueva_reserva, tv_fecha_nueva_reserva, tv_dia_nuevaReservaLista, tv_hora_nueva_reserva_lista;

        public ViewHolderNuevaReserva(@NonNull View itemView) {
            super(itemView);
            tv_clase_desc_nueva_reserva = itemView.findViewById(R.id.tv_clase_desc_nueva_reserva);
            tv_disponibles_nueva_reserva = itemView.findViewById(R.id.tv_disponibles_nueva_reserva);
            tv_fecha_nueva_reserva = itemView.findViewById(R.id.tv_fecha_nueva_reserva);
            tv_dia_nuevaReservaLista = itemView.findViewById(R.id.tv_dia_nuevaReservaLista);
            tv_hora_nueva_reserva_lista = itemView.findViewById(R.id.tv_hora_nueva_reserva_lista);

        }
    }



}
