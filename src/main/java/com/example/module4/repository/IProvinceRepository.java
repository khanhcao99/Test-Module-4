package com.example.module4.repository;

import com.example.module4.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findAllByNameContaining(String name);
}
