package com.example.management.controller;

import com.example.management.DTO.CartItemRequest;
import com.example.management.model.CartItem;
import com.example.management.service.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addtocart(@RequestHeader("X-User-ID") String studentId ,
                                            @RequestBody CartItemRequest request){
        if(!cartService.addtocart(studentId,request)){
            return ResponseEntity.badRequest().body("PRODUCT IS OUT OF STOCK OR USER NOT FOUND");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> RemoveFromCart(@RequestHeader("X-Student-ID") String studentId,
                                               @PathVariable Long productId){
        boolean deleted=cartService.deleteItemFromCart(studentId,productId);
        return deleted? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
}
