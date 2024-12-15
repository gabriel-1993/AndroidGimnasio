package com.gabrielt.f21.ui.modificarpassword;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMsjToast;

    public ModificarPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getmMsjToast() {
        if(mMsjToast == null){
            mMsjToast = new MutableLiveData<String>();
        }
        return mMsjToast;
    }



    public void cambiarClave(String token, String claveActual, String nuevaClave, String repetirClave) {

        // Dato o contraseña vacía
        if (claveActual.isEmpty() || nuevaClave.isEmpty() || repetirClave.isEmpty()) {
            mMsjToast.postValue("Debe ingresar las tres contraseñas.");
            return;
        }

        // Validar longitud de la nueva clave (mínimo 3, máximo 10)
        if (nuevaClave.length() < 4 || nuevaClave.length() > 10) {
            mMsjToast.postValue("La nueva contraseña debe tener entre 4 y 10 caracteres.");
            return;
        }

        // Validar que la nueva clave y confirmar nueva sean iguales
        if (!nuevaClave.equals(repetirClave)) {
            // Las contraseñas no coinciden
            mMsjToast.postValue("Contraseña nueva y confirmar contraseña deben ser iguales.");
            return;
        }

        // Sin cambio en clave nueva: 3 iguales claveActualIngresada
        if (nuevaClave.equals(claveActual) && repetirClave.equals(claveActual)) {
            /* No especificar info de la clave actual si es correcta o no */
            mMsjToast.postValue("Datos incorrectos.");
            return;
        }


        // Llamada a la API para cambiar la contraseña
        Call<String> call = ApiClient.getApiF21().modificarPassword("Bearer " + token, claveActual, nuevaClave, repetirClave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Contraseña cambiada correctamente
                    mMsjToast.postValue("Contraseña modificada con éxito.");
                } else {
                    // Error en la respuesta
                    //resultMessage.postValue("Error al cambiar la contraseña: " + response.message());
                    mMsjToast.postValue("Contraseña actual incorrecta.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Error en la solicitud
                mMsjToast.postValue("Error de conexión: " + t.getMessage());
            }
        });

    }



}