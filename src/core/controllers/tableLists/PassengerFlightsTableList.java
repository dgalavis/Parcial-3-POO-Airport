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
   public static Response updatePassengerFlightsList(DefaultTableModel model, Passenger passenger) {
        try {
            model.setRowCount(0);  // Limpiar tabla

            if (passenger == null) {
                return new Response("No se proporcionó un pasajero válido.", Status.BAD_REQUEST);
            }

            List<Flight> flights = passenger.getFlights();

            if (flights == null || flights.isEmpty()) {
                JOptionPane.showMessageDialog(null,"No tiene vuelos registrados.","Pasajero sin vuelos",JOptionPane.INFORMATION_MESSAGE);
                return new Response("Pasajero sin vuelos.", Status.NO_CONTENT);
            }

            FlightCalculations flightCalculations = new FlightCalculations();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (Flight f : flights) {
                model.addRow(new Object[]{
                    f.getId(),
                    f.getDepartureDate().format(formatter),
                    flightCalculations.calculateArrivalDate(f).format(formatter)
                });
            }

            return new Response("Lista de vuelos del pasajero cargada correctamente.", Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("Error inesperado al cargar la lista del pasajero.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
