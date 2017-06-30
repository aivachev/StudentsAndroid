package com.example.sa.students_android.Models;

import com.example.sa.students_android.Enums.ContactType;

import java.io.Serializable;
import java.util.HashMap;

public class Contacts implements Serializable {

    public String getValue() {
        return value;
    }

    public ContactType getType() {
        return type;
    }

    private String value;
    private ContactType type;

    public Contacts(String value, ContactType type) {
        this.value = value;
        this.type = type;
    }
}
