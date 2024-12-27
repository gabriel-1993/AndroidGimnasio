package com.gabrielt.f21;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gabrielt.f21.model.CancelarReservaView;
import com.gabrielt.f21.model.Usuario;
import com.gabrielt.f21.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    Context context;

    private MutableLiveData<Usuario> mUsuario;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getmUsuario() {
        if(mUsuario == null){
            mUsuario = new MutableLiveData<Usuario>();
        }
        return mUsuario;
    }

    /*    recuperar datos con el id del token */
    public void recuperarDatosUsuarioToken(){

        Call<Usuario> llamadoUsuario = ApiClient.getApiF21().getPerfilUsuario("Bearer "+ApiClient.leer(context));

        llamadoUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario = response.body();
                mUsuario.postValue(usuario);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Toast.makeText(context, "onResponse: Error de respuesta API MainActivity getPerfil()", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
