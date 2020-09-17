package com.kanstantin.decathlon.model;

import com.kanstantin.decathlon.XmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Event implements XmlSerializable {
    private String name;
    private double performance;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public Element serialize(Document document) {
        Element element = document.createElement("event");
        element.setAttribute("name", getName());
        element.setAttribute("performance", String.format("%.2f", getPerformance()));
        element.setAttribute("score", String.format("%d", score));
        return element;
    }
}
