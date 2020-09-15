package com.kanstantin.decathlon.model;

import java.util.HashSet;
import java.util.Set;

public class Participant {
    private String name;
    private Set<Result> results = new HashSet<Result>();

    public Participant() {

    }

    public Participant(String name, Set<Result> results) {
        this.name = name;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }
}
