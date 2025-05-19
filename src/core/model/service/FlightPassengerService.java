/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.model.service;

import core.model.Flight;
import core.model.Passenger;

/**
 *
 * @author lhaur
 */
public class FlightPassengerService {
    public void addPassenger(Flight flight, Passenger passenger) {
        flight.getPassengers().add(passenger);
    }

    public int getPassengerCount(Flight flight) {
        return flight.getPassengers().size();
    }
}
