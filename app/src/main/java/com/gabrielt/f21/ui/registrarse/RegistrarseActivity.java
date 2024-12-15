package com.gabrielt.f21.ui.registrarse;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.ActivityIniciarSesionBinding;
import com.gabrielt.f21.databinding.ActivityRegistrarseBinding;
import com.gabrielt.f21.ui.iniciarsesion.IniciarSesionViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

public class RegistrarseActivity extends AppCompatActivity {

    private RegistrarseViewModel vm;
    private ActivityRegistrarseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistrarseViewModel.class);

        binding.etRegistrarseFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        binding.etRegistrarseFechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDatePicker();
                }
            }
        });

        binding.btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.crearUsuario(binding.etRegistrarseFechaNacimiento.getText()+"", binding.etRegistrarseNombre.getText()+"", binding.etRegistrarseApellido.getText()+"", binding.etRegistrarseEmail.getText()+"", binding.etRegistrarseTelefono.getText()+"".trim() , binding.etRegistrarsePassword.getText()+"".trim(), binding.etRegistrarseConfirmPassword.getText()+"".trim());
            }
        });

        vm.getmMsjToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String msj) {
                Toast.makeText(getApplication(), msj, Toast.LENGTH_LONG).show();
            }
        });
    }

    //mostrar calendario
    private void showDatePicker() {

        // Obtener el DatePicker desde el ViewModel
        MaterialDatePicker<Long> datePicker = vm.createDatePicker();

        // Mostrar el DatePicker
        datePicker.show(getSupportFragmentManager(), "datePicker");

        // Manejar la selección
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Actualizar la vista con la fecha seleccionada
            binding.etRegistrarseFechaNacimiento.setText(datePicker.getHeaderText());
        });
    }


}