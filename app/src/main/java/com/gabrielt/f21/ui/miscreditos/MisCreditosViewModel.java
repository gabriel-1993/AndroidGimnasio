package com.gabrielt.f21.ui.miscreditos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.model.CuotaActualView;
import com.gabrielt.f21.model.MisCreditosView;
import com.gabrielt.f21.model.Usuario;
import com.gabrielt.f21.request.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisCreditosViewModel extends AndroidViewModel {

    private MutableLiveData<MisCreditosView> mMisCreditos;
    private Context context;

    public MisCreditosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<MisCreditosView> getmMisCreditos() {
        if(mMisCreditos == null){
            mMisCreditos = new MutableLiveData<MisCreditosView>();
        }
        return mMisCreditos;
    }

    public void obtenerMisCreditos() {

        Call<MisCreditosView> misCreditosCall = ApiClient.getApiF21().misCreditos("Bearer "+ApiClient.leer(context));

        misCreditosCall.enqueue(new Callback<MisCreditosView>() {
            @Override
            public void onResponse(Call<MisCreditosView> call, Response<MisCreditosView> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MisCreditosView respuesta = response.body();
                    List<MisCreditosView.Cuota> listaCuotas = respuesta.getCuotas();
                    // Ordenamos la lista por las fechas
                    Collections.sort(listaCuotas, (credito1, credito2) -> {
                        try {
                            String fechaCredito1 = credito1.getFechaPago();
                            String fechaCredito2 = credito2.getFechaPago();
                            // Comparar las fechas
                            return fechaCredito1.compareTo(fechaCredito2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return 0; // Si hay un error en el parseo, no se ordena
                        }
                    });
                    // Invertir el orden para que las fechas. Mas recientes arriba
                    Collections.reverse(listaCuotas);
                    // Ahora asignamos la lista ordenada e invertida al LiveData
                    mMisCreditos.postValue(response.body());
                }  else if (response.code() == 404) {
                    // Manejar código 404 cuando no se encuentran créditos
                    MisCreditosView respuestaVacia = new MisCreditosView();
                    respuestaVacia.setCuotas(new ArrayList<>()); // Lista vacía si no se encuentran créditos
                    respuestaVacia.setMensaje("No hay créditos disponibles.");
                    mMisCreditos.postValue(respuestaVacia);
                } else {
                    Log.d("SALIDA", "Error en la respuesta: " + response.message());
                    if (response.code() == 401) {
                        Toast.makeText(context, "No autorizado. Verifica tu token.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error de respuesta API: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MisCreditosView> call, Throwable throwable) {
                Toast.makeText(context, "Error de respuesta API GetMisInmuebles", Toast.LENGTH_SHORT).show();
            }
        });


    }



}