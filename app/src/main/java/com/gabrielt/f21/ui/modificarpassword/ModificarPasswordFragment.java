package com.gabrielt.f21.ui.modificarpassword;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.FragmentModificarPasswordBinding;
import com.gabrielt.f21.request.ApiClient;

public class ModificarPasswordFragment extends Fragment {

    private ModificarPasswordViewModel mViewModel;

    private FragmentModificarPasswordBinding binding;

    public static ModificarPasswordFragment newInstance() {
        return new ModificarPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentModificarPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ModificarPasswordViewModel.class);

        // Usar Glide para cargar la imagen redondeada
        Glide.with(this)
                .load(R.drawable.f21) //archivo en drawable
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(binding.imageViewModificarPassword);



        mViewModel.getmMsjToast().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msj) {
                Toast.makeText(requireContext(), msj, Toast.LENGTH_LONG).show();
            }
        });

        mViewModel.getmPassModificadoOk().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etPassActual.setText("");
                binding.etConfirmarPass.setText("");
                binding.etPassNueva.setText("");

            }
        });

        // Configurar el evento de click del botón para cambiar la contraseña
        binding.btnModificarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveActual = binding.etPassActual.getText().toString().trim();
                String nuevaClave = binding.etPassNueva.getText().toString().trim();
                String repetirClave = binding.etConfirmarPass.getText().toString().trim();

                String token = ApiClient.leer(getContext());
                mViewModel.cambiarClave(token, claveActual, nuevaClave, repetirClave);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }

}