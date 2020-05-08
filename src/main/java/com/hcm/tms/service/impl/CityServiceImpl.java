package com.hcm.tms.service.impl;

import com.hcm.tms.entity.City;
import com.hcm.tms.repository.CityRepository;
import com.hcm.tms.entity.City;
import com.hcm.tms.repository.CityRepository;
import com.hcm.tms.repository.UserRepository;
import com.hcm.tms.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;
    @Override
    public List<City> findAllCity() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
