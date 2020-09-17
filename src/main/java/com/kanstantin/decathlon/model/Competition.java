package com.kanstantin.decathlon.model;

import com.kanstantin.decathlon.Utils;
import com.kanstantin.decathlon.XmlSerializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Competition implements XmlSerializable {
    private String name;
    private List<Participant> participants = new ArrayList<>();

    public void doRankParticipants() {
        Map<Integer, List<Participant>> rankingMap = new TreeMap<>(Collections.reverseOrder());
        participants.forEach(p -> {
            List<Participant> participantList = rankingMap.getOrDefault(p.getTotalScore(), new ArrayList<>());
            participantList.add(p);
            rankingMap.putIfAbsent(p.getTotalScore(), participantList);
        });

        List<Participant> rankedParticipantList = new ArrayList<>();
        int i = 1;
        for (Map.Entry<Integer, List<Participant>> entry: rankingMap.entrySet()) {
            String ranking = Utils.ranking(i, entry.getValue().size());
            entry.getValue().forEach(p -> {
                p.setRanking(ranking);
                rankedParticipantList.add(p);
            });
            i += entry.getValue().size();
        }
        participants = rankedParticipantList;
    }

    @Override
    public Element serialize(Document document) {

        Element element = document.createElement("competition");
        element.setAttribute("name", getName());

        for (Participant p : getParticipants()) {
            Element e = p.serialize(document);
            element.appendChild(e);
        }

        return element;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
