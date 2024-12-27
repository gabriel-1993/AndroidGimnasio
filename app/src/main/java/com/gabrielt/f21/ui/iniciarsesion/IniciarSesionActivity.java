package com.gabrielt.f21.ui.iniciarsesion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielt.f21.MainActivity;
import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.ActivityIniciarSesionBinding;

public class IniciarSesionActivity extends AppCompatActivity {

    private IniciarSesionViewModel vm;
    private ActivityIniciarSesionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciarSesionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(IniciarSesionViewModel.class);

      /*  // Verificar si ya hay una sesión activa
        SharedPreferences preferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Si está logueado, redirigir al MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        */

        // Usar Glide para cargar la imagen redondeada
        Glide.with(this)
                .load(R.drawable.f21) //archivo en drawable
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(binding.ivIniciarSesion);


        binding.btnHasOlvidadoTuPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.iniciarRestablecerPass();
            }
        });


        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }


                vm.login(binding.etUsuarioLogin.getText().toString().trim(), binding.etPassLogin.getText().toString().trim());
                //Limpiar pass de login anterior
                binding.etPassLogin.setText("");
            }
        });


        binding.btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.registrarse();
            }
        });

        binding.btnHasOlvidadoTuPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.llamarRestablecerClave(binding.etUsuarioLogin.getText().toString().trim().toUpperCase());
            }
        });

    }
}