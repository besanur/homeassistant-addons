package com.besanur.prayertimes.repository;

import com.besanur.prayertimes.model.PrayerTimeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrayerTimesDataRepository extends JpaRepository<PrayerTimeData, Integer> {

}
