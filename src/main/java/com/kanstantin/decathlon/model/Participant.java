package com.kanstantin.decathlon.model;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    private String name;
    private List<Result> results = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
