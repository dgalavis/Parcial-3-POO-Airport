/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.PassengerRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author galav
 */
    public class PassengerValidator {
        public static Response parseAndValidate(String idStr, String firstname, String lastname,
                                            String birthStr, String phoneCodeStr, String phoneStr,
                                            String country, PassengerRepository repo, boolean isUpdate) {
        // Validar que los campos requeridos no estén vacíos
        if (isNullOrEmpty(idStr)) {
            return new Response("El ID no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(firstname)) {
            return new Response("El nombre no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(lastname)) {
            return new Response("El apellido no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(birthStr)) {
            return new Response("La fecha de nacimiento no puede estar vacía.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(phoneCodeStr)) {
            return new Response("El código de país no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(phoneStr)) {
            return new Response("El número de teléfono no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (isNullOrEmpty(country)) {
            return new Response("El país no puede estar vacío.", Status.BAD_REQUEST);
        }

        // Ahora sí hacemos los parseos y validaciones numéricas
        long id;
        int phoneCode;
        long phone;
        LocalDate birthDate;

        try {
            id = Long.parseLong(idStr);
            if (id < 0 || idStr.length() > 15) {
                return new Response("El ID debe ser >= 0 y tener máximo 15 dígitos.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("El ID debe ser un número válido.", Status.BAD_REQUEST);
        }

        if (!isUpdate && repo.getPassenger(id) != null) {
            return new Response("Ya existe un pasajero con ese ID.", Status.BAD_REQUEST);
        }

        try {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            birthDate = LocalDate.parse(birthStr, formatter);
        } catch (DateTimeParseException e) {
            return new Response("La fecha de nacimiento es inválida. Formato esperado: YYYY-MM-DD", Status.BAD_REQUEST);
        }

        try {
            phoneCode = Integer.parseInt(phoneCodeStr);
            if (phoneCode < 0 || phoneCodeStr.length() > 3) {
                return new Response("El código de país debe ser >= 0 y tener máximo 3 dígitos.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("El código de país debe ser numérico.", Status.BAD_REQUEST);
        }

        try {
            phone = Long.parseLong(phoneStr);
            if (phone < 0 || phoneStr.length() > 11) {
                return new Response("El teléfono debe ser >= 0 y tener máximo 11 dígitos.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("El teléfono debe ser numérico.", Status.BAD_REQUEST);
        }

        // Si todo es válido, creamos el objeto
        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, phoneCode, phone, country);
        return new Response("Validación exitosa", Status.OK, passenger);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    }
