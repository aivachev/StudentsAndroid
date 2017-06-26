package com.example.sa.students_android.Models;

import android.util.Log;

import com.example.sa.students_android.Managers.GroupsManager;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class User implements Serializable {

    private String login;
    private Integer password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    transient private Group group;
    private Contacts<String, ContactType> contacts = new Contacts();
    final Long id;
    private Role role;

    public User() {
        Random random = new Random();
        this.id = System.currentTimeMillis() + random.nextInt(100000000);
    }

    public User(String login, Integer password, String firstName, String middleName, String lastName, Date dateOfBirth, Long groupId, Role role) {

        Random random = new Random();

        this.login = login;
        this.password = password;

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.id = System.currentTimeMillis() + random.nextInt(100_000_000);
        this.contacts.put("+7(123)456-78-90", ContactType.PHONE);
        this.role = role;

        if(!GroupsManager.groups.containsKey(groupId))
            GroupsManager.createGroup(groupId);
        this.group = GroupsManager.getGroup(groupId);

        GroupsManager.addUserToGroup(this, groupId);
    }

    public User(Long id, String login, Integer password, String firstName, String middleName, String lastName, Date dateOfBirth, Long groupId, Contacts contacts, Role role) {

        this.login = login;
        this.password = password;

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
        this.contacts = contacts;
        this.role = role;

        Log.i("ItsMeUser", String.valueOf(contacts.isEmpty()));

        if(!GroupsManager.groups.containsKey(groupId))
            GroupsManager.createGroup(groupId);
        this.group = GroupsManager.getGroup(groupId);

        if(!GroupsManager.getAllPupils().contains(this))
            GroupsManager.addUserToGroup(this, groupId);
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

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject(firstName);
//        out.writeObject(middleName);
//        out.writeObject(lastName);
//        out.writeObject(dateOfBirth);
//        out.writeObject(group);
//        out.writeObject(role);
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        firstName = (String) in.readObject();
//        middleName = (String) in.readObject();
//        lastName = (String) in.readObject();
//        dateOfBirth = (Date) in.readObject();
//        group = (Group) in.readObject();
//        role = (Role) in.readObject();
//    }

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

    public String getLogin() {
        return login;
    }

    public Integer getPassword() {
        return password;
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

    public HashMap<String, ContactType> getContacts() {
        return contacts;
    }

    public Role getRole() {
        return role;
    }
}
