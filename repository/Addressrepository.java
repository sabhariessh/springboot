package com.example.management.repository;

import com.example.management.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Addressrepository extends JpaRepository<Address,Long> {
}
