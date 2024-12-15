package com.gabrielt.f21.ui.iniciarsesion;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import retrofit2.Call;
import android.util.Patterns;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gabrielt.f21.MainActivity;
import com.gabrielt.f21.request.ApiClient;
import com.gabrielt.f21.ui.registrarse.RegistrarseActivity;
import com.gabrielt.f21.ui.restablecerpassword.RestablecerPasswordActivity;

import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesionViewModel extends AndroidViewModel {
    private Context context;

    public IniciarSesionViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void iniciarRestablecerPass(){
        Intent intent = new Intent(context, RestablecerPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void login(String email, String pass){
        // Validar si los campos están vacíos
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Por favor, ingrese su email y contraseña.", Toast.LENGTH_LONG).show();
            return;
        }
        // Validar que el usuario tenga formato de email valido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "El formato del correo no es válido.", Toast.LENGTH_LONG).show();
            return;
        }
        Call<String> llamada = ApiClient.getApiF21().login(email, pass);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();
                if(response.isSuccessful() && token != null){
                    ApiClient.guardar(context, token);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, "Throwable:"+ throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void registrarse(){
        Intent intent = new Intent(context, RegistrarseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
