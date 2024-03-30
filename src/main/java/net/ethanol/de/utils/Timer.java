package net.ethanol.de.utils;

public class Timer {
    long lastReset;

    public Timer() {
        reset();
    }

    public void reset() {
        lastReset = System.currentTimeMillis();
    }

    public boolean hasExpired(long timeout) {
        return System.currentTimeMillis() - lastReset > timeout;
    }

}
