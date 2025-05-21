/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.storage.AirportStorage;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galav
 */
public class LocationTableList {
      public static Response updateLocationsList(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            List<Location> locations = AirportStorage.getInstance().getLocationRepository().getAllLocations();

            if (locations.isEmpty()) {
                return new Response("No hay aeropuertos registrados.", Status.NO_CONTENT);
            }

            for (Location loc : locations) {
                model.addRow(new Object[]{
                    loc.getAirportId(),
                    loc.getAirportName(),
                    loc.getAirportCity(),
                    loc.getAirportCountry()
                });
            }

            return new Response("Tabla de aeropuertos actualizada correctamente.", Status.OK);
        } catch (Exception e) {
            return new Response("Error al cargar aeropuertos.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
