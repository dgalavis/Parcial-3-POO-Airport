/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.PassengerRepository;

/**
 *
 * @author galav
 */
public class PassengerValidator {
        public static Response validate(Passenger p, PassengerRepository repo, boolean isUpdate) {
        // Validación de ID
        if (p.getId() < 0 || String.valueOf(p.getId()).length() > 15) {
            return new Response("El ID debe ser >= 0 y tener máximo 15 dígitos.", Status.BAD_REQUEST);
        }

        // Si es registro (no actualización), validamos que el ID no exista
        if (!isUpdate && repo.getPassenger(p.getId()) != null) {
            return new Response("Ya existe un pasajero con ese ID.", Status.BAD_REQUEST);
        }

        // Validación de nombres no vacíos
        if (isNullOrEmpty(p.getFirstname()) || isNullOrEmpty(p.getLastname())) {
            return new Response("El nombre y apellido no pueden estar vacíos.", Status.BAD_REQUEST);
        }

        // Validación de fecha de nacimiento (LocalDate no debería ser null)
        if (p.getBirthDate() == null) {
            return new Response("La fecha de nacimiento no puede ser nula.", Status.BAD_REQUEST);
        }

        // Validación de código de país
        if (p.getCountryPhoneCode() < 0 || String.valueOf(p.getCountryPhoneCode()).length() > 3) {
            return new Response("Código de país inválido: debe ser >= 0 y tener máximo 3 dígitos.", Status.BAD_REQUEST);
        }

        // Validación de teléfono
        if (p.getPhone() < 0 || String.valueOf(p.getPhone()).length() > 11) {
            return new Response("Teléfono inválido: debe ser >= 0 y tener máximo 11 dígitos.", Status.BAD_REQUEST);
        }

        // Validación de país no vacío
        if (isNullOrEmpty(p.getCountry())) {
            return new Response("El país no puede estar vacío.", Status.BAD_REQUEST);
        }

        // Si todo está bien
        return null;
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
     
}
