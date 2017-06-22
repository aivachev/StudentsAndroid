package com.example.sa.students_android.Models;

import com.example.sa.students_android.Managers.GroupsManager;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class User implements Externalizable {

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    transient private Group group;
    private Contact contacts;
    final Long id;
    private Role role;

    public User() {
        Random random = new Random();
        this.id = System.currentTimeMillis() + random.nextInt(100000000);
    }

    public User(String fName, String mName, String lName, Date DoB, Long grID, Role role) {

        Random random = new Random();

        this.firstName = fName;
        this.middleName = mName;
        this.lastName = lName;
        this.dateOfBirth = DoB;
        this.id = System.currentTimeMillis() + random.nextInt(100_000_000);
        this.contacts = new Contact();
        this.role = role;

        if(!GroupsManager.groups.containsKey(grID))
            GroupsManager.createGroup(grID);
        this.group = GroupsManager.getGroup(grID);

        GroupsManager.addUserToGroup(this, grID);
    }

    @Override
    public int hashCode() {
        return (int) (21 + id * 42);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;
        if (this.getId() != ((User) obj).getId() ) return false;
        return true;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(firstName);
        out.writeObject(middleName);
        out.writeObject(lastName);
        out.writeObject(dateOfBirth);
        out.writeObject(group);
        out.writeObject(role);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        firstName = (String) in.readObject();
        middleName = (String) in.readObject();
        lastName = (String) in.readObject();
        dateOfBirth = (Date) in.readObject();
        group = (Group) in.readObject();
        role = (Role) in.readObject();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("All relevant info about this user: \n")
                .append("\tLast name: ").append(getLastName()).append("\n")
                .append("\tFirst name: ").append(getFirstName()).append("\n")
                .append("\tMiddle name: ").append(getMiddleName()).append("\n")
                .append("\tDate of birth: ").append(getDateOfBirth()).append("\n")
                .append("\tID: ").append(getId()).append("\n")
                .append("\tGroup number: ").append(getUserGroup()).append("\n")
                .append("\tTheir role is: ").append(getRole()).append("\n");
        return stringBuilder.toString();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public Group getUserGroup() {
        return group;
    }

    public HashMap getContacts() {
        return contacts;
    }

    public Role getRole() {
        return role;
    }
}
