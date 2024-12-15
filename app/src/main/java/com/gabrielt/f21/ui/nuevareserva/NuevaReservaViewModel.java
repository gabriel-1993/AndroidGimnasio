package com.gabrielt.f21.ui.nuevareserva;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.model.MisReservasView;
import com.gabrielt.f21.model.NuevaReservaView;

import java.util.ArrayList;
import java.util.List;

public class NuevaReservaViewModel extends AndroidViewModel {

    private MutableLiveData<List<NuevaReservaView>> mNuevaReservaLista;

    private Context context;
    public NuevaReservaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<NuevaReservaView>> getmNuevaReservaLista() {
        if(mNuevaReservaLista == null){
            mNuevaReservaLista = new MutableLiveData<>();
        }
        return mNuevaReservaLista;
    }


    public void obtenerListaNuevaReserva() {
        List<NuevaReservaView> listaEjemplo = new ArrayList<>();

        // Ejemplos de datos
        listaEjemplo.add(new NuevaReservaView(1, "FUNCIONAL", 25,"Miercoles","08:00", "2024-09-12",true, false));
        listaEjemplo.add(new NuevaReservaView(3, "CROSSFIT", 25,"Martes","08:00", "2024/9/12",true, false));
        listaEjemplo.add(new NuevaReservaView(4, "CROSSFIT", 25,"Martes","08:00", "2024/9/12",true, false));
        listaEjemplo.add(new NuevaReservaView(6, "ZUMBA", 20,"Viernes","08:00", "2024/9/12",true, false));
        listaEjemplo.add(new NuevaReservaView(8, "MOVILIDAD", 20,"Lunes","08:00", "2024/9/12",true, false));
        listaEjemplo.add(new NuevaReservaView(7, "AVANZADO", 8,"Lunes","08:00", "2024/9/12",true, false));

        // Guardamos la lista en MutableLiveData
        mNuevaReservaLista.setValue(listaEjemplo);




    }





}