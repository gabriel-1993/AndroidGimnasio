package com.gabrielt.f21.ui.restablecerpassword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gabrielt.f21.R;

public class RestablecerPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restablecer_password);

        // Obtener el Intent y extraer el enlace
        Intent intent = getIntent();
        Uri data = intent.getData(); // Esto obtiene la URI del enlace

        if (data != null) {
            // Extraer el token de la URL
            String token = data.getQueryParameter("token");

            // Aquí puedes usar el token para hacer lo que necesites, por ejemplo, enviar una solicitud para restablecer la contraseña
            Log.d("Enlace", "Token recibido: " + token);
        } else {
            Log.d("Enlace", "No se recibió ningún enlace");
        }

    }
}