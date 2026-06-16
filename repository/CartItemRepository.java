package com.example.management.repository;

import com.example.management.model.CartItem;
import com.example.management.model.Product;
import com.example.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByStudentAndProduct(Student student, Product product);

    void deleteByStudentAndProduct(Student student, Product product);
}