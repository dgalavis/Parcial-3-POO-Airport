/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.storage;

import core.model.Flight;
import core.model.Location;
import core.model.Passenger;
import core.model.Plane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author galav
 */
/**
 * Esta clase simula el almacenamiento de todos los objetos del modelo.
 * Aplica el patrón Singleton para que solo exista una instancia.
 * Almacena listas de pasajeros, vuelos, aviones y localizaciones.
 */
public class AirportStorage {
    /*Singleton: única instancia garantiza que toda la aplicación utlice
    el mismo repositorio de datos
    */
    private static AirportStorage instance;
    
    /*Devuelve una única instancia de AirportStorage
    Si no existe la crea ->> es la unica forma de obtner
    una instancia de airport storgae como tipo para acceder 
    a una base de datos
    */
    public static AirportStorage getInstance (){
        if (instance == null){
            instance = new AirportStorage();
        }
        return instance;
    }
    
    private final PassengerRepository passengerRepo;
    private final PlaneRepository planeRepo;
    private final FlightRepository flightRepo;
    private final LocationRepository locationRepo;

    public AirportStorage() {
        this.passengerRepo = new PassengerRepository();
        this.planeRepo = new PlaneRepository();
        this.flightRepo = new FlightRepository();
        this.locationRepo = new LocationRepository();
        
    }
    
    // Métodos de acceso a los repositorios
    public PassengerRepository getPassengerRepo() {
        return passengerRepo;
    }
    
    public PlaneRepository getPlaneRepo() {
        return planeRepo;
    }
    public FlightRepository getFlightRepository() {
        return flightRepo;
    }
    public LocationRepository getLocationRepository() {
        return locationRepo;
    }
    
}

    
