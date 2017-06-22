package com.example.sa.students_android.Managers;

import com.example.sa.students_android.Models.DummyData;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.MyUtilMethods.SerializationStuff;

import org.w3c.dom.Document;

import java.util.*;

public class UsersManager implements ManagerInterface<User> {

    private HashMap<Long, User> users = new HashMap<>();
    private Random random = new Random();

    public UsersManager() {
    }

    @Override
    public User add(User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public void remove(User user) {
        user.getUserGroup().remove(user.getId());
        this.users.remove(user.getId());
    }

    @Override
    public void removeAll() {
        users.clear();
    }

    @Override
    public User get(Long userID) {
        return users.get(userID);
    }

    @Override
    public List<User> getAll() {
        List<User> allElements = new ArrayList<>();
        allElements.addAll(users.values());
        return allElements;
    }

    @Override
    public void serializeToFile(User user) {

        String outputFileName = user.getLastName() +
                user.getFirstName().substring(0, 1) +
                user.getMiddleName().substring(0, 1);

        SerializationStuff.serializeToFile(user, outputFileName);
    }

    @Override
    public void serializeAllToFile(String outputFileName) {
        SerializationStuff.serializeAllToFile(users, outputFileName);
    }

    @Override
    public List<User> deserializeFromFile(String inputFileName) {
        return SerializationStuff.deserializeFromFile(inputFileName);
    }

    @Override
    public Document serializeToXML(User element, Document document) {
        return SerializationStuff.serializeToXML(element, document, "User");
    }

    @Override
    public Document serializeAllToXML(Document document) {

        Document doc = document;

        for(Object MapEntry : users.entrySet()) {
            User user = (User) ((Map.Entry) MapEntry).getValue();
            doc = serializeToXML(user, doc);
        }

        return doc;
    }

    @Override
    public List<User> deserializeFromXML(String inputFileName) {
        return null;
    }

    private User createUser(String firstName, String middleName, String lastName, Date dateOfBirth, Long groupID, Role role) {

        User user = null;

        if (role == Role.TEACHER || role == Role.ADMIN)
            user = new User(firstName, middleName, lastName, dateOfBirth, null, role);
        if (role == Role.STUDENT)
            user = new User(firstName, middleName, lastName, dateOfBirth, groupID, role);

        if (user != null)
            users.put(user.getId(), user);
        return user;
    }

    public void removeAllUsersByRole(Role role) {
        for(Object o : users.entrySet())
            if(((User) ((Map.Entry) o).getValue()).getRole() == role)
                users.remove(((Map.Entry) o).getKey());
    }

    private List<User> getUsersByRole(Role role) {
        List<User> result = new ArrayList<>();

        for (Object o : users.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (((User) pair.getValue()).getRole() == role)
                result.add((User) pair.getValue());
        }

        return result;
    }

    public User addDummyStudent(Integer year) {

        int gender = random.nextInt(2);
        int indexFNameMales = random.nextInt(DummyData.FIRSTNAMES_MALES.length);
        int indexFNameFemales = random.nextInt(DummyData.FIRSTNAMES_FEMALES.length);
        int indexMName = random.nextInt(DummyData.MIDDLENAMES.length);
        int indexLName = random.nextInt(DummyData.LASTNAMES.length);

        Random random = new Random();
        long groupID = 1000L + (long) random.nextInt(3);
        User user;

        if (gender == 0)
            user = createUser(DummyData.FIRSTNAMES_MALES[indexFNameMales],
                    DummyData.MIDDLENAMES[indexMName] + "вич",
                    DummyData.LASTNAMES[indexLName],
                    new Date(year - 1 + random.nextInt(3),
                            random.nextInt(12) + 1,
                            random.nextInt(29) + 1),
                    groupID, Role.STUDENT);
        else
            user = createUser(DummyData.FIRSTNAMES_FEMALES[indexFNameFemales],
                    DummyData.MIDDLENAMES[indexMName] + "вна",
                    DummyData.LASTNAMES[indexLName] + "а",
                    new Date(year - 1 + random.nextInt(3),
                            random.nextInt(12) + 1,
                            random.nextInt(29) + 1),
                    groupID, Role.STUDENT);
        //add(user);
        return user;
    }

    public User getRandomUserByRole(Role role) {
        List result = new ArrayList();
        if(!users.isEmpty())
            result = getUsersByRole(role);
        Random random = new Random();

        return (User) result.get(random.nextInt(result.size()));
    }
}
