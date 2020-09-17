package com.kanstantin.decathlon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XmlSerializable {
    Element serialize(Document document);
}
