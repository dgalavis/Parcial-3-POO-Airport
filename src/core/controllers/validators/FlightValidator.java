/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.FlightRepository;
import core.models.storage.LocationRepository;
import core.models.storage.PlaneRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;

/**
 *
 * @author lhaur
 */
public class FlightValidator {
    public static Response parseAndValidate(
        String id,
        String departureLocationId,
        String arrivalLocationId,
        String departureDateStr,
        String departureTimeStr,
        String hoursArrivalStr,
        String minutesArrivalStr,
        String planeId,
        String scaleLocationId,
        String hoursScaleStr,
        String minutesScaleStr,
        FlightRepository flightRepo,
        LocationRepository locationRepo,
        PlaneRepository planeRepo,
        boolean isUpdate
    ) {
        // === Validar ID ===
        if (isNullOrEmpty(id)) return new Response("El ID no puede estar vacío.", Status.BAD_REQUEST);
        if (!id.matches("[A-Z]{3}[0-9]{3}")) return new Response("El ID debe tener el formato XXX123.", Status.BAD_REQUEST);
        if (!isUpdate && flightRepo.getFlight(id) != null) {
            return new Response("Ya existe un vuelo con ese ID.", Status.BAD_REQUEST);
        }

        // === Validar ubicaciones ===
        if (isNullOrEmpty(departureLocationId) || isNullOrEmpty(arrivalLocationId)) {
            return new Response("La ubicación de salida y llegada no pueden estar vacías.", Status.BAD_REQUEST);
        }
        if (departureLocationId.equals(arrivalLocationId)) {
            return new Response("La ubicación de salida y llegada no pueden ser iguales.", Status.BAD_REQUEST);
        }

        Location departure = locationRepo.getLocation(departureLocationId);
        if (departure == null) return new Response("La ubicación de salida no existe.", Status.BAD_REQUEST);

        Location arrival = locationRepo.getLocation(arrivalLocationId);
        if (arrival == null) return new Response("La ubicación de llegada no existe.", Status.BAD_REQUEST);

        // === Validar avión ===
        Plane plane = planeRepo.getPlane(planeId);
        if (plane == null) return new Response("El avión no existe.", Status.BAD_REQUEST);

        // === Validar fecha y hora de salida ===
        if (isNullOrEmpty(departureDateStr) || isNullOrEmpty(departureTimeStr)) {
            return new Response("La fecha y la hora de salida no pueden estar vacías.", Status.BAD_REQUEST);
        }

        LocalDateTime departureDateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d'T'H:m");
            departureDateTime = LocalDateTime.parse(departureDateStr + "T" + departureTimeStr, formatter);

            if (departureDateTime.isBefore(LocalDateTime.now())) {
                return new Response("La fecha de salida debe ser futura o actual.", Status.BAD_REQUEST);
            }
        } catch (DateTimeParseException e) {
            return new Response("Formato de fecha u hora inválido. Usa YYYY-MM-DD y HH:mm.", Status.BAD_REQUEST);
        }

        // === Validar duración llegada ===
        int hoursArrival, minutesArrival;
        try {
            hoursArrival = Integer.parseInt(hoursArrivalStr);
            minutesArrival = Integer.parseInt(minutesArrivalStr);
            if (hoursArrival < 0 || minutesArrival < 0 || minutesArrival > 59) {
                return new Response("Duración de llegada inválida.", Status.BAD_REQUEST);
            }
            if (hoursArrival == 0 && minutesArrival == 0) {
                return new Response("La duración de llegada no puede ser cero.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("La duración de llegada debe ser numérica.", Status.BAD_REQUEST);
        }

        // === Validar escala ===
        boolean hasScale = !isNullOrEmpty(scaleLocationId) && !scaleLocationId.equalsIgnoreCase("None");
        Location scale = null;
        int hoursScale = 0, minutesScale = 0;

        if (hasScale) {
            if (scaleLocationId.equals(departureLocationId) || scaleLocationId.equals(arrivalLocationId)) {
                return new Response("La escala no puede ser igual a la salida o llegada.", Status.BAD_REQUEST);
            }

            scale = locationRepo.getLocation(scaleLocationId);
            if (scale == null) return new Response("La ubicación de escala no existe.", Status.BAD_REQUEST);

            try {
                hoursScale = Integer.parseInt(hoursScaleStr);
                minutesScale = Integer.parseInt(minutesScaleStr);
                if (hoursScale < 0 || minutesScale < 0 || minutesScale > 59) {
                    return new Response("Duración de escala inválida.", Status.BAD_REQUEST);
                }
                if (hoursScale == 0 && minutesScale == 0) {
                    return new Response("La duración de la escala no puede ser cero.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("La duración de la escala debe ser numérica.", Status.BAD_REQUEST);
            }
        } else {
            // 🚀 Si no hay escala, forzar tiempo = 0
            hoursScale = 0;
            minutesScale = 0;
        }

        // === Crear el vuelo usando el constructor correcto ===
        Flight flight;
        if (hasScale) {
            flight = new Flight(
                id,
                plane,
                departure,
                scale,
                arrival,
                departureDateTime,
                hoursArrival,
                minutesArrival,
                hoursScale,
                minutesScale
            );
        } else {
            flight = new Flight(
                id,
                plane,
                departure,
                arrival,
                departureDateTime,
                hoursArrival,
                minutesArrival
            );
        }

        return new Response("Validación exitosa.", Status.OK, flight);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    // === Delay Validator ===
    public static Response validateDelay(String flightId, String hoursStr, String minutesStr, FlightRepository repo) {
        if (isNullOrEmpty(flightId)) {
            return new Response("El ID del vuelo no puede estar vacío.", Status.BAD_REQUEST);
        }

        Flight flight = repo.getFlight(flightId);
        if (flight == null) {
            return new Response("El vuelo no existe.", Status.NOT_FOUND);
        }

        int hours, minutes;
        try {
            hours = Integer.parseInt(hoursStr);
            minutes = Integer.parseInt(minutesStr);

            if (hours < 0 || minutes < 0 || minutes > 59) {
                return new Response("Horas o minutos inválidos.", Status.BAD_REQUEST);
            }

            if (hours == 0 && minutes == 0) {
                return new Response("El retraso no puede ser cero.", Status.BAD_REQUEST);
            }

        } catch (NumberFormatException e) {
            return new Response("Las horas y minutos deben ser numéricos.", Status.BAD_REQUEST);
        }

        // Modificar directamente el vuelo real
        flight.setDepartureDate(flight.getDepartureDate().plusHours(hours).plusMinutes(minutes));

        return new Response("Vuelo retrasado correctamente.", Status.OK, flight.clone());
    }
}
