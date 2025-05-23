/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.storage.AirportStorage;
import core.models.utils.FlightCalculations;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lhaur
 */
public class FlightTableList {
    public static Response updateFlightsList(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            List<Flight> flights = AirportStorage.getInstance().getFlightRepository().getAllFlights();

            if (flights.isEmpty()) {
                return new Response("No hay vuelos registrados.", Status.NO_CONTENT);
            }
            
            FlightCalculations flightCalculations = new FlightCalculations();

            for (Flight f : flights) {
                model.addRow(new Object[]{
                    f.getId(),
                    f.getDepartureLocation().getAirportCity(),
                    f.getArrivalLocation().getAirportCity(),
                    f.getScaleLocation() != null ? f.getScaleLocation().getAirportCity() : "No scale",
                    f.getDepartureDate(),
                    flightCalculations.calculateArrivalDate(f),  
                    f.getPlane().getId(),
                    f.getNumPassengers(),
                });
            }

            return new Response("Lista de vuelos cargada correctamente.", Status.OK);
        } catch (Exception e) {
            return new Response("Error inesperado al cargar la lista de vuelos.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
