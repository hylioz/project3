package com.epam.threads.service;

import com.epam.threads.entity.Dock;
import com.epam.threads.entity.Port;
import com.epam.threads.entity.Ship;
import org.apache.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PortManager {
    private static final Logger LOGGER = Logger.getLogger(PortManager.class);

    private Semaphore portSemaphore;
    private ReentrantLock lock = new ReentrantLock();
    private Port port;

    public PortManager(Semaphore portSemaphore, Port port) {
        this.portSemaphore = portSemaphore;
        this.port = port;
    }

    public void initShip(Ship ship) {
        if (ship.getCurrentCargo() < ship.getMaxCapacity() / 2) {
            load(ship);
        } else unload(ship);
    }

    private void load(Ship ship) {
        try {
            portSemaphore.acquire();
            LOGGER.info("Ship " + Thread.currentThread().getName() + " come to port{load}");
            lock.lock();
            int portCurrentCargo = port.getCurrentCargo();
            int shipMaxCapacity = ship.getMaxCapacity();
            if (portCurrentCargo >= shipMaxCapacity / 2) {//if the port has enough cargo for ship
                for (Dock dock : port.getDocks()) {
                    if (!dock.isBusy()) {//if there is a free dock
                        dock.setBusy(true);
                        LOGGER.info("Ship " + Thread.currentThread().getName() + " loading...");
                        if (portCurrentCargo > shipMaxCapacity) {
                            ship.setCurrentCargo(shipMaxCapacity);
                            port.setCurrentCargo(portCurrentCargo - shipMaxCapacity);
                        } else {
                            ship.setCurrentCargo(portCurrentCargo);
                            port.setCurrentCargo(0);
                        }
                        Thread.sleep(2000);
                        LOGGER.info("Ship " + Thread.currentThread().getName() + " loaded.");
                        dock.setBusy(false);
                        break;
                    } else {
                        LOGGER.info("No free docks to load ship " + Thread.currentThread().getName() + ".");
                        portSemaphore.release();
                        lock.unlock();
                        Thread.sleep(2100);
                        load(ship);
                    }
                }
            } else {
                LOGGER.info("Not enough cargo to load ship " + Thread.currentThread().getName() + ".");
                portSemaphore.release();
                lock.unlock();
                Thread.sleep(500);
                load(ship);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error acquire semaphore permission: " + e);
            Thread.currentThread().interrupt();
        } finally {
            portSemaphore.release();
            lock.unlock();
        }
    }

    private void unload(Ship ship) {
        try {
            portSemaphore.acquire();
            LOGGER.info("Ship " + Thread.currentThread().getName() + " come to port{unload}");
            lock.lock();
            int portCurrentCapacity = port.getMaxCapacity() - port.getCurrentCargo();
            int shipCurrentCargo = ship.getCurrentCargo();
            int portCurrentCargo = port.getCurrentCargo();
            if (portCurrentCapacity >= shipCurrentCargo / 2) {//if the port has place for ship cargo
                for (Dock dock : port.getDocks()) {
                    if (!dock.isBusy()) {//if there is a free dock
                        dock.setBusy(true);
                        LOGGER.info("Ship " + Thread.currentThread().getName() + " unloading...");
                        if (portCurrentCapacity > shipCurrentCargo) {
                            ship.setCurrentCargo(0);
                            port.setCurrentCargo(portCurrentCargo + shipCurrentCargo);
                        } else {
                            ship.setCurrentCargo(shipCurrentCargo - portCurrentCapacity);
                            port.setCurrentCargo(port.getMaxCapacity());
                        }
                        Thread.sleep(2000);
                        LOGGER.info("Ship " + Thread.currentThread().getName() + " unloaded");
                        dock.setBusy(false);
                        break;
                    } else {
                        LOGGER.info("No free docks to unload ship " + Thread.currentThread().getName() + ".");
                        portSemaphore.release();
                        lock.unlock();
                        Thread.sleep(2100);
                        load(ship);
                    }
                }
            } else {
                LOGGER.info("Not enough capacity to unload ship " + Thread.currentThread().getName() + ".");
                portSemaphore.release();
                lock.unlock();
                Thread.sleep(500);
                load(ship);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error acquire semaphore permission: " + e);
            Thread.currentThread().interrupt();
        } finally {
            portSemaphore.release();
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "PortManager{" +
                "portSemaphore=" + portSemaphore +
                ", lock=" + lock +
                ", port=" + port +
                '}';
    }
}
