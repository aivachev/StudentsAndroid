package com.example.sa.students_android.Models;

import java.util.HashMap;

public class Contact extends HashMap{

    private String value;
    private ContactType type;

    public Contact() {
    }

    public Contact(String value, ContactType type) {

        this.value = value;
        this.type = type;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Contact)) return false;
        if(this.type != ((Contact)obj).getType()) return false;
        if(this.value != ((Contact)obj).getValue()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return ((21 + value.hashCode() * 41) + (21 + type.hashCode() * 41));
    }

}
