package com.gabrielt.f21.ui.nuevareserva;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielt.f21.R;
import com.gabrielt.f21.model.ConfirmarNuevaReservaView;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.NuevaReservaView;
import com.gabrielt.f21.request.ApiClient;
import com.gabrielt.f21.ui.perfil.PerfilViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaReservaAdapter extends RecyclerView.Adapter<NuevaReservaAdapter.ViewHolderNuevaReserva> {

    private List<NuevaReservaView.Reserva> reservaLista;
    private LayoutInflater layoutInflater;
    private FechaConverter fechaConverter;
    private Context context;

    private PerfilViewModel perfilViewmodel;

    public NuevaReservaAdapter(List<NuevaReservaView.Reserva> reservaLista, LayoutInflater layoutInflater) {
        this.reservaLista = reservaLista;
        this.layoutInflater = layoutInflater;
        fechaConverter = new FechaConverter();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderNuevaReserva onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.nueva_reserva_card, parent, false);
        return new ViewHolderNuevaReserva(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolderNuevaReserva holder, int position) {
        /*
        NuevaReservaView.Reserva reserva = reservaLista.get(position);

        // Setear los datos en las vistas
        holder.tvClaseDescripcion.setText(reserva.getDescripcionClase());
        holder.tvLugaresDisponibles.setText("Disponibles: " + reserva.getLugaresDisponibles());
        holder.tvFecha.setText(fechaConverter.convertirFechaLegibleCorta(reserva.getFechaClase()+ "T00:00:00"));
        Log.d("59 fecha", reserva.getFechaClase());
        Log.d("60 dia", reserva.getDiaClase());

        holder.tvDia.setText(reserva.getDiaClase());
        holder.tvHora.setText(fechaConverter.formatearHoraSinSegundos(reserva.getHoraClase()));

        // Manejar el clic en la card
        holder.itemView.setOnClickListener(view -> {
            mostrarDialogoConfirmacion(view.getContext(), reserva);
        });
        */

        NuevaReservaView.Reserva reserva = reservaLista.get(position);

        // Setear los datos en las vistas
        holder.tvClaseDescripcion.setText(reserva.getDescripcionClase());
        holder.tvLugaresDisponibles.setText("Disponibles: " + reserva.getLugaresDisponibles());
        holder.tvFecha.setText(fechaConverter.convertirFechaLegibleCorta(reserva.getFechaClase() + "T00:00:00"));
        Log.d("59 fecha", reserva.getFechaClase());

        // Convertir la fecha a día de la semana
        String diaSemana = "Desconocido";
        if (reserva.getFechaClase() != null && !reserva.getFechaClase().isEmpty()) {
            LocalDate localDate = LocalDate.parse(reserva.getFechaClase());
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            diaSemana = dayOfWeek.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        }

        Log.d("60 dia", diaSemana);
        holder.tvDia.setText(diaSemana);
        holder.tvHora.setText(fechaConverter.formatearHoraSinSegundos(reserva.getHoraClase()));

        // Manejar el clic en la card
        holder.itemView.setOnClickListener(view -> {
            mostrarDialogoConfirmacion(view.getContext(), reserva);
        });

    }

    @Override
    public int getItemCount() {
        return reservaLista.size();
    }

    public static class ViewHolderNuevaReserva extends RecyclerView.ViewHolder {

        TextView tvClaseDescripcion, tvLugaresDisponibles, tvFecha, tvDia, tvHora;

        public ViewHolderNuevaReserva(@NonNull View itemView) {
            super(itemView);
            tvClaseDescripcion = itemView.findViewById(R.id.tv_clase_desc_nueva_reserva);
            tvLugaresDisponibles = itemView.findViewById(R.id.tv_disponibles_nueva_reserva);
            tvFecha = itemView.findViewById(R.id.tv_fecha_nueva_reserva);
            tvDia = itemView.findViewById(R.id.tv_dia_nuevaReservaLista);
            tvHora = itemView.findViewById(R.id.tv_hora_nueva_reserva_lista);
        }
    }

    private void mostrarDialogoConfirmacion(Context context, NuevaReservaView.Reserva reserva) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Confirmar Reserva")
                .setMessage("¿Deseas reservar la clase de '" + reserva.getDescripcionClase() + "' el " +reserva.getDiaClase() +" a las "+
                        fechaConverter.formatearHoraSinSegundos(reserva.getHoraClase()) + "?")
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    // Acción para confirmar la reserva
                   // Toast.makeText(context, "Reserva confirmada", Toast.LENGTH_SHORT).show();
                    Call<ConfirmarNuevaReservaView> llamada = ApiClient.getApiF21().nuevaReserva("Bearer "+ApiClient.leer(context), reserva.getId());
                    llamada.enqueue(new Callback<ConfirmarNuevaReservaView>() {
                        @Override
                        public void onResponse(Call<ConfirmarNuevaReservaView> call, Response<ConfirmarNuevaReservaView> response) {
                            if (response.isSuccessful()) {
                                ConfirmarNuevaReservaView reservaResponse = response.body();
                                if (reservaResponse != null) {

                                        // No hay error, continuar con el flujo exitoso
                                        System.out.println("Reserva creada exitosamente: " + reservaResponse.getMensaje());
                                       //Toast: Recerva creada exitosamente
                                        Toast.makeText(context, reservaResponse.getMensaje() , Toast.LENGTH_SHORT).show();
                                    }

                            } else {
                                Toast.makeText(context, "Error en la respuesta HTTP: " + response.message() , Toast.LENGTH_SHORT).show();
//                                System.out.println("Error en la respuesta HTTP: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ConfirmarNuevaReservaView> call, Throwable t) {
                            Toast.makeText(context, "Fallo en la llamada: " + t.getMessage() , Toast.LENGTH_SHORT).show();
                            //System.out.println("Fallo en la llamada: " + t.getMessage());
                        }
                    });

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Acción para cancelar
                    Toast.makeText(context, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .show();
    }


}
