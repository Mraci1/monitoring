package com.aldisued.iot.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertNotFoundException extends RuntimeException {
    public AlertNotFoundException(UUID sensorId) {
        super("No alert found for sensor " + sensorId);
    }
}