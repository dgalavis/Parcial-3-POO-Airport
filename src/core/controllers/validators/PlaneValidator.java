/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.validators;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Plane;
import core.models.storage.PlaneRepository;

/**
 *
 * @author galav
 */
public class PlaneValidator {
    public static Response parseAndValidate(String id, String brand, String model, String maxCapacityStr, String airline, PlaneRepository repo) {
        // 1. Validar campos vacíos
        if (isNullOrEmpty(id)) return new Response("El ID no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(brand)) return new Response("La marca no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(model)) return new Response("El modelo no puede estar vacío.", Status.BAD_REQUEST);
        if (isNullOrEmpty(maxCapacityStr)) return new Response("La capacidad no puede estar vacía.", Status.BAD_REQUEST);
        if (isNullOrEmpty(airline)) return new Response("La aerolínea no puede estar vacía.", Status.BAD_REQUEST);

        // 2. Validar formato del ID
        if (!isValidPlaneId(id)) {
            return new Response("El ID del avión debe tener el formato XXYYYYY (2 letras mayúsculas seguidas de 5 dígitos).", Status.BAD_REQUEST);
        }

        // 3. Validar unicidad del ID
        if (repo.getPlane(id) != null) {
            return new Response("Ya existe un avión con ese ID.", Status.BAD_REQUEST);
        }

        // 4. Validar capacidad
        int capacity;
        try {
            capacity = Integer.parseInt(maxCapacityStr);
            if (capacity <= 0) {
                return new Response("La capacidad debe ser un número mayor que cero.", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("La capacidad debe ser un número válido.", Status.BAD_REQUEST);
        }

        // 5. Crear objeto Plane si todo es válido
        Plane plane = new Plane(id, brand, model, capacity, airline);
        return new Response("Validación exitosa", Status.OK, plane);
        }

        private static boolean isNullOrEmpty(String s) {
            return s == null || s.trim().isEmpty();
        }

        private static boolean isValidPlaneId(String id) {
            if (id == null || id.length() != 7) return false;

            char c1 = id.charAt(0);
            char c2 = id.charAt(1);
            if (!Character.isUpperCase(c1) || !Character.isUpperCase(c2)) {
                return false;
            }

            for (int i = 2; i < 7; i++) {
                if (!Character.isDigit(id.charAt(i))) {
                    return false;
                }
            }

            return true;
        }

}
