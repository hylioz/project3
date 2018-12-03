package com.epam.threads.service;

import com.epam.threads.entity.Port;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class StorageManager extends Thread {
    private ReentrantLock lock = new ReentrantLock();
    private Port port;
    private static final Logger LOGGER = Logger.getLogger(StorageManager.class);

    public StorageManager(Port port) {
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (port.getCurrentCargo() < port.getMaxCapacity() * 0.15) {
                //if the cargo is not enough
                port.setCurrentCargo(port.getMaxCapacity() / 4);
            }
            lock.unlock();
            lock.lock();
            if (port.getCurrentCargo() > port.getMaxCapacity() * 0.85) {
                //if the cargo is too much
                port.setCurrentCargo(port.getMaxCapacity() / 4);
            }
            lock.unlock();
            try {
                Thread.sleep(3000);
                //TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                LOGGER.error("Storage manager error");
                Thread.currentThread().interrupt();
            }
        }
    }
}