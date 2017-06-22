package com.example.sa.students_android.Managers;

import android.util.LongSparseArray;

import org.w3c.dom.Document;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.MyUtilMethods.SerializationStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sa on 12.06.17.
 */
public class GroupsManager implements ManagerInterface<Group> {

    public final GroupsManager soloGroupsManager;

    public static HashMap<Long, Group> groups = new HashMap<>();

    public GroupsManager() {
        soloGroupsManager = this;
    }

    public GroupsManager getGroupsManager() {
        return this;
    }

    public static void createGroup(Long number) {
        groups.put(number, new Group(number));
    }

    public static void addUserToGroup(User user, Long groupID) {
        groups.get(groupID).put(user.getId(), user);
        groups.get(groupID).setSize(groups.get(groupID).getSize() + 1);
    }

    public static Group getGroup(long groupID) {
        return groups.get(groupID);
    }

    public static List<User> getAllPupils() {
        List<User> allPupils = new ArrayList<>();
        for (Object o : groups.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            Group group = (Group) pair.getValue();
            allPupils.addAll(group.values());
        }
        return allPupils;
    }

    @Override
    public Group add(Group group) {
        if(group != null)
            group = groups.put(group.getGroupID(), group);
        return group;
    }

    @Override
    public void remove(Group group) {
        groups.remove(group);
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Group get(Long groupID) {
        return groups.get(groupID);
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        groups.addAll(GroupsManager.groups.values());
        return groups;
    }

    @Override
    public void serializeToFile(Group group) {

        String outputFileName = "Group_" +
                group.getGroupID();

        SerializationStuff.serializeToFile(group, outputFileName);

    }

    @Override
    public void serializeAllToFile(String outputFileName) {
        SerializationStuff.serializeAllToFile(groups, outputFileName);
    }

    @Override
    public List<Group> deserializeFromFile(String inputFileName) {
        return SerializationStuff.deserializeFromFile(inputFileName);
    }

    @Override
    public Document serializeToXML(Group element, org.w3c.dom.Document document) {
        return SerializationStuff.serializeToXML(element, document, "Group");
    }

    @Override
    public Document serializeAllToXML(Document document) {
        Document doc = document;

        for(Object MapEntry : groups.entrySet()) {
            Group group = (Group) ((Map.Entry) MapEntry).getValue();
            doc = serializeToXML(group, doc);
        }

        return doc;
    }

    @Override
    public List<Group> deserializeFromXML(String inputFileName) {
        return null;
    }
}
