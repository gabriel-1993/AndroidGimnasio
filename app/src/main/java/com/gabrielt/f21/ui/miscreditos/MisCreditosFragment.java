package com.gabrielt.f21.ui.miscreditos;

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
import com.gabrielt.f21.model.MisCreditosView;

import java.util.List;

public class MisCreditosFragment extends Fragment {

    private MisCreditosViewModel mViewModel;
    private FragmentMisCreditosBinding binding;

    public static MisCreditosFragment newInstance() {
        return new MisCreditosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inicializar el ViewModel
        mViewModel = new ViewModelProvider(this).get(MisCreditosViewModel.class);
        binding = FragmentMisCreditosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observar mutable : lista de miscreditos
        mViewModel.getmMisCreditos().observe(getViewLifecycleOwner(), new Observer<MisCreditosView>() {
            @Override
            public void onChanged(MisCreditosView misCreditosView) {
                if (misCreditosView != null && misCreditosView.getCuotas() != null) {
                    MisCreditosAdapter misCreditosAdapter = new MisCreditosAdapter(misCreditosView.getCuotas(), inflater);
                    binding.rvMisCreditos.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    binding.rvMisCreditos.setAdapter(misCreditosAdapter);
                }
            }
        });

        mViewModel.obtenerMisCreditos();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisCreditosViewModel.class);
        // TODO: Use the ViewModel
    }

}