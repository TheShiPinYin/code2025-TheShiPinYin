package com.example.model.entity;

/**
* 
*@author OliverKim
*@since 2025-04-11
*/
public enum Role {
    ADMIN("admin"),
    USER("user");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
