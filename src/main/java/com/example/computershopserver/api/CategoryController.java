package com.example.computershopserver.api;

import com.example.computershopserver.entity.Category;
import com.example.computershopserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<Category> getList(){
        return categoryService.findAll();
    }
    @GetMapping("/{id}")
    public Category update(@PathVariable Long id) {
        return categoryService.findOneById(id);
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Category save(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping
    public Category update(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @Valid @RequestBody Category category) {
        return categoryService.update(id, category);
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }

}
