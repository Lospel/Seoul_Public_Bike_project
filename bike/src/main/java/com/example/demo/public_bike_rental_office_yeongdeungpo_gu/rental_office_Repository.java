package com.example.demo.public_bike_rental_office_yeongdeungpo_gu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;






public interface rental_office_Repository extends JpaRepository<public_bike_rental_office_yeongdeungpo_gu, String> {
    @Query("select r.Rental_Office_Number, r.Latitude, r.Longitude from public_bike_rental_office_yeongdeungpo_gu r where r.Ward_KOR = :Ward_KOR" )
    List<public_bike_rental_office_yeongdeungpo_gu> findAllByWard_KOR(@Param("Ward_KOR") String Ward_KOR);
    
}
