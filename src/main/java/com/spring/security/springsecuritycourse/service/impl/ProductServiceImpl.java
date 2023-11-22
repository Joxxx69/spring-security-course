package com.spring.security.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.spring.security.springsecuritycourse.dto.SaveProductDTO;
import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.entity.Category;
import com.spring.security.springsecuritycourse.persistence.entity.Product;
import com.spring.security.springsecuritycourse.persistence.repository.ProductRepository;
import com.spring.security.springsecuritycourse.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    //nivel de Servicio de implementacion
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);

    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(SaveProductDTO saveProductDTO) {
        Product product = new Product();
        product.setName(saveProductDTO.getName());
        product.setPrice(saveProductDTO.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setCategoryId(saveProductDTO.getCategoryId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateOneById(Long productId, SaveProductDTO saveProductDTO) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ObjectNotFoundException("Product Not Found with id: "+ productId));
        productFromDB.setName(saveProductDTO.getName());
        productFromDB.setPrice(saveProductDTO.getPrice());
        productFromDB.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setCategoryId(saveProductDTO.getCategoryId());
        productFromDB.setCategory(category);

        return productRepository.save(productFromDB);
    }

    @Override
    public Product disableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                        .orElseThrow(()-> new ObjectNotFoundException("Product Not Found with id: "+ productId));
        productFromDB.setStatus(Product.ProductStatus.DISABLED);
        return productRepository.save(productFromDB);

    }

    
}
