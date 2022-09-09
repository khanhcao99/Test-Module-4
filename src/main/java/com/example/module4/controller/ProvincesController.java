package com.example.module4.controller;

import com.example.module4.model.Country;
import com.example.module4.model.Province;
import com.example.module4.service.ICountryService;
import com.example.module4.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/Provinces")
public class ProvincesController {
    @Autowired
    private ICountryService countryService;

    @Autowired
    private IProvinceService provinceService;

    @GetMapping("/Countries")
    public ResponseEntity<List<Country>> findAllCountries(){
        return new ResponseEntity<>(countryService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Province>> findAllProvinces(){
        return new ResponseEntity<>(provinceService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProvince(@RequestBody Province province){
        return new ResponseEntity<>(provinceService.save(province), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Province> detail(@PathVariable("id") long id){
        Optional<Province> provinceOptional = provinceService.findById(id);
        if (provinceOptional.isPresent()){
            return new ResponseEntity<>(provinceOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateProvince(@RequestBody Province province){
        return new ResponseEntity<>(provinceService.save(province), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") long id){
        Optional<Province> provinceOptional = provinceService.findById(id);
        if (provinceOptional.isPresent()){
            provinceService.deleteById(provinceOptional.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("search/{search}")
    public ResponseEntity<List<Province>> findByName(@PathVariable("search") String name){
        List<Province> provinceList = provinceService.findAllByNameContaining(name);
        if (provinceList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provinceList, HttpStatus.OK);
    }
}
