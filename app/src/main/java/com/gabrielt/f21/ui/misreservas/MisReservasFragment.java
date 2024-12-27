package com.gabrielt.f21.ui.misreservas;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.FragmentMisCreditosBinding;
import com.gabrielt.f21.databinding.FragmentMisReservasBinding;
import com.gabrielt.f21.model.MisReservasView;
import com.gabrielt.f21.ui.miscreditos.MisCreditosAdapter;
import com.gabrielt.f21.ui.miscreditos.MisCreditosViewModel;

import java.util.ArrayList;
import java.util.List;

public class MisReservasFragment extends Fragment {

    private MisReservasViewModel mViewModel;
    private FragmentMisReservasBinding binding;

    public static MisReservasFragment newInstance() {
        return new MisReservasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inicializar el ViewModel
        mViewModel = new ViewModelProvider(this).get(MisReservasViewModel.class);
        binding = FragmentMisReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getmMisReservas().observe(getViewLifecycleOwner(), new Observer<MisReservasView>() {
            @Override
            public void onChanged(MisReservasView misReservasView) {
                List<MisReservasView.Reserva> reservas = misReservasView.getReservas();
                MisReservasAdapter misReservasAdapter = new MisReservasAdapter(reservas, inflater);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvMisReservas.setAdapter(misReservasAdapter);
                binding.rvMisReservas.setLayoutManager(gridLayoutManager);
            }
        });

        // Llamar al método para recuperar misreservas
        mViewModel.obtenerMisReservas();

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisReservasViewModel.class);
        // TODO: Use the ViewModel
    }

}