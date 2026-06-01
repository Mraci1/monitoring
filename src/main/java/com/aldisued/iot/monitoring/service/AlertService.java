package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.exception.AlertNotFoundException;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                        KafkaTemplate<String, AlertDto> kafkaTemplate) {
        this.alertRepository = alertRepository;
        this.sensorRepository = sensorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Alert saveAlert(AlertDto alertDto) {
        Alert savedAlert = alertRepository.save(new Alert(alertDto.message(), alertDto.timestamp(),
                sensorRepository.getReferenceById(alertDto.sensorId())));
        kafkaTemplate.send("alerts", alertDto);
        return savedAlert;
    }

    public AlertDto findLastAlertBySensorId(UUID sensorId) {
        return alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId)
                .map(alert -> new AlertDto(alert.getSensor().getId(), alert.getMessage(), alert.getTimestamp()))
                .orElseThrow(() -> new AlertNotFoundException(sensorId));
    }
}
