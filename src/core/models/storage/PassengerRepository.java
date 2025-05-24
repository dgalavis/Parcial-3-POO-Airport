/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Passenger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author galav
 */
public class PassengerRepository extends ObservableRepository {
    
    private  List<Passenger> passengers= new ArrayList<>();
     
    // =====================================
    // MÉTODOS PARA PASAJEROS
    // =====================================
    
    public boolean addPassenger(Passenger passenger) {
        for (Passenger p : this.passengers) {
            if (p.getId() == passenger.getId()) {
                return false;
            }
        }
        //notifyObservers(); // Notifica a la vista
        this.passengers.add(passenger);
         notifyObservers(); // Notifica a la vista
        return true;
    }
    
   
    public Passenger getPassengerRaw(Long id) {
        for (Passenger p : passengers) {
            if (p.getId() == id) return p; // Referencia real
        }
        return null;
    }

    public Passenger getPassenger(Long id) {
        for (Passenger p : passengers) {
            if (p.getId() == id) return p.clone(); // Patrón Prototype
        }
        return null;
    }
    
    public boolean delPassenger(Long id) {
        for (Passenger passenger : this.passengers) {
            if (passenger.getId() == id) {
                this.passengers.remove(passenger);
                return true;
            }
        }
        return false;
    }
    
    public List<Passenger> getAllPassengers() {
    List<Passenger> sortedList = new ArrayList<>();

    // Clonar todos los pasajeros
    for (Passenger p : passengers) {
        sortedList.add(p.clone());
    }

    // Ordenar por ID usando Comparator
    Collections.sort(sortedList, new Comparator<Passenger>() {
        @Override
        public int compare(Passenger p1, Passenger p2) {
           return Long.compare(p1.getId(), p2.getId());        }
    });

    return sortedList;
    }
    
   public boolean updatePassenger(Passenger updated) {
    for (int i = 0; i < passengers.size(); i++) {
        if (passengers.get(i).getId() == updated.getId()) {
            passengers.set(i, updated);
            notifyObservers(); // Notifica a la vista
            return true;
        }
    }
    return false;
}

}
