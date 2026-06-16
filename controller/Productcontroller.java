package com.example.management.controller;

import com.example.management.DTO.ProductRequest;
import com.example.management.DTO.ProductResponse;
import com.example.management.service.ProductService;
import jakarta.servlet.annotation.HttpConstraint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class Productcontroller {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponse> createproduct(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<ProductResponse>(productService.addProduct(productRequest),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getallproduct(){
        return ResponseEntity.ok(productService.getallproducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getproductbyid(@PathVariable Long id){
        return ResponseEntity.ok(productService.getproductbyId(id));
    }
    @PutMapping
    public ResponseEntity<ProductResponse> updateproduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest productRequest){
        return productService.updateproducts(id,productRequest)
                .map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteproduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchproduct(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchproduct(keyword));
    }
}
