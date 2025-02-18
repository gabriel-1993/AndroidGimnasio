package com.gabrielt.f21.ui.registrarse;

import android.app.Application;
import android.content.Context;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.request.ApiClient;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarseViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mMsjToast;
    FechaConverter fechaConverter;

    private MutableLiveData<Boolean> mLimpiarCampos;

    public MutableLiveData<Boolean> getmLimpiarCampos() {
        if(mLimpiarCampos==null){
            mLimpiarCampos = new MutableLiveData<>();
        }
        return mLimpiarCampos;
    }

    public MutableLiveData<String> getmMsjToast() {
        if(mMsjToast == null){
            mMsjToast =  new MutableLiveData<String>();
        }
        return mMsjToast;
    }

    public RegistrarseViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        fechaConverter = new FechaConverter();
    }


    //calendario
    public MaterialDatePicker<Long> createDatePicker() {
        // Obtener el calendario actual
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Establecer el inicio (70 años atrás)
        calendar.add(Calendar.YEAR, -70);

        long startDate = calendar.getTimeInMillis();

        // Establecer el fin (70 años adelante)
        calendar.add(Calendar.YEAR, 70); // Regresa 70 años al presente y avanza 70 más

        long endDate = calendar.getTimeInMillis();

        // Construir las restricciones del calendario
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setStart(startDate)
                .setEnd(endDate);

        // Construir el DatePicker
        return MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar fecha")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();
    }


    public void crearUsuario(String fechaNacimiento, String nombre, String apellido, String email, String telefono, String pass, String confirmarPass){
        // Validar si los campos están vacíos
        if (fechaNacimiento.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || pass.isEmpty() || confirmarPass.isEmpty()) {
           mMsjToast.setValue("Datos vacios, por favor revise el formulario.");
            return;
        }
        // Validar que el usuario tenga formato de email valido
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            mMsjToast.setValue("El formato del correo no es válido.");
            return;
        }

        if(!pass.equals(confirmarPass)){
            mMsjToast.setValue("Las contraseñas ingresadas deben ser iguales.");
            return;
        }
        String fechaIso8601 = fechaConverter.convertirFechaISO8601(fechaNacimiento);

        Call<String> llamada = ApiClient.getApiF21().registrarse( fechaIso8601, nombre.toUpperCase().trim(), apellido.toUpperCase().trim(), email.toUpperCase().trim(), telefono, pass);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // La respuesta fue exitosa (200 OK)
                    mMsjToast.postValue("Cuenta creada con éxito.");
                    mLimpiarCampos.postValue(true); // Notificar a la Activity para limpiar los campos

                } else {
                    try {
                        // Extraer el mensaje de error enviado por el servidor
                        String errorMensaje = response.errorBody().string();
                        mMsjToast.postValue(errorMensaje);
                    } catch (IOException e) {
                        // Error al leer el cuerpo de error
                        mMsjToast.postValue("Error al procesar la respuesta del servidor.");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Error de red u otra falla
                mMsjToast.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }


}
