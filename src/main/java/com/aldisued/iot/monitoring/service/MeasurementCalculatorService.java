package com.aldisued.iot.monitoring.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation == null || deviation < 0.0 || deviation > 1.0) {
      throw new IllegalArgumentException("Deviation must be between 0.0 and 1.0");
    }

    if (values.isEmpty()) {
      return Collections.emptyList();
    }

    double averageValue = values.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElseThrow(IllegalArgumentException::new);
    double min = averageValue * (1.0 - deviation);
    double max = averageValue * (1.0 + deviation);

    return values.stream()
            .filter(value -> value >= min && value <= max)
            .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    // TODO: Task 10
    return List.of();
  }

}
