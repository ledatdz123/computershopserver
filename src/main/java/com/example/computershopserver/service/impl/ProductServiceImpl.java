package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.ProductDTO;
import com.example.computershopserver.entity.Category;
import com.example.computershopserver.entity.Product;
import com.example.computershopserver.repository.CategoryRepository;
import com.example.computershopserver.repository.ProductRepository;
import com.example.computershopserver.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepo;
    private ModelMapper modelMapper;
    private final int PAGE_OFFSET=1;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepo,
                              ModelMapper modelMapper) {
        super();
        this.productRepository = productRepository;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDTO> findAllPagination(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-PAGE_OFFSET, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        return new PageImpl<>(productPage.stream().map(this::toDTO).collect(Collectors.toList()), pageable, productPage.getTotalElements());
    }

    @Override
    public List<ProductDTO> findAllProduct() {
        return productRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public ProductDTO findOneById(Long id) {
        return toDTO(productRepository.findById(id).orElseThrow(()->new IllegalStateException("Not found product")));
    }

    @Override
    public ProductDTO save(ProductDTO product){
        Product product1=new Product();
        if(product.getId()!=null) {
            ProductDTO oldProduct = findOneById(product.getId());
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategoryId(product.getCategoryId());
            oldProduct.setBrandId((product.getBrandId()));
            oldProduct.setCategoryName(product.getCategoryName());
            oldProduct.setQuantity(product.getQuantity());
            oldProduct.setDescription(product.getDescription());
            if(!product.getImage().isBlank())
                oldProduct.setImage(product.getImage());
            System.out.println(toEntity(oldProduct).toString());
            oldProduct.setAvgRating(0F);
            return toDTO(productRepository.save(toEntity(oldProduct)));
        }
        return toDTO(productRepository.save(toEntity(product)));
    }

    @Override
    public void saveAll(List<ProductDTO> productDTOs) {
        List<Product> products = new ArrayList<>();
        for (ProductDTO dto : productDTOs) {
            products.add(toEntity(dto));
        }
        productRepository.saveAll(products);
    }

    @Override
    public Page<ProductDTO> findByCategory(Long categoryId, int pageNumber, int pageSize) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new IllegalStateException("Not found category"));
        Pageable pageable = PageRequest.of(pageNumber-PAGE_OFFSET, pageSize);
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return new PageImpl<ProductDTO>(products.stream().map(this::toDTO).collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Transactional
    @Override
    public List<ProductDTO> findByKeyword(String keyword, Long categoryId) {
        final String kw = keyword;
        List<Product> products = new ArrayList<>();
        if(categoryId != null && categoryId != 0L) {
            products = productRepository.findAllByCategory(Category.builder().id(categoryId).build())
                    .stream()
                    .filter(product -> product.getName().toUpperCase().contains(kw.toUpperCase())).toList();
        }else {
            products = productRepository.findAll()
                    .stream().
                            filter(product -> product.getName().toUpperCase().contains(kw.toUpperCase())).toList();
        }
        return products.stream().map(this::toDTO).toList();
    }

    @Override
    public List<ProductDTO> findAllByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new IllegalStateException("Not found category"));
        return productRepository.findAllByCategory(category).stream().map(this::toDTO).toList();
    }

    private ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
