package com.example.computershopserver.service;

import com.example.computershopserver.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Page<ProductDTO> findAllPagination(int pageNumber, int pageSize);

    public List<ProductDTO> findAllProduct();

    public ProductDTO findOneById(Long id);

    public ProductDTO save(ProductDTO product);

    public void delete(Long id);

    public void saveAll(List<ProductDTO> productDTOs);

    public Page<ProductDTO> findByCategory(Long categoryId, int pageNumber, int pageSize);

    public List<ProductDTO> findByKeyword(String keyword, Long categoryId);

    public List<ProductDTO> findAllByCategory(Long categoryId);
}

