package com.epam.threads.entity;

import com.epam.threads.service.PortManager;

public class Ship implements Runnable {
    private int maxCapacity;
    private int currentCargo;
    private PortManager portManager;

    public Ship(int maxCapacity, int currentCargo, PortManager portManager) {
        this.maxCapacity = maxCapacity;
        this.currentCargo = currentCargo;
        this.portManager = portManager;
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

    public PortManager getPortManager() {
        return portManager;
    }

    public void setPortManager(PortManager portManager) {
        this.portManager = portManager;
    }

    public void run() {
        portManager.initShip(this);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "maxCapacity=" + maxCapacity +
                ", currentCargo=" + currentCargo +
                ", portManager=" + portManager +
                '}';
    }
}
