package com.astimac.fb.popup.example.eventbus;

/**
 * Created by alex on 6/10/15.
 */
public class ServiceRuntimeChangedEvent {

    private final boolean isServiceRunning;

    public ServiceRuntimeChangedEvent(boolean isServiceRunning) {
        this.isServiceRunning = isServiceRunning;
    }

    public boolean isServiceRunning() {
        return this.isServiceRunning;
    }
}
