/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tableLists;

import core.models.interfaces.Observer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author galav
 */
public class TableRefresherObserver implements Observer {
    private Runnable updateFunction;

    public TableRefresherObserver(Runnable updateFunction) {
        this.updateFunction = updateFunction;
    }

    @Override
    public void update() {
        updateFunction.run();
    }
}
