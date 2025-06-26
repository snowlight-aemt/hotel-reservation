package me.snow.hotelpms.room.errors;

public class LackOfQuantityException extends RuntimeException {
    public LackOfQuantityException(String message) {
        super(message);
    }
}
