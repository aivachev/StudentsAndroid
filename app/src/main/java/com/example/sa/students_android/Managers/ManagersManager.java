package com.example.sa.students_android.Managers;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by sa on 13.06.17.
 */
public class ManagersManager implements ManagerInterface<ManagerInterface> {

    GroupsManager groupsManager;
    UsersManager usersManager;
    LessonsManager lessonsManager;

    public ManagersManager() {
        this.groupsManager = new GroupsManager();
        this.usersManager = new UsersManager();
        this.lessonsManager = new LessonsManager();
    }

    /*public Group addGroup(Long grID) {
        groupsManager.createGroup(grID);
        return groupsManager.getGroup(grID);
    }*/

    @Override
    public ManagerInterface add(ManagerInterface managerInterface) {

        return null;
    }

    @Override
    public void remove(ManagerInterface managerInterface) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public ManagerInterface get(Long managerInterface) {
        return null;
    }

    @Override
    public List<ManagerInterface> getAll() {
        return null;
    }

    @Override
    public void serializeToFile(ManagerInterface managerInterface) {

    }

    @Override
    public void serializeAllToFile(String outputFileName) {

    }

    @Override
    public List<ManagerInterface> deserializeFromFile(String inputFileName) {
        return null;
    }

    @Override
    public Document serializeToXML(ManagerInterface element, org.w3c.dom.Document document) {
        return null;
    }

    @Override
    public Document serializeAllToXML(Document document) {
        return null;
    }

    @Override
    public List<ManagerInterface> deserializeFromXML(String inputFileName) {
        return null;
    }
}
