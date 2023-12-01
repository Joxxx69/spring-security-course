package com.spring.security.springsecuritycourse.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.springsecuritycourse.dto.SaveProductDTO;
import com.spring.security.springsecuritycourse.persistence.entity.Product;
import com.spring.security.springsecuritycourse.service.ProductService;

import jakarta.validation.Valid;

//@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    //@PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page<Product> productsPage = productService.findAll(pageable);
        if (productsPage.hasContent()) {
            return ResponseEntity.ok(productsPage);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //return ResponseEntity.notFound().build();
    }
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    //@PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId) {
        Optional<Product> product = productService.findOneById(productId);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    //@PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProductDTO SaveProductDTO) {
        Product product = productService.createOne(SaveProductDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    //@PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    //@PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId,
    @RequestBody @Valid SaveProductDTO saveProductDTO) {
        Product product = productService.updateOneById(productId, saveProductDTO);
        
        return ResponseEntity.ok(product);
        //return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    //@PreAuthorize("hasRole('ADMINISTRATOR')")
    //@PreAuthorize("hasAuthority('DISABLED_ONE_PRODUCT')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableOneById(@PathVariable Long productId) {
        Product product = productService.disableOneById(productId);
        return ResponseEntity.ok(product);
    }
}
