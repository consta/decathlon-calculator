package com.kanstantin.decathlon;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XMLMapper {
    public static String writeValue(XmlSerializable instance) {
        Document doc = obtainDocument();
        doc.appendChild(instance.serialize(doc));
        doc.setXmlStandalone(true);
        return transform(doc);
    }

    private static String transform(Document doc) {
        DOMSource domSource = new DOMSource(doc);
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            throw new RuntimeException("Failed to obtain an instance of transformer", ex);
        }

        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");


        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        try {
            transformer.transform(domSource, sr);
        } catch (TransformerException ex) {
            throw new RuntimeException("Failed to render XML document", ex);
        }
        return sw.toString();
    }

    private static Document obtainDocument() {
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();
            doc = impl.createDocument(null, null, null);
        }
        catch (ParserConfigurationException ex) {
            throw new RuntimeException("Failed to build XML document", ex);
        }
        return doc;
    }
}
