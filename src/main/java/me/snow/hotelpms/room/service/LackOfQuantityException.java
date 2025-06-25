package me.snow.hotelpms.room.service;

public class LackOfQuantityException extends RuntimeException {
    public LackOfQuantityException(String message) {
        super(message);
    }
}
