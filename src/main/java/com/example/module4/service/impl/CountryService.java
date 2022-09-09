package com.example.module4.service.impl;

import com.example.module4.model.Country;
import com.example.module4.repository.ICountryRepository;
import com.example.module4.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements ICountryService {
    @Autowired
    private ICountryRepository iCountryRepository;

    @Override
    public List<Country> findAll() {
        return iCountryRepository.findAll();
    }

    @Override
    public Country save(Country country) {
        return iCountryRepository.save(country);
    }

    @Override
    public Optional<Country> findById(Long id) {
        return iCountryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        iCountryRepository.deleteById(id);
    }
}
