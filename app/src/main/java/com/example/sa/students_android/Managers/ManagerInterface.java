package com.example.sa.students_android.Managers;

import org.w3c.dom.Document;

import java.util.List;

/**
 * Created by sa on 13.06.17.
 */
public interface ManagerInterface<T> {

    T add(T object);

    void remove(T object);
    void removeAll();

    T get(Long number);
    List<T> getAll();

    void serializeToFile(T object);
    void serializeAllToFile(String outputFileName);

    List<T> deserializeFromFile(String inputFileName);

    Document serializeToXML(T element, org.w3c.dom.Document document);
    Document serializeAllToXML(Document document);

    List<T> deserializeFromXML(String inputFileName);

}
