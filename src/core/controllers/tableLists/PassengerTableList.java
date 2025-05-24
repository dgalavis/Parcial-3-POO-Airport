/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.AirportStorage;
import core.models.utils.PassengerCalculations;
import core.models.utils.PassengerFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galav
 */
public class PassengerTableList {
    public static Response updatePassengersList(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            List<Passenger> passengers = AirportStorage.getInstance().getPassengerRepo().getAllPassengers();

            if (passengers.isEmpty()) {
                return new Response("No hay pasajeros registrados.", Status.NO_CONTENT);
            }

            for (Passenger p : passengers) {
                model.addRow(new Object[]{
                    p.getId(), 
                    PassengerFormatter.getFullname(p), 
                    p.getBirthDate(), 
                    PassengerCalculations.calculateAge(p),
                    PassengerFormatter.generateFullPhone(p), 
                    p.getCountry(), 
                    PassengerCalculations.getNumFlights(p) 
                });
            }

            return new Response("Lista de pasajeros cargada exitosamente.", Status.OK);
        } catch (Exception e) {
            return new Response("Error inesperado al cargar la lista de pasajeros.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
