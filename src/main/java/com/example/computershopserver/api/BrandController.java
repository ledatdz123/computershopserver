package com.example.computershopserver.api;

import com.example.computershopserver.entity.Brand;
import com.example.computershopserver.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping()
    public List<Brand> getList(){
        return brandService.findAll();
    }

    @GetMapping("/{id}")
    public Brand update(@PathVariable Long id) {
        return brandService.findOneById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Brand save(@Valid @RequestBody Brand brand) {
        return brandService.save(brand);
    }

    @PutMapping("/{id}")
    public Brand update(@PathVariable Long id, @Valid @RequestBody Brand brand) {
        return brandService.update(id,brand);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return brandService.delete(id);
    }
}
