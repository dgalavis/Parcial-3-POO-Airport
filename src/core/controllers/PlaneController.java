/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.controllers.validators.PlaneValidator;
import core.models.Plane;
import core.models.storage.AirportStorage;
import core.models.storage.PlaneRepository;

/**
 *
 * @author lhaur
 */
public class PlaneController {
    
    public static Response registerPlane(String id, String brand, String model, String maxCapacity, String airline) {
    PlaneRepository repo = AirportStorage.getInstance().getPlaneRepo();

    Response r = PlaneValidator.parseAndValidate(id, brand, model, maxCapacity, airline, repo);
    if (r.getStatus() != Status.OK) return r;

    Plane plane = (Plane) r.getObject();
    boolean added = repo.addPlane(plane);

    if (!added) {
        return new Response("No se pudo registrar el avión.", Status.INTERNAL_SERVER_ERROR);
    }
    
    return new Response("Avión registrado exitosamente.", Status.CREATED);
}

}
