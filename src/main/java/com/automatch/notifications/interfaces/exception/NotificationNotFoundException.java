package com.automatch.notifications.interfaces.exception;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(String msg) {
        super(msg);
    }
}