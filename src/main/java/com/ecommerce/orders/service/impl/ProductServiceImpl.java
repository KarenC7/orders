package com.ecommerce.orders.service.impl;

import com.ecommerce.orders.adapter.FakeStoreApiClient;
import com.ecommerce.orders.dto.ProductDto;
import com.ecommerce.orders.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final FakeStoreApiClient fakeStoreApiClient;

    public ProductServiceImpl(FakeStoreApiClient fakeStoreApiClient) {
        this.fakeStoreApiClient = fakeStoreApiClient;
    }

    @Override
    public List<ProductDto> getProducts(int page, int size) {
        log.info("Getting products - page: {}, size: {}", page, size);
        List<ProductDto> allProducts = fakeStoreApiClient.getAllProducts();
        int fromIndex = Math.min(page * size, allProducts.size());
        int toIndex = Math.min(fromIndex + size, allProducts.size());
        List<ProductDto> pagedProducts = allProducts.subList(fromIndex, toIndex);
        log.info("Returning {} products from index {} to {}", pagedProducts.size(), fromIndex, toIndex);
        return pagedProducts;
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.info("Getting product by id: {}", id);
        return fakeStoreApiClient.getProductById(id);
    }
}