/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.flightservice;

import core.models.Flight;
import java.time.LocalDateTime;

/**
 *
 * @author lhaur
 */
public class FlightCalculations {
    public LocalDateTime calculateArrivalDate(Flight flight) {
        return flight.getDepartureDate().plusHours(flight.getHoursDurationScale()).plusMinutes(flight.getMinutesDurationScale()).plusHours(flight.getHoursDurationArrival()).plusMinutes(flight.getMinutesDurationArrival());
    }
   
}
