package com.example.computershopserver.entity.entityenum;

public enum ERole {
    ADMIN(1), USER(2);

    private int value;

    private ERole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
