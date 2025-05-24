/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Plane;
import core.models.storage.AirportStorage;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galav
 */
public class PlaneTableList {
    
    public static Response updatePlanesList(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            List<Plane> planes = AirportStorage.getInstance().getPlaneRepo().getAllPlanes();

            if (planes.isEmpty()) {
                return new Response("No hay aviones registrados.", Status.NO_CONTENT);
            }

            for (Plane p : planes) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getBrand(),
                    p.getModel(),
                    p.getMaxCapacity(),
                    p.getAirline(),
                    p.getFlights().size() 
                });
            }

            return new Response("Tabla de aviones actualizada correctamente.", Status.OK);
        } catch (Exception e) {
            return new Response("Error inesperado al actualizar la tabla de aviones.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
