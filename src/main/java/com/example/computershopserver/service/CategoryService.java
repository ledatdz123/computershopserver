package com.example.computershopserver.service;

import com.example.computershopserver.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();

    public Category findOneById(Long id);

    public Category save(Category category);

    public boolean delete(Long id);
    public Category update(Long id, Category category);
}
