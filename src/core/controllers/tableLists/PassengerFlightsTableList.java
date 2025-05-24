/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Passenger;
import core.models.storage.AirportStorage;
import core.models.utils.FlightCalculations;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lhaur
 */
public class PassengerFlightsTableList {
    public static Response updatePassengerFlightsList(DefaultTableModel model, String passengerId) {
    try {
            model.setRowCount(0);
            long Id = Long.parseLong(passengerId);

            Passenger passenger = AirportStorage.getInstance().getPassengerRepo().getPassenger(Id);
            if (passenger == null) {
                return new Response("Pasajero no encontrado.", Status.NOT_FOUND);
            }

            List<Flight> flights = passenger.getFlights();
            if (flights == null || flights.isEmpty()) {
                return new Response("El pasajero no tiene vuelos asignados.", Status.NO_CONTENT);
            }

            FlightCalculations flightCalculations = new FlightCalculations();

            for (Flight f : flights) {
                model.addRow(new Object[]{
                    f.getId(),
                    f.getDepartureDate(),
                    flightCalculations.calculateArrivalDate(f)
                });
            }

            return new Response("Lista de vuelos del pasajero cargada correctamente.", Status.OK);

        } catch (Exception e) {
            return new Response("Error inesperado al cargar vuelos del pasajero.", Status.INTERNAL_SERVER_ERROR);
        }

    }
}
