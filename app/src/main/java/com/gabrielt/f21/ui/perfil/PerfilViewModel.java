package com.gabrielt.f21.ui.perfil;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabrielt.f21.model.FechaConverter;
import com.gabrielt.f21.model.Usuario;
import com.gabrielt.f21.request.ApiClient;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;

import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Usuario usuario;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<Uri> mAvatar;
    private String avatar;
    private MutableLiveData<String> mMsjToast;
    FechaConverter fechaConverter;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        fechaConverter = new FechaConverter();
    }

    public MutableLiveData<String> getmMsjToast() {
        if(mMsjToast == null){
            mMsjToast =  new MutableLiveData<String>();
        }
        return mMsjToast;
    }
    public MutableLiveData<Usuario> getmUsuario() {
        if(mUsuario == null){
            mUsuario = new MutableLiveData<Usuario>();
        }
        return mUsuario;
    }

    public LiveData<Uri> getmAvatar() {
        if(mAvatar == null){
            mAvatar = new MutableLiveData<>();
        }
        return mAvatar;
    }

    /*    recuperar datos con el id del token */
    public void recuperarDatosUsuarioToken(){
        Call<Usuario> usuarioCall = ApiClient.getApiF21().getPerfilUsuario("Bearer "+ApiClient.leer(context));
        usuarioCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    usuario = response.body();

                    //parsear fecha de datetime a string legible
                    String fechaLegible = fechaConverter.convertirFechaLegible(usuario.getFechaNacimiento()+"");
                    usuario.setFechaNacimiento(fechaLegible);
                    avatar = usuario.getAvatar();
                    mUsuario.postValue(usuario);
                }else{
                    Log.d("SALiDA", response.message());
                    mMsjToast.postValue("Error de respuesta API getPerfilUsuario()");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Toast.makeText(context, "onFaailure: Error de respuesta API getPerfilUsuario()", Toast.LENGTH_SHORT).show();
            }
        });
    }


   //mostrar calendario fecha nacimiento
    public MaterialDatePicker<Long> createDatePicker() {
        // Obtener el calendario actual
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Establecer el inicio (70 años atrás)
        calendar.add(Calendar.YEAR, -70);

        long startDate = calendar.getTimeInMillis();

        // Establecer el fin (70 años adelante)
        calendar.add(Calendar.YEAR, 70); // Regresa 70 años al presente y avanza 70 más

        long endDate = calendar.getTimeInMillis();

        // Construir las restricciones del calendario
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setStart(startDate)
                .setEnd(endDate);

        // Construir el DatePicker
        return MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar fecha")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();
    }

    //FOTO USUARIO
    public void recibirFoto(ActivityResult result){

        if(result.getResultCode() == RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();

            avatar = uri.toString();

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mAvatar.setValue(uri);
        }
    }

    public void actualizarAvatar() {
        if (mAvatar.getValue() == null) {
            return;
        }

        Uri avatarUri = mAvatar.getValue();

        // Crear un archivo temporal a partir del Uri
        File avatarFile = createTemporaryFileFromUri(avatarUri);
        if (avatarFile == null) {
            // Muestra un Toast con el mensaje de error
            Toast.makeText(getApplication(), "Error al crear un archivo temporal de la imagen.", Toast.LENGTH_LONG).show();
            Log.e("Error:", "Archivo temporal no creado");
            return;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatarFile", avatarFile.getName(), requestFile);

        Call<ResponseBody> call = ApiClient.getApiF21().modificarAvatar("Bearer " + ApiClient.leer(context), avatarPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Foto actualizada con éxito", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        String errorMessage = response.errorBody().string(); // Captura el cuerpo de la respuesta de error
                        Log.d("Error response:", errorMessage);
                        Toast.makeText(getApplication(), "Error al actualizar el avatar: "+ response.code(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplication(), "Error al procesar el error de respuesta", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión al intentar actualizar el avatar", Toast.LENGTH_LONG).show();

            }
        });
    }

    // Metodo para crear un archivo temporal a partir de un Uri
    private File createTemporaryFileFromUri(Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                return null;
            }

            // Crear archivo temporal
            File tempFile = new File(context.getCacheDir(), "tempAvatarFile.jpg");
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            // Copiar el contenido del InputStream al archivo temporal
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setmAvatar(MutableLiveData<Uri> mAvatar) {
        this.mAvatar = mAvatar;
    }



    public void modificarPerfil(String fecha_nacimiento_string, String nombre, String apellido, String email, String telefono){

            //Validar que existan cambios
            if(     mUsuario.getValue().getFechaNacimiento().trim().equals(fecha_nacimiento_string.trim()) &&
                    mUsuario.getValue().getNombre().toUpperCase().trim().equals(nombre.toUpperCase().trim()) &&
                    mUsuario.getValue().getApellido().toUpperCase().trim().equals(apellido.toUpperCase().trim()) &&
                    mUsuario.getValue().getEmail().toUpperCase().trim().equals(email.toUpperCase().trim()) &&
                    mUsuario.getValue().getTelefono().trim().equals(telefono.trim())
            ) {
                mMsjToast.setValue("No se detectaron cambios.\nPrimero realice alguna modificación.");
                return;
            }

            //Validar campos obligatorios
            if(fecha_nacimiento_string.isEmpty() || apellido.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()){
                mMsjToast.setValue("Por favor, complete todos los campos antes de continuar.");
                return;
            }

            //fecha string con formato para que .net pueda parsearla automaticamente a datetime
            String fechaNacimientoISO8601 = fechaConverter.convertirFechaISO8601(fecha_nacimiento_string);

            // Validación de Apellido (solo letras, mínimo 2 caracteres)
            if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,12}")) {
                mMsjToast.setValue("Apellido: Debe contener solo letras y tener entre 2 y 12 caracteres.");
                return;
            }

            // Validación de Nombre (solo letras, mínimo 2 caracteres)
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,20}")) {
                mMsjToast.setValue("Nombre:  Debe contener solo letras y tener entre 2 y 20 caracteres.");
                return;
            }

            // Validación de Teléfono (opcional, solo si no está vacío)
            if (!telefono.matches("[0-9+\\-\\s]{7,15}")) {
                mMsjToast.setValue("Teléfono: Debe contener solo números, guiones o espacios, y tener entre 7 y 15 dígitos.");
                return;
            }

            // Validación de Email (patrón simple para correo electrónico válido)
            if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                mMsjToast.setValue("Email: Introduzca un correo electrónico válido.");
                return;
            }


        Call<String> call = ApiClient.getApiF21().modificarPerfil("Bearer " + ApiClient.leer(context), fechaNacimientoISO8601, nombre, apellido, telefono, email);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        mMsjToast.postValue("");
                        mMsjToast.postValue("Datos modificados con exito.");
                    } else {
                        mMsjToast.postValue("Error al intentar modificar datos del perfil.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    mMsjToast.postValue("Error de conexion al modificar el perfil");

                }
            });

    }

}