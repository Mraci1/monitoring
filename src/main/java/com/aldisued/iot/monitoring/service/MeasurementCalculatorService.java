package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementCalculatorService {

    public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
        if (deviation > 1.0 || deviation < 0.0) {
            throw new IllegalArgumentException("Deviation must be between 0 and 1");
        }
        double average = values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double lowerBound = average * (1 - deviation);
        double upperBound = average * (1 + deviation);

        return values.stream()
                .filter(value -> value >= lowerBound && value <= upperBound)
                .toList();
    }

    public List<Double> getMovingAverage(List<Double> data, int windowSize) {
        // TODO: Task 10
        return List.of();
    }

}
