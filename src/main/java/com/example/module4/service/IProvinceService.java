package com.example.module4.service;

import com.example.module4.model.Province;

import java.util.List;

public interface IProvinceService extends ICRUD<Province>{
    List<Province> findAllByNameContaining (String name);
}
