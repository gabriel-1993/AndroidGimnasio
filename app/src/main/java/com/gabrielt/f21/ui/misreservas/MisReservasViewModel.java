package com.gabrielt.f21.ui.misreservas;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.model.MisCreditosView;
import com.gabrielt.f21.model.MisReservasView;
import com.gabrielt.f21.model.MisReservasView.Reserva;
import com.gabrielt.f21.request.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisReservasViewModel extends AndroidViewModel {


    private MutableLiveData<MisReservasView> mMisReservas;
    private Context context;

    public MisReservasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<MisReservasView> getmMisReservas() {
        if (mMisReservas == null) {
            mMisReservas = new MutableLiveData<MisReservasView>();
        }
        return mMisReservas;
    }


    public void obtenerMisReservas() {
        // Recupera el token de la sesión
        String stringToken = "Bearer "+ApiClient.leer(context);

        // Haciendo la llamada a la API para obtener las reservas
        Call<MisReservasView> misReservasCall = ApiClient.getApiF21().obtenerReservas(stringToken);

        misReservasCall.enqueue(new Callback<MisReservasView>() {
            @Override
            public void onResponse(Call<MisReservasView> call, Response<MisReservasView> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MisReservasView respuesta = response.body();
                    List<MisReservasView.Reserva> listaReservas = respuesta.getReservas(); // capturar la lista de reservas

                    // Ordenar la lista de reservas por la fecha 'fechaClase'
                    Collections.sort(listaReservas, (reserva1, reserva2) -> {
                        try {
                            // Obtener las fechas en formato String
                            String fechaReserva1 = reserva1.getFechaClase();
                            String fechaReserva2 = reserva2.getFechaClase();

                            // Comparar las fechas
                            return fechaReserva1.compareTo(fechaReserva2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return 0; // Si hay un error en el parseo, no se ordena
                        }
                    });

                    // Invertir el orden para que las fechas más recientes estén arriba
                    Collections.reverse(listaReservas);

                    // Asignamos la lista ordenada al LiveData
                    mMisReservas.postValue(respuesta);  // Aquí ya se actualiza el LiveData con la lista ordenada
                } else {
                    Log.d("SALIDA", "Error en la respuesta: " + response.message());
                    // Manejo de errores específicos
                    if (response.code() == 401) {
                        Toast.makeText(context, "No autorizado. Verifica tu token.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error de respuesta API: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<MisReservasView> call, Throwable throwable) {
                Log.d("SALIDA", "Error de conexión: " + throwable.getMessage());
                Toast.makeText(context, "Error de conexión con la API", Toast.LENGTH_SHORT).show();
            }
        });
    }

}