package com.gabrielt.f21;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gabrielt.f21.model.Usuario;
import com.gabrielt.f21.ui.iniciarsesion.IniciarSesionActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.gabrielt.f21.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.toolbar).show();
            }
        });

        binding.navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_cerrar_sesion) {
                cerrarSesion();
                return true;
            }
            return false;
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_mis_creditos, R.id.nav_mis_reservas, R.id.nav_nueva_reserva)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        vm.getmUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                View headerView = navigationView.getHeaderView(0);
                ImageView foto = headerView.findViewById(R.id.iv_foto_header);
                TextView saludo = headerView.findViewById(R.id.tv_saludo);
                TextView email = headerView.findViewById(R.id.tv_saludo_email);


                String nom = usuario.getNombre();
                String ape = usuario.getApellido();

                //Convertir la primera letra de cada palabra a mayúscula y el resto a minúsculas
                String nombreFormateado = nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase();
                String apellidoFormateado = ape.substring(0, 1).toUpperCase() + ape.substring(1).toLowerCase();

                // Crear el saludo con el formato adecuado
                String saludoString = "¡Bienvenido, " + nombreFormateado + " " + apellidoFormateado + "!";
                saludo.setText(saludoString);
                email.setText(usuario.getEmail().toLowerCase());

                // Carga la imagen del propietario
                Glide.with(MainActivity.this)
                        //.load("http://192.168.0.5:5290" + usuario.getAvatar())
                        //.load("http://192.168.1.4:5290" + usuario.getAvatar())
                        //JustoDaract
                        .load("http://192.168.0.11:5001" + usuario.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.sinfoto)
                        .error(R.drawable.sinfoto)
                        .circleCrop()
                        .into(foto);
            }
        });

        vm.recuperarDatosUsuarioToken();


        // Listener para cuando se abre el menú
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                vm.recuperarDatosUsuarioToken(); // Llama a leerPropietario cuando el menú se abre
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        vm.recuperarDatosUsuarioToken(); // Actualiza los datos cuando la actividad vuelve a estar visible
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void cerrarSesion() {
        new AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de querer salir de la app?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Limpiar datos de sesión (si es necesario)
                        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
                        preferences.edit().clear().apply();

                        // Redirigir a la actividad de inicio de sesión
                        Intent intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
                        startActivity(intent);

                        // Finalizar la actividad actual
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }




}