package com.epam.threads.entity;

public class Dock {
    private boolean isBusy = false;

    public Dock() {
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        return "Dock{" +
                "isBusy=" + isBusy +
                '}';
    }
}
