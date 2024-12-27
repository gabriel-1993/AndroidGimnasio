package com.gabrielt.f21.ui.misreservas;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielt.f21.R;
import com.gabrielt.f21.model.CancelarReservaView;
import com.gabrielt.f21.model.ConfirmarNuevaReservaView;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.MisReservasView;
import com.gabrielt.f21.model.NuevaReservaView;
import com.gabrielt.f21.request.ApiClient;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisReservasAdapter extends RecyclerView.Adapter<MisReservasAdapter.ViewHolderMisReservas>{

    private List<MisReservasView.Reserva> misReservasLista;
    private LayoutInflater li;
    private FechaConverter fechaConverter;

    private Context context;

    public MisReservasAdapter(List<MisReservasView.Reserva> misReservasLista, LayoutInflater li) {
        this.misReservasLista = misReservasLista;
        this.li = li;
        this.context = context;
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

            //Modificar estilos de Reservas vacia para tener los mismos que en Creditos vacios
            // Mensaje cuando no hay cuotas
            holder.tv_fecha_reserva.setText(fechaHoy);
            holder.tv_fecha_reserva.setTextSize(16);
            holder.tv_fecha_reserva.setTextColor(Color.YELLOW);

            holder.tv_clase_descripcion.setText("");
            holder.tv_clase_descripcion.setText("No se encontraron");
            holder.tv_clase_descripcion.setTextSize(18);
            holder.tv_clase_descripcion.setTextColor(Color.WHITE);

            holder.tv_estado_reserva.setText("Reservas.");
            holder.tv_estado_reserva.setTextSize(18);
            holder.tv_estado_reserva.setTextColor(Color.WHITE);


            return;
        }

        MisReservasView.Reserva reserva = misReservasLista.get(position);

        holder.tv_fecha_reserva.setText( fechaConverter.convertirFechaLegibleCorta(reserva.getFechaClase()+ "T00:00:00"));
        holder.tv_dia_reserva.setText( reserva.getHoraClase().getDia());
        holder.tv_hora_reserva.setText( fechaConverter.formatearHoraSinSegundos(reserva.getHoraClase().getHora()));
        holder.tv_clase_descripcion.setText(reserva.getTipoDeClaseDescripcion());
        holder.tv_estado_reserva.setText(reserva.getEstadoReserva());


        switch (reserva.getEstadoReserva().toLowerCase().trim()) {
            case "presente":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.verde, holder.itemView.getContext().getTheme()));
                break;
            case "reservada":
                holder.tv_estado_reserva.setTextColor(holder.itemView.getResources().getColor(R.color.white, holder.itemView.getContext().getTheme()));
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

        //cancelar reserva
        // Manejar el clic en la card
        holder.itemView.setOnClickListener(view -> {
           mostrarDialogoConfirmacion(view.getContext(), reserva);
            //Log.d("fecha", reserva.getFechaClase());
            //Log.d("hora", reserva.getHoraClase().getHora());
        });


    }

    @Override
    public int getItemCount() {
        //Minimo se va mostrar una card:
        return Math.max(1, misReservasLista.size());
    }

    public class ViewHolderMisReservas extends RecyclerView.ViewHolder{
        TextView tv_fecha_reserva, tv_hora_reserva, tv_dia_reserva, tv_clase_descripcion, tv_estado_reserva;
        public ViewHolderMisReservas(@NonNull View itemView) {
            super(itemView);
            tv_fecha_reserva = itemView.findViewById(R.id.tv_fecha_nueva_reserva);
            tv_hora_reserva = itemView.findViewById(R.id.tv_hora_nueva_reserva_lista);
            tv_dia_reserva = itemView.findViewById(R.id.tv_dia_nuevaReservaLista);
            tv_clase_descripcion = itemView.findViewById(R.id.tv_clase_desc_nueva_reserva);
            tv_estado_reserva = itemView.findViewById(R.id.tv_disponibles_nueva_reserva);
        }
    }

    private void mostrarDialogoConfirmacion(Context context, MisReservasView.Reserva reserva) {
        if(reserva.getEstadoReserva().equals("Cancelada")){
            Toast.makeText(context, "La reserva ya fue cacelada.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Parsear los strings de fecha y hora
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            Date fechaReserva = sdfFecha.parse(reserva.getFechaClase().trim());
            Date horaReserva = sdfHora.parse(reserva.getHoraClase().getHora().trim());

            // Obtener la fecha y hora actual
            Calendar ahora = Calendar.getInstance();
            Date fechaActual = sdfFecha.parse(sdfFecha.format(ahora.getTime()));
            Date horaActual = sdfHora.parse(sdfHora.format(ahora.getTime()));

            boolean puedeCancelar = false;

            // Verificar si la fecha es futura o si es hoy pero con suficiente margen de tiempo

            if (fechaReserva.after(fechaActual)){
                puedeCancelar = true; // Fecha futura
            } else if (fechaReserva.equals(fechaActual)) {
                // La fecha es hoy, verificar la hora
                Calendar horaReservaCal = Calendar.getInstance();
                horaReservaCal.setTime(horaReserva);

                Calendar horaActualCal = Calendar.getInstance();
                horaActualCal.setTime(horaActual);
                horaActualCal.add(Calendar.HOUR_OF_DAY, 1); // Agregar una hora de margen

                if (horaReservaCal.after(horaActualCal)) {
                    puedeCancelar = true; // Horario futuro con al menos 1 hora de margen
                }
            }

            if (puedeCancelar) {
               //  Mostrar el diálogo de confirmación
                new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setTitle("Cancelar Reserva")
                        .setMessage("¿Deseas cancelar la clase de '" + reserva.getTipoDeClaseDescripcion() + "' el " +
                                reserva.getFechaClase() + " a las " +
                                fechaConverter.formatearHoraSinSegundos(reserva.getHoraClase().getHora()) + "?")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            // Acción para cancelar la reserva
                            Call<CancelarReservaView> llamada = ApiClient.getApiF21().cancelarReserva("Bearer " + ApiClient.leer(context), reserva.getId());

                            llamada.enqueue(new Callback<CancelarReservaView>() {
                                @Override
                                public void onResponse(Call<CancelarReservaView> call, Response<CancelarReservaView> response) {
                                    Toast.makeText(context, "Reserva cancelada con exito.", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<CancelarReservaView> call, Throwable throwable) {
                                    Toast.makeText(context, "No fue posible cancelar la reserva.", Toast.LENGTH_SHORT).show();

                                }
                            });
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .show();

            } else {
                Toast.makeText(context, "Puedes cancelar hasta 1 hora de antes de la clase.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            // Manejar errores de parseo
            Toast.makeText(context, "Error al procesar la fecha u hora de la reserva.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
