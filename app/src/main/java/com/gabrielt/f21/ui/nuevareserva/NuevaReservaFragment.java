package com.gabrielt.f21.ui.nuevareserva;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gabrielt.f21.R;
import com.gabrielt.f21.databinding.FragmentNuevaReservaBinding;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.NuevaReservaView;
import com.gabrielt.f21.ui.nuevareserva.NuevaReservaAdapter;
import com.gabrielt.f21.ui.nuevareserva.NuevaReservaViewModel;

import java.util.List;

public class NuevaReservaFragment extends Fragment {

    private NuevaReservaViewModel mViewModel;
    private FragmentNuevaReservaBinding binding;

    private FechaConverter fechaConverter;

    public static NuevaReservaFragment newInstance() {
        return new NuevaReservaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inicializar el ViewModel
        mViewModel = new ViewModelProvider(this).get(NuevaReservaViewModel.class);
        binding = FragmentNuevaReservaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fechaConverter = new FechaConverter();


        // Configurar el RecyclerView
        configurarRecyclerView(inflater);

        // Obtener la lista de reservas
        mViewModel.obtenerLista();

        return root;
    }

    private void configurarRecyclerView(LayoutInflater inflater) {
        mViewModel.getNuevasReservasRespuesta().observe(getViewLifecycleOwner(), new Observer<NuevaReservaView>() {
            @Override
            public void onChanged(NuevaReservaView nuevaReservaView) {
                // Configurar el Adapter y LayoutManager
                List<NuevaReservaView.Reserva> nuevaReservaLista = nuevaReservaView.getClaseHorarios();

                NuevaReservaAdapter nuevaReservaAdapter = new NuevaReservaAdapter(nuevaReservaLista, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.rvNuevaReservaLista.setLayoutManager(gridLayoutManager);
                binding.rvNuevaReservaLista.setAdapter(nuevaReservaAdapter);
            }
        });
    }



}
