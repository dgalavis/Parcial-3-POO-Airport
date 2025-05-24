/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Plane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author galav
 */
public class PlaneRepository extends ObservableRepository {
     private  List<Plane> planes= new ArrayList<>();
     
    
    public boolean addPlane(Plane plane) {
        for (Plane p : this.planes) {
            if (p.getId() == plane.getId()) {
                return false;
            }
        }
       
        this.planes.add(plane);
         notifyObservers(); 
        return true;
    }
    
   public Plane getPlane(String id) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) {
                return p.clone();
            }
        }
        return null;
    }
    
    public boolean delPlane(Long id) {
        for (Plane p : this.planes) {
            if (p.getId().equals(id)) {
                this.planes.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public List<Plane> getAllPlanes() {
    List<Plane> sortedList = new ArrayList<>();

 
    for (Plane p : planes) {
        sortedList.add(p.clone());
    }

    // Ordenar por Id
    Collections.sort(sortedList, new Comparator<Plane>() {        @Override
        public int compare(Plane p1, Plane p2) {
            return p1.getId().compareTo(p2.getId());       }
    });

    return sortedList;
    }
    
    
    
}
