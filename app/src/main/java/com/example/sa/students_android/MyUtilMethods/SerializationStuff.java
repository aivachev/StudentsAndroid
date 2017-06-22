package com.example.sa.students_android.MyUtilMethods;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sa on 19.06.17.
 */
public class SerializationStuff {

    public static void serializeToFile(Object object, String fileName) {

        try (FileOutputStream out = new FileOutputStream(fileName + ".ser");
             ObjectOutputStream oout = new ObjectOutputStream(out)) {

            oout.writeObject(object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void serializeAllToFile(HashMap hashMap, String fileName) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            for (Object o : hashMap.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                objectOutputStream.writeObject(pair.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> deserializeFromFile(String inputFileName) {
        List<T> result = new ArrayList<>();
        T content;

        try(ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(inputFileName))) {

            while ((content = (T) ois.readObject()) != null)
                result.add(content);

        } catch (Exception ex) {
            if(ex instanceof EOFException)
                return result;
            ex.printStackTrace();
        }
        return result;
    }

    public static <T> Document serializeToXML(T element, Document document, String elementClass) {

        Document doc = document;

        Element rootElement = doc.getDocumentElement();
        Element mainDocUnit = doc.createElement("Element");

        if(rootElement == null) {

            switch (elementClass) {
                case "User":
                    rootElement = doc.createElement("Users");
                    break;
                case "Group":
                    rootElement = doc.createElement("Groups");
                    break;
                case "Lesson":
                    rootElement = doc.createElement("Lessons");
                    break;
            }
            doc.appendChild(rootElement);
//            if (element.getClass().isAssignableFrom(User.class))
//                rootElement = doc.createElement("Users");
//            else if (element.getClass().isAssignableFrom(Lesson.class))
//                rootElement = doc.createElement("Lessons");
//            else if (element.getClass().isAssignableFrom(Group.class))
//                rootElement = doc.createElement("Groups");
//            else if (element.getClass().isAssignableFrom(Journal.class))
//                rootElement = doc.createElement("Journals");
        }

        rootElement.appendChild(mainDocUnit);
        Element fieldsSection = doc.createElement("Fields");
        mainDocUnit.appendChild(fieldsSection);

        T currentElement = element;

        Field[] elementFields = currentElement.getClass().getDeclaredFields();
        for (Field field : elementFields) {

            field.setAccessible(true);

            boolean isCollection = HashMap.class.isAssignableFrom(field.getType());

            if (!isCollection) {
                Element fieldName = doc.createElement("field");
                doc = XMLStuff.processField(doc, fieldsSection, fieldName, field, currentElement);
            } else {
                try {
                    Element collectionRootElement = doc.createElement("field-collection");
                    //fieldsSection.appendChild(collectionRootElement);
                    HashMap innerMap = (HashMap) field.get(currentElement);

                    for(Object o : innerMap.entrySet()) {
                        Element innerFieldName = doc.createElement("collection-element");
                        collectionRootElement.appendChild(innerFieldName);
                        Object mapObj = ((Map.Entry) o).getValue();
                        Field[] innerFields = mapObj.getClass().getDeclaredFields();

                        for(Field iF : innerFields) {
                            iF.setAccessible(true);
                            Element innerInnerFieldName = doc.createElement("element-field");
                            doc = XMLStuff.processField(doc, innerFieldName, innerInnerFieldName, iF, mapObj);
                        }
                    }
                    doc = XMLStuff.processField(doc, fieldsSection, collectionRootElement, field, currentElement);
                    fieldsSection.appendChild(collectionRootElement);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        Element methodsSection = doc.createElement("Methods");
        mainDocUnit.appendChild(methodsSection);

        Method[] elementMethods = currentElement.getClass().getDeclaredMethods();
        for (Method method : elementMethods) {

            method.setAccessible(true);

            String name = method.getName().toString();
            String returnType = method.getReturnType().toString();

            Element methodName = doc.createElement("method");

            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            methodName.setAttributeNode(attrName);

            Attr attrReturn = doc.createAttribute("return");
            attrReturn.setValue(returnType);
            methodName.setAttributeNode(attrReturn);

            methodsSection.appendChild(methodName);
        }
        return doc;
    }

    public static <T> Document serializeAllToXML(Document document, HashMap<Long, T> hashMap, String elementClass) {
        for(T element : hashMap.values())
            serializeToXML(element, document, elementClass);
        return document;
    }
}
