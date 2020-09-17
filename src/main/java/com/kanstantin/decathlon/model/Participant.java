package com.kanstantin.decathlon.model;

import com.kanstantin.decathlon.XmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class Participant implements XmlSerializable {
    private String name;
    private List<Event> events = new ArrayList<>();
    private Integer totalScore;
    private String ranking;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    @Override
    public Element serialize(Document document) {
        Element element = document.createElement("participant");
        element.setAttribute("name", getName());
        element.setAttribute("place", getRanking());
        element.setAttribute("totalScore", String.format("%d", getTotalScore()));

        for (Event event: getEvents()) {
            Element eventElement = event.serialize(document);
            element.appendChild(eventElement);
        }
        return element;
    }
}
