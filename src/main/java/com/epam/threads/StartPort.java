package com.epam.threads;

import com.epam.threads.service.PortManager;
import com.epam.threads.service.StorageManager;
import com.epam.threads.entity.Dock;
import com.epam.threads.entity.Port;
import com.epam.threads.entity.Ship;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StartPort {
    public static void main(String[] args) {
        Dock dock = new Dock();
        ArrayList<Dock> docks = new ArrayList<Dock>();
        for (int i = 0; i < 5; i++) {
            docks.add(dock);
        }
        Port port = new Port(200, 100, docks);
        Semaphore semaphore = new Semaphore(5);
        PortManager portManager = new PortManager(semaphore, port);
        ArrayList<Ship> ships = new ArrayList<Ship>();
        for (int i = 0; i < 20; i++) {
            ships.add(new Ship((int) (Math.random() * 6) + 5, (int) ((Math.random() * 6) + 5)
                    / 2, portManager));
        }
        StorageManager storageManager = new StorageManager(port);
        storageManager.setDaemon(true);
        storageManager.start();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(ships.get(i));
            thread.setName(String.valueOf(i+1));
            thread.start();
        }
    }
}
