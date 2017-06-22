package com.example.sa.students_android.MyUtilMethods;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;

public class XMLStuff {

    public static Document prepareXMLFile() {
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            return doc;
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static <T> Document processField(Document doc, Element rootElement, Element fieldName, Field field, T currentElement) {

        try {
            String type = field.getType().toString();
            String name = field.getName().toString();
            String value = "";
            if(field.get(currentElement) != null)
                value = field.get(currentElement).toString();

            Attr attrType = doc.createAttribute("type");
            attrType.setValue(type);
            fieldName.setAttributeNode(attrType);

            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            fieldName.setAttributeNode(attrName);

            Attr attrValue = doc.createAttribute("value");
            attrValue.setValue(value);
            fieldName.setAttributeNode(attrValue);

            rootElement.appendChild(fieldName);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static void produceXMLFile(Document document, String fileName) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName + ".xml"));
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
