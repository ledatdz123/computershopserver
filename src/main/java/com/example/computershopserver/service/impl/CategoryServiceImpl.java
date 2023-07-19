package com.example.computershopserver.service.impl;

import com.example.computershopserver.entity.Category;
import com.example.computershopserver.repository.CategoryRepository;
import com.example.computershopserver.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findOneById(Long id) {
        // TODO Auto-generated method stub
        return categoryRepo.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
    }

    @Override
    public Category save(Category category) {
        if(category.getId()!=null) {
            Category oldCate = categoryRepo.findById(category.getId()).orElseThrow(()-> new IllegalStateException("Not Found Category"));
            oldCate.setName(category.getName());
            return categoryRepo.save(oldCate);
        }
        return categoryRepo.save(category);
    }

    @Override
    public boolean delete(Long id) {
        categoryRepo.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
        categoryRepo.deleteById(id);
        return true;
    }

    @Override
    public Category update(Long id, Category category) {
        Category brandExist=categoryRepo.findById(id)
                .orElseThrow(()-> new IllegalStateException("Not Found Category"));
        brandExist.setName(category.getName());
        categoryRepo.save(brandExist);
        return categoryRepo.save(category);
    }

}
