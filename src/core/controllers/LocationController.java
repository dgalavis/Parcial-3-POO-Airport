/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.models.Location;
import core.models.storage.LocationRepository;

/**
 *
 * @author lhaur
 */
public class LocationController {
    private LocationRepository locationRepository; //Para guardar y buscar datos}

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Response registerLocation(Location location) {
        if (location == null) {
            return new Response(false, "La localización no puede ser nula.", location.clone());
        }

        String id = location.getAirportId();
        if (id == null || !id.matches("[A-Z]{3}")) {
            return new Response(false, "El ID del aeropuerto debe tener exactamente 3 letras mayúsculas.", location.clone());
        }

        if (locationRepository.findById(id) != null) {
            return new Response(false, "Ya existe una localización con ese ID.", location.clone());
        }

        if (location.getAirportName().isBlank() ||
            location.getAirportCity().isBlank() ||
            location.getAirportCountry().isBlank()) {
            return new Response(false, "Nombre, ciudad y país no deben estar vacíos.", location.clone());
        }

        double lat = location.getAirportLatitude();
        double lon = location.getAirportLongitude();

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            return new Response(false, "Latitud debe estar entre -90 y 90 y longitud entre -180 y 180.", location.clone());
        }

        boolean added = locationRepository.addLocation(location.clone());

        if (added) {
            return new Response(true, "Localización registrada correctamente.", location.clone());
        } else {
            return new Response(false, "No se pudo registrar la localización.", location.clone());
        }
    }

    // Obtener una localización por ID
    public Location getLocation(String id) {
        return locationRepository.getLocation(id); // ya retorna copia
    }

    // Obtener todas las localizaciones ordenadas por ID
    public java.util.List<Location> getAllLocations() {
        return locationRepository.getAllLocations();
    }
    

}
