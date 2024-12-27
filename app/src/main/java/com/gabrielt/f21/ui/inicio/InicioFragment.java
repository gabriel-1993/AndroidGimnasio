package com.gabrielt.f21.ui.inicio;

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
import com.gabrielt.f21.databinding.FragmentInicioBinding;
import com.gabrielt.f21.databinding.FragmentPerfilBinding;
import com.gabrielt.f21.model.CuotaActualView;
import com.gabrielt.f21.model.NovedadView;
import com.gabrielt.f21.model.ProximaReservaView;
import com.gabrielt.f21.ui.miscreditos.MisCreditosAdapter;
import com.gabrielt.f21.ui.perfil.PerfilViewModel;

import java.util.List;

public class InicioFragment extends Fragment {

    private InicioViewModel mViewModel;
    private FragmentInicioBinding binding;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        //Cuota actual
        mViewModel.getmCuotaActual().observe(getViewLifecycleOwner(), new Observer<CuotaActualView>() {
            @Override
            public void onChanged(CuotaActualView cuotaActualView) {
                binding.fixedCards.tvClasesRestantesInicio.setText(cuotaActualView.getCreditosDisponibles() + " Clases");
                binding.fixedCards.tvDiasVencimientoCreditosInicio.setText("Vence " +cuotaActualView.getFechaVencimiento());
            }
        });

        //Cuota caducada/vencida
        mViewModel.getmCuotaVencida().observe(getViewLifecycleOwner(), new Observer<CuotaActualView>() {
            @Override
            public void onChanged(CuotaActualView cuotaActualView) {
                binding.fixedCards.tvClasesRestantesInicio.setText("Sin creditos");
                binding.fixedCards.tvDiasVencimientoCreditosInicio.setText("disponibles");
            }
        });

        mViewModel.consultarEstadoCuota();




        mViewModel.getmProximaReserva().observe(getViewLifecycleOwner(), new Observer<ProximaReservaView>() {
            @Override
            public void onChanged(ProximaReservaView proximaReservaView) {
                binding.fixedCards.tvProximaReserva.setText(proximaReservaView.toString());
            }
        });

        mViewModel.getmProximaReservaNoExiste().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String string) {
                binding.fixedCards.tvProximaReserva.setText(string);
            }
        });

      mViewModel.consultarProximaReserva();

        mViewModel.getmListaNovedades().observe(getViewLifecycleOwner(), new Observer<List<NovedadView>>() {
            @Override
            public void onChanged(List<NovedadView> novedadViews) {
                if (novedadViews != null) {
                    InicioAdapter novedadesAdapter = new InicioAdapter(novedadViews, inflater);
                    binding.rvNovedaesInicio.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    binding.rvNovedaesInicio.setAdapter(novedadesAdapter);
                }
            }
        });

        mViewModel.obtenerNovedades();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        // TODO: Use the ViewModel
    }

}