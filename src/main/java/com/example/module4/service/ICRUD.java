package com.example.module4.service;

import java.util.List;
import java.util.Optional;

public interface ICRUD<E> {
    List<E> findAll();

    E save(E e);

    Optional<E> findById (Long id);

    void deleteById(Long id);
}
