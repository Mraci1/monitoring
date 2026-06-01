package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.SensorDto;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.SensorNameAlreadyExistsException;
import com.aldisued.iot.monitoring.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorController {

  private final SensorService sensorService;

  public SensorController(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @PostMapping
  public Sensor saveSensor(@Valid @RequestBody SensorDto sensorDto) {
    return sensorService.saveSensor(sensorDto);
  }

  @ExceptionHandler(SensorNameAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public String handleDataIntegrityViolationException(SensorNameAlreadyExistsException ex) {
    return ex.getMessage();
  }
}