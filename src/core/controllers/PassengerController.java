/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.PassengerValidator;
import core.models.Flight;
import core.models.Passenger;
import core.models.storage.AirportStorage;
import core.models.storage.FlightRepository;
import core.models.storage.PassengerRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lhaur
 */
public class PassengerController {
  
   public static Response registerPassenger(String idStr, String firstname, String lastname,
                                             String birthStr, String phoneCodeStr, String phoneStr,
                                             String country) {
        PassengerRepository repo = AirportStorage.getInstance().getPassengerRepo();

        // Validar y parsear todos los datos de entrada
        Response response = PassengerValidator.parseAndValidate(
                idStr, firstname, lastname, birthStr,
                phoneCodeStr, phoneStr, country,
                repo, false // false porque es registro, no actualización
        );

        if (response.getStatus() != Status.OK) return response;

        // Extraer pasajero ya validado y parseado
        Passenger p = (Passenger) response.getObject();

        // Agregar al repositorio
        boolean added = repo.addPassenger(p);
        if (!added) {
            return new Response("No se pudo registrar el pasajero (ID duplicado).", Status.BAD_REQUEST);
        }
       
        return new Response("Pasajero registrado exitosamente.", Status.CREATED);
    }
   // NUEVO: método para actualizar pasajero
    public static Response updatePassenger(String idStr, String firstname, String lastname,String birthStr, String phoneCodeStr, String phoneStr,String country) {
        PassengerRepository repo = AirportStorage.getInstance().getPassengerRepo();

        // Validar y parsear los datos (en modo actualización)
        Response response = PassengerValidator.parseAndValidate(
                idStr, firstname, lastname, birthStr,
                phoneCodeStr, phoneStr, country,
                repo, true // true porque es actualización
        );

        if (response.getStatus() != Status.OK) return response;

        Passenger p = (Passenger) response.getObject();

        // Verificar que el pasajero exista realmente
        if (repo.getPassenger(p.getId()) == null) {
            return new Response("El pasajero con ese ID no existe.", Status.NOT_FOUND);
        }

        boolean updated = repo.updatePassenger(p);

        if (!updated) {
            return new Response("No se pudo actualizar el pasajero.", Status.INTERNAL_SERVER_ERROR);
        }
       
        return new Response("Pasajero actualizado exitosamente.", Status.OK);
    }
    
    public static List<Flight> getFlightsByPassengerId(long passengerId) {
        PassengerRepository repo = AirportStorage.getInstance().getPassengerRepo();
        Passenger passenger = repo.getPassenger(passengerId);
        if (passenger != null) {
            return passenger.getFlights(); // asumo que tienes un getter que devuelve List<Flight>
        }
        return Collections.emptyList(); // si no existe pasajero, retorna lista vacía
    }
    
}
