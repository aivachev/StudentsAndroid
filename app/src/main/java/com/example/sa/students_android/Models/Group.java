package com.example.sa.students_android.Models;

import java.util.*;

public class Group<Long, User> extends HashMap{

    private long groupID;
    private Integer size = 0;

    public void setSize(Integer size) {
        this.size = size;
    }

    public Group (long groupID) {
        this.groupID = groupID;
    }

    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(this.values());
        return allUsers;
    }

    public long getGroupID() {
        return groupID;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getGroupID());
    }

    public String info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("All relevant info about this group (#").append(getGroupID()).append("):\n")
                .append("\t").append("It contains ").append(getSize()).append(" people:").append("\n\n");

        for(Object o : this.entrySet()) {
            Object objectUser = ((Map.Entry)o).getValue();
            com.example.sa.students_android.Models.User actualUser = (com.example.sa.students_android.Models.User)objectUser;
            stringBuilder.append("\t\t").append("Last name: ").append(actualUser.getLastName()).append("\n")
                    .append("\t\t").append("First name: ").append(actualUser.getFirstName()).append("\n")
                    .append("\t\t").append("Middle name: ").append(actualUser.getMiddleName()).append("\n\n");
        }
        stringBuilder.append("== == == == == == == == == == == == == ==\n");
        return stringBuilder.toString();
    }
}
