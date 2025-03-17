package com.ecommerce.orders.adapter;

import com.ecommerce.orders.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FakeStoreApiClient {

    private final RestTemplate restTemplate;
    @Value("${fakestoreapi.base-url}")
    private String baseUrl;

    public FakeStoreApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<ProductDto> getAllProducts() {
        String url = baseUrl + "/products";
        log.info("Fetching all products from {}", url);
        ProductDto[] products = restTemplate.getForObject(url, ProductDto[].class);
        return Arrays.asList(products);
    }

    public ProductDto getProductById(Long id) {
        String url = baseUrl + "/products/" + id;
        log.info("Fetching product with id {} from {}", id, url);
        return restTemplate.getForObject(url, ProductDto.class);
    }
}