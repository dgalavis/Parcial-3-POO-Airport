/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.PassengerValidator;
import core.models.Passenger;
import core.models.storage.PassengerRepository;

/**
 *
 * @author lhaur
 */
public class PassengerController {
   private final PassengerRepository repo;

    public PassengerController(PassengerRepository repo) {
        this.repo = repo;
    }

    // REGISTRAR PASAJERO
    public Response registerPassenger(Passenger p) {
        Response validation = PassengerValidator.validate(p, repo, false);
        if (validation != null) return validation;

        boolean added = repo.addPassenger(p);
        if (!added) {
            return new Response("No se pudo registrar el pasajero. Ya existe.", Status.BAD_REQUEST);
        }

        return new Response("Pasajero registrado exitosamente.", Status.CREATED, p.clone());
    }

    // ACTUALIZAR PASAJERO
    public Response updatePassenger(Passenger updated) {
        if (repo.getPassenger(updated.getId()) == null) {
            return new Response("El pasajero no existe.", Status.NOT_FOUND);
        }

        Response validation = PassengerValidator.validate(updated, repo, true);
        if (validation != null) return validation;

        boolean updatedOK = repo.updatePassenger(updated);
        if (!updatedOK) {
            return new Response("Error al actualizar el pasajero.", Status.INTERNAL_SERVER_ERROR);
        }

        return new Response("Pasajero actualizado correctamente.", Status.OK, updated.clone());
    }

    // OBTENER TODOS LOS PASAJEROS ORDENADOS POR ID
    public Response getAllPassengers() {
        return new Response("Pasajeros obtenidos correctamente.", Status.OK, repo.getAllPassengers());
    }
}
