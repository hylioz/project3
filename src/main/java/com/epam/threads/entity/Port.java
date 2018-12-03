package com.epam.threads.entity;

import java.util.ArrayList;
import java.util.List;

public class Port {
    private int maxCapacity;
    private int currentCargo;
    private ArrayList<Dock> docks;

    public Port(int maxCapacity, int currentCargo, ArrayList<Dock> docks) {
        this.maxCapacity = maxCapacity;
        this.currentCargo = currentCargo;
        this.docks = docks;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(int currentCargo) {
        this.currentCargo = currentCargo;
    }

    public List<Dock> getDocks() {
        return docks;
    }

    public void setDocks(ArrayList<Dock> docks) {
        this.docks = docks;
    }

    @Override
    public String toString() {
        return "Port{" +
                "maxCapacity=" + maxCapacity +
                ", currentCargo=" + currentCargo +
                ", docks=" + docks +
                '}';
    }
}
