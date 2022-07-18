package com.careerdevs.weatherapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Indexed;

@Indexed
public interface CurrentReportRepository extends JpaRepository<CurrentReportRepository, Long> {

}
