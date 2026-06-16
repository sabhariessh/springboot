package com.example.management.service;

import com.example.management.DTO.CartItemRequest;
import com.example.management.model.CartItem;
import com.example.management.model.Product;
import com.example.management.model.Student;
import com.example.management.repository.CartItemRepository;
import com.example.management.repository.ProductRepository;
import com.example.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final StudentRepository studentRepository;
    private final CartItemRepository cartItemRepository;
    public boolean addtocart(String studentId, CartItemRequest request) {
        Optional<Product> productOpt=productRepository.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product=productOpt.get();
        if(product.getStockQuantity()<request.getQuantity()){
            return false;
        }
        Optional<Student> studentOpt=studentRepository.findById(Long.valueOf(studentId));
        if(studentOpt.isEmpty()){
            return false;
        }
        Student student=studentOpt.get();
        CartItem existingcartitem=cartItemRepository.findByStudentAndProduct(student,product);
        if(existingcartitem!=null){
            //update cart item
            existingcartitem.setQuantity(existingcartitem.getQuantity()+request.getQuantity());
            existingcartitem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingcartitem.getQuantity())));
            cartItemRepository.save(existingcartitem);
        }
        else{
            CartItem cartItem=new CartItem();
            cartItem.setStudent((student));
            cartItem.setProduct((product));
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String studentId, Long productId) {
        Optional<Product> productOpt=productRepository.findById(productId);
        Optional<Student> studentOpt=studentRepository.findById(Long.valueOf(studentId));
        if(productOpt.isPresent() && studentOpt.isPresent()){
            cartItemRepository.deleteByStudentAndProduct(studentOpt.get(),productOpt.get());
            return true;
        }
        return false;
    }
}
