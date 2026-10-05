package com.aldisued.iot.monitoring.service;

import java.util.ArrayList;
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

    double averageValue = calculateAverage(values);
    double min = averageValue * (1.0 - deviation);
    double max = averageValue * (1.0 + deviation);

    return values.stream()
            .filter(value -> value >= min && value <= max)
            .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    if (windowSize <= 0 || windowSize > data.size()) {
      throw new IllegalArgumentException("Window size must be between 1 and the number of values");
    }

    List<Double> window = new ArrayList<>(data.subList(0, windowSize));
    List<Double> movingAverages = new ArrayList<>();
    movingAverages.add(calculateAverage(window));
    
    for (int i = windowSize; i < data.size(); i++) {
      window.removeFirst();
      window.add(data.get(i));
      movingAverages.add(calculateAverage(window));
    }

    return movingAverages;
  }

  private double calculateAverage(List<Double> values) {
    return values.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElseThrow(IllegalArgumentException::new);
  }

}
