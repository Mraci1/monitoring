package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

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
        if (windowSize <= 0 || windowSize > data.size()) {
            throw new IllegalArgumentException("Window size must be between 0 and the size of the data list");
        }
        return IntStream.rangeClosed(0, data.size() - windowSize)
                .mapToObj(i -> data.subList(i, i + windowSize))
                .map(subList -> subList.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0))
                .toList();
    }

}
