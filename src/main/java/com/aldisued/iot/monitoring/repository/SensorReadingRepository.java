package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {
    @Query("""
        SELECT AVG(s.value)
        FROM SensorReading s
        WHERE s.sensor.type = SensorType.TEMPERATURE
            AND s.timestamp BETWEEN :from AND :to
    """)
    Optional<Double> queryAverageTemperature(LocalDateTime from, LocalDateTime to);
}
