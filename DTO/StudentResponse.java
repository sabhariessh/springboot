package com.example.management.DTO;

import com.example.management.model.UserRole;
import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private UserRole role;
    private com.example.management.DTO.AddressDTO address;
}