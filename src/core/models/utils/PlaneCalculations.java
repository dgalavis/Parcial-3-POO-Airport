/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.utils;

import core.models.Plane;

/**
 *
 * @author galav
 */
public class PlaneCalculations {
    public static int getNumFlights(Plane plane) {
        return plane.getFlights().size();
    }
}
