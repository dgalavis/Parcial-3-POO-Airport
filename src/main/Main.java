/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import core.data.JsonDataLoader;
import core.models.Flight;
import core.models.Plane;
import core.models.storage.AirportStorage;

/**
 *
 * @author galav
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hola");
        // Cargar datos desde JSON
        JsonDataLoader loader = new JsonDataLoader();
        loader.loadAllData();

        // Obtener el almacenamiento
        AirportStorage storage = AirportStorage.getInstance();

        // ✅ Imprimir algunos datos de prueba
        System.out.println("=== Aviones cargados ===");
        for (Plane p : storage.getPlaneRepo().getAllPlanes()) {
            System.out.println("ID: " + p.getId() + ", Marca: " + p.getBrand());
            System.out.println("Vuelos asignados: " + p.getFlights().size());
        }

        System.out.println("\n=== Vuelos cargados ===");
        for (Flight f : storage.getFlightRepository().getAllFlights()) {
            System.out.println("Vuelo: " + f.getId() + ", Avión: " + f.getPlane().getId());
            System.out.println("Desde: " + f.getDepartureLocation().getAirportId() +
                               " → Hacia: " + f.getArrivalLocation().getAirportId());
        }
    }
}
