package com.example.model.entity;

import lombok.Getter;

/**
* 
*@author OliverKim
*@since 2025-04-11
*/
@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

}
