package com.gabrielt.f21.ui.nuevareserva;

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
import com.gabrielt.f21.databinding.FragmentMisReservasBinding;
import com.gabrielt.f21.databinding.FragmentNuevaReservaBinding;
import com.gabrielt.f21.model.NuevaReservaView;
import com.gabrielt.f21.ui.misreservas.MisReservasAdapter;
import com.gabrielt.f21.ui.misreservas.MisReservasViewModel;

import java.util.List;

public class NuevaReservaFragment extends Fragment {

    private NuevaReservaViewModel mViewModel;
    private FragmentNuevaReservaBinding binding;

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


        mViewModel.getmNuevaReservaLista().observe(getViewLifecycleOwner(), new Observer<List<NuevaReservaView>>() {
            @Override
            public void onChanged(List<NuevaReservaView> nuevaReservaLista) {

                NuevaReservaAdapter nuevaReservaAdapter = new NuevaReservaAdapter(nuevaReservaLista, inflater);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);

                binding.rvNuevaReservaLista.setAdapter(nuevaReservaAdapter);
                binding.rvNuevaReservaLista.setLayoutManager(gridLayoutManager);

            }
        });

        // Llamar al método para mostrar todas las clases disponibles para nueva reserva
        mViewModel.obtenerListaNuevaReserva();

        return root;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NuevaReservaViewModel.class);
        // TODO: Use the ViewModel
    }

}