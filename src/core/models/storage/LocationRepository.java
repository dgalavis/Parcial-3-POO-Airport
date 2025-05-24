/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author galav
 */
public class LocationRepository extends ObservableRepository {
    private  List<Location> locations= new ArrayList<>();

    public boolean addLocation(Location location) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }
        this.locations.add(location);
        notifyObservers(); 
        return true;
    }

    public Location getLocation(String id) {
        for (Location l : locations) {
            if (l.getAirportId().equals(id)) {
                return l.clone();
            }
        }
        return null;
    }

    public boolean delLocation(String id) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(id)) {
                this.locations.remove(l);
                return true;
            }
        }
        return false;
    }

    public List<Location> getAllLocations() {
        List<Location> sortedList = new ArrayList<>();

        for (Location l : locations) {
            sortedList.add(l.clone());
        }

        Collections.sort(sortedList, new Comparator<Location>() {
            @Override
            public int compare(Location l1, Location l2) {
                return l1.getAirportId().compareTo(l2.getAirportId());
            }
        });

        return sortedList;
    }
   
}
