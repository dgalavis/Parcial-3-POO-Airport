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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author lhaur
 */
public class FlightValidator {
    public static Response parseAndValidate(
            String flightId, String originId, String destinationId,
            String dateStr, String timeStr, String limitStr, String planeId,
            FlightRepository flightRepo, LocationRepository locationRepo, PlaneRepository planeRepo,
            boolean isUpdate
    ) {
        // Validar campos vacíos
        if (isNullOrEmpty(flightId)) return new Response("El ID del vuelo no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(originId)) return new Response("El ID de origen no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(destinationId)) return new Response("El ID de destino no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(dateStr)) return new Response("La fecha no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(timeStr)) return new Response("La hora no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(limitStr)) return new Response("El límite de pasajeros no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(planeId)) return new Response("El ID del avión no puede estar vacío.", Status.BAD_REQUEST);

        // Validar formato ID vuelo
        if (!flightId.matches("[A-Z]{3}[0-9]{3}")) {
            return new Response("El ID del vuelo debe tener formato XXX123.", Status.BAD_REQUEST);
        }

        // Verificar unicidad del vuelo (si es creación)
        if (!isUpdate && flightRepo.getFlight(flightId) != null) {
            return new Response("Ya existe un vuelo con ese ID.", Status.BAD_REQUEST);
        }

        // Validar origen ≠ destino
        if (originId.equals(destinationId)) {
            return new Response("El origen y destino no pueden ser iguales.", Status.BAD_REQUEST);
        }

        // Verificar origen y destino existen
        Location origin = locationRepo.getLocation(originId);
        if (origin == null) return new Response("El aeropuerto de origen no existe.", Status.BAD_REQUEST);
        Location destination = locationRepo.getLocation(destinationId);
        if (destination == null) return new Response("El aeropuerto de destino no existe.", Status.BAD_REQUEST);

        // Verificar avión existe
        Plane plane = planeRepo.getPlane(planeId);
        if (plane == null) return new Response("El avión especificado no existe.", Status.BAD_REQUEST);

        // Validar fecha
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
            if (date.isBefore(LocalDate.now())) {
                return new Response("La fecha debe ser futura o actual.", Status.BAD_REQUEST);
            }
        } catch (DateTimeParseException e) {
            return new Response("Formato de fecha inválido. Debe ser YYYY-MM-DD.", Status.BAD_REQUEST);
        }

        // Validar hora
        LocalTime time;
        try {
            time = LocalTime.parse(timeStr);
        } catch (DateTimeParseException e) {
            return new Response("Formato de hora inválido. Debe ser HH:mm.", Status.BAD_REQUEST);
        }

        // Validar límite de pasajeros
        int limit;
        try {
            limit = Integer.parseInt(limitStr);
            if (limit <= 0 || limit > plane.getCapacity()) {
                return new Response("El límite debe ser entre 1 y la capacidad del avión (" + plane.getCapacity() + ").", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("El límite de pasajeros debe ser numérico.", Status.BAD_REQUEST);
        }

        // Si todo está bien, crear el vuelo
        Flight flight = new Flight(flightId, origin, destination, date, time, limit, plane);
        return new Response("Validación exitosa.", Status.OK, flight);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
