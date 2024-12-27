package com.gabrielt.f21.ui.inicio;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.model.CuotaActualView;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.MisCreditosView;
import com.gabrielt.f21.model.NovedadView;
import com.gabrielt.f21.model.ProximaReservaView;
import com.gabrielt.f21.model.Usuario;
import com.gabrielt.f21.request.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<CuotaActualView> mCuotaActual;
    private MutableLiveData<CuotaActualView> mCuotaVencida;
    private MutableLiveData<ProximaReservaView> mProximaReserva;
    private MutableLiveData<String> getmProximaReservaNoExiste;
    private CuotaActualView cuota;
    private ProximaReservaView proximaReserva;
    private MutableLiveData<List<NovedadView>> mListaNovedades;
    private FechaConverter fechaConverter;
    private Context context;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        fechaConverter = new FechaConverter();
        cuota = new CuotaActualView();
        proximaReserva = new ProximaReservaView();
    }

    public MutableLiveData<List<NovedadView>> getmListaNovedades() {
        if(mListaNovedades == null){
            mListaNovedades = new MutableLiveData<List<NovedadView>>();
        }
        return mListaNovedades;
    }

    public MutableLiveData<CuotaActualView> getmCuotaActual() {
        if (mCuotaActual == null) {
            mCuotaActual = new MutableLiveData<CuotaActualView>();
        }
        return mCuotaActual;
    }

    public MutableLiveData<CuotaActualView> getmCuotaVencida() {
        if (mCuotaVencida == null) {
            mCuotaVencida = new MutableLiveData<CuotaActualView>();
        }
        return mCuotaVencida;
    }

    public MutableLiveData<ProximaReservaView> getmProximaReserva() {
        if (mProximaReserva == null) {
            mProximaReserva = new MutableLiveData<ProximaReservaView>();
        }
        return mProximaReserva;
    }

    public MutableLiveData<String> getmProximaReservaNoExiste() {
        if (getmProximaReservaNoExiste == null) {
            getmProximaReservaNoExiste = new MutableLiveData<>();
        }
        return getmProximaReservaNoExiste;
    }

    /*Verificar cuota: encurso/vencida y creditosDisponibles con el id del token. */
    public void consultarEstadoCuota() {
        Call<CuotaActualView> cuotaCreditosDisponibles = ApiClient.getApiF21().obtenerCuotaActual("Bearer " + ApiClient.leer(context));

        cuotaCreditosDisponibles.enqueue(new Callback<CuotaActualView>() {
            @Override
            public void onResponse(Call<CuotaActualView> call, Response<CuotaActualView> response) {
                if (response.isSuccessful()) {

                    cuota = response.body();
                    Log.d("API_RESPONSE", "creditosDisponibles: " + cuota.getCreditosDisponibles());
                    if (cuota.getMensaje().equals("cuota en curso encontrada.")) {
                        String fechaLegible = fechaConverter.convertirFechaLegibleCorta(cuota.getFechaVencimiento() + "T00:00:00");
                        cuota.setFechaVencimiento(fechaLegible);
                        mCuotaActual.postValue(cuota);
                    } else {
                        // Si no se encontraron cuotas, mostrar el mensaje correspondiente
                        cuota.setCreditosDisponibles("0");
                        cuota.setFechaVencimiento("");
                        cuota.setMensaje("No hay cuotas vigentes.");
                        mCuotaVencida.postValue(cuota);
                    }
                } else if (response.code() == 404) {
                    // Manejar código 404 cuando no se encuentran cuotas vigentes
                    Log.e("API_ERROR", "Código 404: No hay cuotas vigentes.");
                    cuota = new CuotaActualView();
                    cuota.setCreditosDisponibles("0");
                    cuota.setFechaVencimiento("");
                    cuota.setMensaje("No hay cuotas vigentes.");
                    mCuotaVencida.postValue(cuota);
                } else {
                    // Manejar otras respuestas HTTP no exitosas
                    Log.e("API_ERROR", "Código: " + response.code() + " Mensaje: " + response.message());
                    Toast.makeText(context, "Error: No se pudo obtener la cuota actual (Código: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CuotaActualView> call, Throwable throwable) {
                Toast.makeText(context, "onFaailure: Error de respuesta API obtenerCuotaActual()", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Consultar proxima reserva para mostrar en inicio
    public void consultarProximaReserva() {
        Call<ProximaReservaView> proximaReservaLlamado = ApiClient.getApiF21()
                .obtenerProximaReserva("Bearer " + ApiClient.leer(context));

        proximaReservaLlamado.enqueue(new Callback<ProximaReservaView>() {
            @Override
            public void onResponse(Call<ProximaReservaView> call, Response<ProximaReservaView> response) {
                if (response.isSuccessful()) {
                    proximaReserva = response.body();
                    if (proximaReserva.getMsj().equals("Proxima reserva encontrada")) {
                        // parsear a dd-mm-aaaa
                        String fechaConHora = proximaReserva.getFechaClase() + "T00:00:00";
                        String f = fechaConverter.convertirFechaLegibleCorta(fechaConHora);
                        proximaReserva.setFechaClase(f);
                        mProximaReserva.postValue(proximaReserva);
                    }
                } else {
                    // Verificar si el código de respuesta es 404 (Not Found)
                    if (response.code() == 404) {
                        Log.w("API_WARNING", "Proxima reserva no encontrada (404)");
                        getmProximaReservaNoExiste.postValue("Sin reserva");
                    } else {
                        // Manejar otros errores HTTP
                        Log.e("API_ERROR", "Código: " + response.code() + " Mensaje: " + response.message());
                        Toast.makeText(context, "Error: No se pudo obtener la próxima reserva (Código: "
                                + response.code() + ")", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProximaReservaView> call, Throwable throwable) {
                Toast.makeText(context, "onFailure: Error de respuesta API obtenerProximaReserva()",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void obtenerNovedades() {
        // Realiza la llamada a la API para obtener las novedades
        Call<List<NovedadView>> llamadoNovedades = ApiClient.getApiF21().getNovedades();

        // Ejecuta la llamada de manera asíncrona
        llamadoNovedades.enqueue(new Callback<List<NovedadView>>() {
            @Override
            public void onResponse(Call<List<NovedadView>> call, Response<List<NovedadView>> response) {
                if (response.isSuccessful()) {
                    List<NovedadView> novedades = response.body();
                    Log.d("Novedades", "Datos recibidos: " + novedades.toString()); // Agregar el log aquí
                    mListaNovedades.postValue(novedades);
                } else {
                    Log.e("Novedades", "Error al obtener novedades: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<NovedadView>> call, Throwable throwable) {
                // Maneja cualquier error en la solicitud, como problemas de red
                Log.e("Novedades", "Error de respuesta API: " + throwable.getMessage());
                Toast.makeText(context, "Error de respuesta API: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
