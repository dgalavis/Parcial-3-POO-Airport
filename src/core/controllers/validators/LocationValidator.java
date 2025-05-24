/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.storage.LocationRepository;

/**
 *
 * @author lhaur
 */
public class LocationValidator {
    public static Response parseAndValidate(String id,String name,String city,String country,String latStr,String lonStr,LocationRepository repo,boolean isUpdate) {
        // Validación de campos vacíos
        if (isNullOrEmpty(id)) return new Response("El ID no puede estar vacío.", Status.BAD_REQUEST);
        if (!id.matches("[A-Z]{3}")) return new Response("El ID debe tener exactamente 3 letras mayúsculas.", Status.BAD_REQUEST);
        if (isNullOrEmpty(name)) return new Response("El nombre no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(city)) return new Response("La ciudad no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(country)) return new Response("El país no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(latStr)) return new Response("La latitud no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(lonStr)) return new Response("La longitud no puede estar vacía.", Status.BAD_REQUEST);

        // Validación de latitud
        double latitude;
        try {
            latitude = Double.parseDouble(latStr);
            if (latitude < -90 || latitude > 90) {
                return new Response("La latitud debe estar entre -90 y 90.", Status.BAD_REQUEST);
            }
            if (!hasAtMostFourDecimals(latStr)) {
                return new Response("La latitud debe tener como máximo 4 cifras decimales.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("La latitud debe ser numérica.", Status.BAD_REQUEST);
        }

        // Validación de longitud
        double longitude;
        try {
            longitude = Double.parseDouble(lonStr);
            if (longitude < -180 || longitude > 180) {
                return new Response("La longitud debe estar entre -180 y 180.", Status.BAD_REQUEST);
            }
            if (!hasAtMostFourDecimals(lonStr)) {
                return new Response("La longitud debe tener como máximo 4 cifras decimales.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("La longitud debe ser numérica.", Status.BAD_REQUEST);
        }

        // Validación de ID único si es nuevo registro
        if (!isUpdate && repo.getLocation(id) != null) {
            return new Response("Ya existe una ubicación con ese ID.", Status.BAD_REQUEST);
        }

        // Crear ubicación válida
        Location location = new Location(id, name, city, country, latitude, longitude);
        return new Response("Validación exitosa.", Status.OK, location);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean hasAtMostFourDecimals(String numberStr) {
        if (!numberStr.contains(".")) return true;
        String[] parts = numberStr.split("\\.");
        return parts[1].length() <= 4;
    }

}
