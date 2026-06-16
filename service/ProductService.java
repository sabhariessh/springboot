package com.example.management.service;

import com.example.management.repository.ProductRepository;
import com.example.management.DTO.ProductRequest;
import com.example.management.DTO.ProductResponse;
import com.example.management.model.Product;
import com.example.management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct=productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        return productResponse;

    }


    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        productRepository.save(product);
    }
    public List<ProductResponse> getallproducts(){
        return productRepository.findAll().stream().map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    public ProductResponse getproductbyId(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id"+id));
        return mapToProductResponse(product);
    }
    public Optional<ProductResponse>updateproducts(Long id,ProductRequest productRequest){
        return productRepository.findById(id).map(existingProduct->{
                    updateProductFromRequest(existingProduct,productRequest);
                    Product savedproduct=productRepository.save(existingProduct);
                    return mapToProductResponse(savedproduct);
                });
    }
    public String deleteProduct(Long id){
        Product product=productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("product not found with id:"+id));
        product.setActive(false);
        productRepository.save(product);
        return "product deactivated";
    }

    public List<ProductResponse> searchproduct(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
