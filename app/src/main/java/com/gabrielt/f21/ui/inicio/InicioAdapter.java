package com.gabrielt.f21.ui.inicio;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielt.f21.R;
import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.MisCreditosView;
import com.gabrielt.f21.model.NovedadView;
import com.gabrielt.f21.ui.miscreditos.MisCreditosAdapter;

import java.util.List;

public class InicioAdapter extends RecyclerView.Adapter<InicioAdapter.ViewHolderInicio> {


    private final List<NovedadView> novedadesLista;
    private LayoutInflater li;

    public InicioAdapter(List<NovedadView> novedadesLista, LayoutInflater li) {
        this.novedadesLista = novedadesLista;
        this.li = li;
    }


    @NonNull
    @Override
    public InicioAdapter.ViewHolderInicio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.novedad_card, parent, false);
        return new ViewHolderInicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InicioAdapter.ViewHolderInicio holder, int position) {

        if (novedadesLista.isEmpty()) {
            holder.tv_titulo_novedad.setText("Lista de novedades vacia.");
            holder.tv_descripcion_novedad.setText("");
            return;
        }else{
            NovedadView novedad = novedadesLista.get(position);

            holder.tv_titulo_novedad.setText(novedad.getTitulo());
            holder.tv_descripcion_novedad.setText(novedad.getDescripcion());
            Log.d("fotito", "url: "+ novedad.getUrl_Imagen());
            // Cargar la imagen con Glide
            Glide.with(holder.itemView.getContext())
                    .load("http://192.168.0.20:5001"+novedad.getUrl_Imagen()) // URL de la imagen
                    .placeholder(R.drawable.rayo_amarillo_intermedio) // Imagen mientras se carga
                    .error(R.drawable.mis_creditos_icono) // Imagen en caso de error
                    .into(holder.iv_foto_novedad); // ImageView donde se cargará la imagen
        }

    }

    @Override
    public int getItemCount () {
        //Minimo se va mostrar una card:
        return Math.max(1, novedadesLista.size());
    }


    public class ViewHolderInicio extends RecyclerView.ViewHolder {
        TextView tv_titulo_novedad, tv_descripcion_novedad;
        ImageView iv_foto_novedad;

        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);
            iv_foto_novedad = itemView.findViewById(R.id.iv_foto_novedad);
            tv_titulo_novedad = itemView.findViewById(R.id.tv_titulo_novedad);
            tv_descripcion_novedad = itemView.findViewById(R.id.tv_descirpcion_novedad);
        }
    }

}