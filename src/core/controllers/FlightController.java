/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.FlightValidator;
import core.models.Flight;
import core.models.Passenger;
import core.models.storage.AirportStorage;
import core.models.storage.FlightRepository;
import core.models.storage.LocationRepository;
import core.models.storage.PassengerRepository;
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

        int hours = Integer.parseInt(hoursStr);
        int minutes = Integer.parseInt(minutesStr);

        Flight flight = flightRepo.getFlightRaw(flightId);

        if (flight == null) {
            return new Response("Vuelo no encontrado para retrasar.", Status.NOT_FOUND);
        }
        //eliminar los println
        System.out.println("ANTES de delay: " + flight.getDepartureDate());
        flight.setDepartureDate(flight.getDepartureDate().plusHours(hours).plusMinutes(minutes));
        System.out.println("DESPUÉS de delay: " + flight.getDepartureDate());

        return new Response("Vuelo retrasado correctamente.", Status.OK, flight.clone());
    }
    
    public Response addPassengerToFlight(long passengerId, String flightId) {
        PassengerRepository passengerRepo = AirportStorage.getInstance().getPassengerRepo();
        Passenger passenger = passengerRepo.getPassengerRaw(passengerId); 

        if (passenger == null) {
            return new Response("Pasajero no encontrado.", Status.NOT_FOUND);
        }

        Flight flight = flightRepo.getFlightRaw(flightId);
        if (flight == null) {
            return new Response("Vuelo no encontrado.", Status.NOT_FOUND);
        }

        boolean yaTieneVuelo = passenger.getFlights().stream()
            .anyMatch(f -> f.getId().equals(flightId));

        if (yaTieneVuelo) {
            return new Response("Este pasajero ya está registrado en ese vuelo.", Status.BAD_REQUEST);
        }

        passenger.addFlight(flight);
        flight.addPassenger(passenger);

        return new Response("Pasajero añadido correctamente al vuelo.", Status.OK);
    }
 
}
