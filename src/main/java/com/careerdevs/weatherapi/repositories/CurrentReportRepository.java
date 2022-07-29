package com.careerdevs.weatherapi.repositories;

import com.careerdevs.weatherapi.models.CurrentWeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Indexed;

@Indexed
public interface CurrentReportRepository extends JpaRepository<CurrentWeatherReport, Long> {

}
