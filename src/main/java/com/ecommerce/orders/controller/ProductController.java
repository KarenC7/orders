package com.ecommerce.orders.controller;

import com.ecommerce.orders.dto.ProductDto;
import com.ecommerce.orders.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        log.info("GET /products called with page={} and size={}", page, size);
        return productService.getProducts(page, size);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        log.info("GET /products/{} called", id);
        return productService.getProductById(id);
    }
}
