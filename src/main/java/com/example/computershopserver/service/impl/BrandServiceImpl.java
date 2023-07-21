package com.example.computershopserver.service.impl;

import com.example.computershopserver.entity.Brand;
import com.example.computershopserver.repository.BrandRepository;
import com.example.computershopserver.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findOneById(Long id) {
        return brandRepository.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
    }

    @Override
    public Brand save(Brand brand) {
        if(brand.getId()!=null) {
            Brand oldCate = brandRepository.findById(brand.getId()).orElseThrow(()-> new IllegalStateException("Not Found Category"));
            oldCate.setName(brand.getName());
            return brandRepository.save(oldCate);
        }
        return brandRepository.save(brand);
    }

    @Override
    public boolean delete(Long id) {
        brandRepository.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
        brandRepository.deleteById(id);
        return true;
    }

    @Override
    public Brand update(Long id, Brand brand) {
        Brand brandExist=brandRepository.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
        brandExist.setName(brand.getName());
        brandRepository.save(brandExist);
        return brandRepository.save(brand);
    }
}
