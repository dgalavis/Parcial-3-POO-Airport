/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.LocationValidator;
import core.models.Location;
import core.models.storage.LocationRepository;

/**
 *
 * @author lhaur
 */
public class LocationController {
    
    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    // Crear ubicación
    public Response createLocation(String id, String name, String city, String country, String latitude, String longitude) {
        // Validar y parsear los datos
        Response validation = LocationValidator.parseAndValidate(id, name, city, country, latitude, longitude, repository, false);

        if (validation.getStatus() != Status.OK) {
            return validation;
        }

        Location location = (Location) validation.getObject();
        repository.addLocation(location);
        return new Response("Ubicación registrada exitosamente.", Status.CREATED, location.clone());
    }

    // Obtener todas las ubicaciones ordenadas por ID
    public Response getAllLocations() {
        var list = repository.getAllLocations();
        if (list.isEmpty()) {
            return new Response("No hay ubicaciones registradas.", Status.NO_CONTENT);
        }
        return new Response("Ubicaciones obtenidas exitosamente.", Status.OK, list);
    }

    // Obtener una ubicación por ID
    public Response getLocationById(String id) {
        Location location = repository.getLocation(id);
        if (location == null) {
            return new Response("Ubicación no encontrada.", Status.NOT_FOUND);
        }
        return new Response("Ubicación encontrada.", Status.OK, location);
    }

}
