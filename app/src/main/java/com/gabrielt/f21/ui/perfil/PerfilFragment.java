package com.gabrielt.f21.ui.perfil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.FragmentPerfilBinding;
import com.gabrielt.f21.model.Usuario;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding binding;
    //foto
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        abrirGaleria();


        mViewModel.getmUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                binding.etPerfilFechaNacimiento.setText(usuario.getFechaNacimiento());
                binding.etApellidoPerfil.setText(usuario.getApellido());
                binding.etNombrePerfil.setText(usuario.getNombre());
                binding.etTelefonoPerfil.setText(usuario.getTelefono());
                binding.etEmailPerfil.setText(usuario.getEmail());

                //binding.ivPerfilModificar.setImageResource(R.drawable.ic_launcher_background);
                Glide.with(getContext())
                        //lp
                        //.load("http://192.168.1.4:5001"+usuario.getAvatar())
                        //ger
                        //.load("http://192.168.0.20:5001"+usuario.getAvatar())
                        //jd
                        .load("http://192.168.0.11:5001"+usuario.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.sinfoto)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop()
                        .into(binding.ivPerfil);
            }
        });

        mViewModel.recuperarDatosUsuarioToken();

        mViewModel.getmAvatar().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                // Muestra la imagen seleccionada
                binding.ivPerfil.setImageURI(uri);
                // Llama al metodo del ViewModel para actualizar el avatar en el servidor
                mViewModel.actualizarAvatar();
            }
        });

        mViewModel.getmMsjToast().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msj) {
                Toast.makeText(requireContext(), msj, Toast.LENGTH_LONG).show();
            }
        });

        // Configurar eventos para mostrar calendario
        //focus: sino se debe hacer click dos veces para abrir
        binding.etPerfilFechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //if para mostrar calendario con change focus true
                if(hasFocus){
                    showDatePicker();
                }
            }
        });

        //click
        binding.etPerfilFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Configura el botón para modificar datos (fecha nacimiento, Nombre, Apellido, Email y Telefono)
        binding.btnModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.modificarPerfil(binding.etPerfilFechaNacimiento.getText().toString().trim(), binding.etNombrePerfil.getText().toString().trim(), binding.etApellidoPerfil.getText().toString().trim(), binding.etEmailPerfil.getText().toString().trim(), binding.etTelefonoPerfil.getText().toString().trim());
            }
        });

        binding.btnFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        binding.btnModificarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega al fragment de modificar clave
                NavHostFragment.findNavController(PerfilFragment.this)
                        .navigate(R.id.nav_modificar_pass);
            }
        });



        return root;
    }




    //mostrar calendario
    private void showDatePicker() {
            // Obtener el DatePicker desde el ViewModel
            MaterialDatePicker<Long> datePicker = mViewModel.createDatePicker();

            // Mostrar el DatePicker
            datePicker.show(getParentFragmentManager(), "datePicker");

            // Manejar la selección
            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Actualizar la vista con la fecha seleccionada
                binding.etPerfilFechaNacimiento.setText(datePicker.getHeaderText());
            });
    }

    // FOTO USUARIO
    //abrir galeria para seleccionar foto
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); // Filtra para mostrar solo imagenes
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mViewModel.recibirFoto(result);
            }
        });
    }









    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }



}