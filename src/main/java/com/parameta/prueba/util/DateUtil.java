package com.parameta.prueba.util;

import com.parameta.prueba.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate stringToLocalDate(String fechaString, String nombreCampo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(fechaString, formatter);
        } catch (Exception e) {
            throw new ValidationException(String.format(Constant.MENSAJE_FORMATO_FECHA_INCORRECTO, nombreCampo));
        }
    }
}