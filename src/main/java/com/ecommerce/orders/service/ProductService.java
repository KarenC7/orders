package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts(int page, int size);
    ProductDto getProductById(Long id);
}
