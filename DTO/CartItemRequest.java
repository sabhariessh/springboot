package com.example.management.DTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    //private Integer stockQuantity;
    private Integer quantity;


}