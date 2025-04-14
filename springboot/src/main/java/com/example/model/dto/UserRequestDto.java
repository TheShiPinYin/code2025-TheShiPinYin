package com.example.model.dto;

import com.example.model.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author OliverKim
 * @since 2025-04-14
 */
@Data
public class UserRequestDto {
    
    public List<Integer> ids;
    public Role role;
    
}
