package com.hcm.tms.repository;

import com.hcm.tms.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    City findByCityId(String id);
    City findByCityName(String name);
}
