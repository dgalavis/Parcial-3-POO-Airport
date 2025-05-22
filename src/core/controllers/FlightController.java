/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.FlightValidator;
import core.models.Flight;
import core.models.storage.AirportStorage;
import core.models.storage.FlightRepository;
import core.models.storage.LocationRepository;
import core.models.storage.PlaneRepository;

/**
 *
 * @author lhaur
 */
public class FlightController {
    private final FlightRepository flightRepo;
    private final LocationRepository locationRepo;
    private final PlaneRepository planeRepo;

    public FlightController(FlightRepository flightRepo, LocationRepository locationRepo, PlaneRepository planeRepo) {
        this.flightRepo = flightRepo;
        this.locationRepo = locationRepo;
        this.planeRepo = planeRepo;
    }

    public Response createFlight(
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
        String minutesScaleStr
    ) {
        // Validar datos de entrada
        Response validation = FlightValidator.parseAndValidate(
            id,
            departureLocationId,
            arrivalLocationId,
            departureDateStr,
            departureTimeStr,
            hoursArrivalStr,
            minutesArrivalStr,
            planeId,
            scaleLocationId,
            hoursScaleStr,
            minutesScaleStr,
            flightRepo,
            locationRepo,
            planeRepo,
            false // No es actualización, es creación
        );

        if (validation.getStatus() != Status.OK) {
            return validation;
        }

        // Obtener vuelo validado y listo
        Flight flight = (Flight) validation.getObject();

        // Agregar al repositorio
        boolean added = flightRepo.addFlight(flight);
        if (!added) {
            return new Response("No se pudo registrar el vuelo.", Status.INTERNAL_SERVER_ERROR);
        }

        return new Response("Vuelo registrado exitosamente.", Status.CREATED, flight.clone());
    }
    
    
    
    public Response delayFlight(String flightId, String hoursStr, String minutesStr) {
    Response validation = FlightValidator.validateDelay(flightId, hoursStr, minutesStr, flightRepo);

    if (validation.getStatus() != Status.OK) {
        return validation;
    }

    Flight updated = (Flight) validation.getObject();

    return new Response("Vuelo retrasado correctamente.", Status.OK, updated.clone());
}

    
}
