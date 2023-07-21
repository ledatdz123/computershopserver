package com.example.computershopserver.service;

import com.example.computershopserver.entity.Brand;

import java.util.List;
public interface BrandService {
    public List<Brand> findAll();

    public Brand findOneById(Long id);
    public Brand save(Brand brand);
    public boolean delete(Long id);
    public Brand update(Long id, Brand brand);
}

