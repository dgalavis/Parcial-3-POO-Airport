/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.data;

import core.models.Flight;
import core.models.Location;
import core.models.Passenger;
import core.models.Plane;
import core.models.storage.AirportStorage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author galav
 */
public class JsonDataLoader {
     private final AirportStorage storage = AirportStorage.getInstance();

    public void loadAllData()  {
        loadLocations();
        loadPlanes();
        loadPassengers();
        loadFlights();
    }

    private void loadLocations()  {
        try {
            // Ruta absoluta o relativa al archivo JSON
            FileReader reader = new FileReader("json/locations.json");

            // Leer el archivo y parsear como JSON
            JSONArray array = new JSONArray(new JSONTokener(reader));

            // Recorrer el array e instanciar los objetos Location
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Location loc = new Location(
                    obj.getString("airportId"),        // ← Verifica si esto es un typo (debería ser "airportId")
                    obj.getString("airportName"),
                    obj.getString("airportCity"),
                    obj.getString("airportCountry"),
                    obj.getDouble("airportLatitude"),
                    obj.getDouble("airportLongitude")
                );

                storage.getLocationRepository().addLocation(loc);
            }

            System.out.println("✅ Locations cargados correctamente.");

        } catch (Exception e) {
            System.out.println("❌ Error al cargar locations: " + e.getMessage());
        }
     
        
    }

     //@SuppressWarnings("empty-statement")
   private void loadPlanes() {
    try {
        FileReader reader = new FileReader("json/planes.json");
        JSONArray array = new JSONArray(new JSONTokener(reader));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Plane plane = new Plane(
                obj.getString("id"),
                obj.getString("brand"),
                obj.getString("model"),
                obj.getInt("maxCapacity"),
                obj.getString("airline")
            );
            storage.getPlaneRepo().addPlane(plane);
        }

        System.out.println("✅ Planes cargados correctamente.");
    } catch (Exception e) {
        System.out.println("❌ Error al cargar planes: " + e.getMessage());
    }
}


 private void loadPassengers() {
    try {
        FileReader reader = new FileReader("json/passengers.json");
        JSONArray array = new JSONArray(new JSONTokener(reader));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Passenger p = new Passenger(
                obj.getLong("id"),
                obj.getString("firstname"),
                obj.getString("lastname"),
                LocalDate.parse(obj.getString("birthDate")),
                obj.getInt("countryPhoneCode"),
                obj.getLong("phone"),
                obj.getString("country")
            );
            storage.getPassengerRepo().addPassenger(p);
        }

        System.out.println("✅ Pasajeros cargados correctamente.");
    } catch (Exception e) {
        System.out.println("❌ Error al cargar pasajeros: " + e.getMessage());
    }
}

    private void loadFlights() {
        try {
            FileReader reader = new FileReader("json/flights.json");
            JSONArray array = new JSONArray(new JSONTokener(reader));

            for (int i = 0; i < array.length(); i++) {
                JSONObject f = array.getJSONObject(i);

                Plane plane = storage.getPlaneRepo().getPlane(f.getString("plane"));
                Location dep = storage.getLocationRepository().getLocation(f.getString("departureLocation"));
                Location arr = storage.getLocationRepository().getLocation(f.getString("arrivalLocation"));
                Location scale = null;

                if (!f.isNull("scaleLocation")) {
                    scale = storage.getLocationRepository().getLocation(f.getString("scaleLocation"));
                }

                Flight flight;
                if (scale != null) {
                    flight = new Flight(
                        f.getString("id"),
                        plane, dep, scale, arr,
                        LocalDateTime.parse(f.getString("departureDate")),
                        f.getInt("hoursDurationArrival"),
                        f.getInt("minutesDurationArrival"),
                        f.getInt("hoursDurationScale"),
                        f.getInt("minutesDurationScale")
                    );
                } else {
                    flight = new Flight(
                        f.getString("id"),
                        plane, dep, arr,
                        LocalDateTime.parse(f.getString("departureDate")),
                        f.getInt("hoursDurationArrival"),
                        f.getInt("minutesDurationArrival")
                    );
                }

                if (plane != null) {
                    plane.addFlight(flight);
                }

                storage.getFlightRepository().addFlight(flight);
            }

            System.out.println("✅ Vuelos cargados correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al cargar vuelos: " + e.getMessage());
        }
    }


}
