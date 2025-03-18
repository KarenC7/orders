package com.ecommerce.orders.controller;

import com.ecommerce.orders.dto.ProductDto;
import com.ecommerce.orders.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get products from external API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "500", description = "Bad server response"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        log.info("GET /products called with page={} and size={}", page, size);
        return productService.getProducts(page, size);
    }

    @Operation(summary = "Get product from external API by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "500", description = "Bad server response"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        log.info("GET /products/{} called", id);
        return productService.getProductById(id);
    }
}
