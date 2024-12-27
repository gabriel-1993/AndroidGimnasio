package com.gabrielt.f21.model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class FechaConverter {

    // Fecha nacimiento ingresa del calendario con formato string: 9 de diciembre de 2024
    //Servidor .NET lo espera en DateTime , lo envio en String pero con el formato que le permite a .NET parsearlo automaticamente
    //cadena ISO 8601 con el formato yyyy-MM-ddTHH:mm:ss.
    public String convertirFechaISO8601(String fecha) {
        try {
            // Convierte el String de entrada a Date
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
            Date fechaDate = formatoEntrada.parse(fecha);

            // Convierte Date a formato ISO 8601
            SimpleDateFormat formatoISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return formatoISO8601.format(fechaDate); // Ejemplo: 1993-03-02T00:00:00
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String convertirFechaLegible(String fechaISO) {
        try {
            // Convierte la fecha en formato ISO 8601 a Date
            SimpleDateFormat formatoISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date fechaDate = formatoISO8601.parse(fechaISO);

            // Convierte Date a formato legible
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
            return formatoSalida.format(fechaDate); // Ejemplo: 02 de marzo de 1993
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertirFechaLegibleCorta(String fechaISO) {
        try {
            // Convierte la fecha en formato ISO 8601 a Date
            SimpleDateFormat formatoISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date fechaDate = formatoISO8601.parse(fechaISO);

            // Convierte Date a formato legible con el formato dd/MM/yyyy
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSalida.format(fechaDate); // Ejemplo: 31/09/2024
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //para parsear el dd/mm/aaaa --> aaaa-mm-dd. Para comparar con la base de datos
    public String convertirFechaBdd(String fechaLegible) {
        try {
            // Convierte la fecha en formato legible (dd/MM/yyyy) a Date
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate = formatoEntrada.parse(fechaLegible);

            // Convierte Date al formato ISO (yyyy-MM-dd)
            SimpleDateFormat formatoISO8601 = new SimpleDateFormat("yyyy-MM-dd");
            return formatoISO8601.format(fechaDate); // Ejemplo: 2024-09-31
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //Parsera de hh:mm:ss a con hh:mm
    public static String formatearHoraSinSegundos(String horaConSegundos) {
        try {
            // El formato original de la hora que se recibe (con segundos)
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(horaConSegundos);

            // Formato de la hora que se desea (sin segundos)
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date); // Devuelve la hora formateada
        } catch (Exception e) {
            e.printStackTrace();
            return horaConSegundos; // Devuelve la hora original si ocurre un error
        }
    }


}
