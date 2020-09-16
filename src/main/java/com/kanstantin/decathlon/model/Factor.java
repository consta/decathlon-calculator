package com.kanstantin.decathlon.model;

public class Factor {
    private String name;
    private double figA;
    private double figB;
    private double figC;

    public Factor() {

    }

    public Factor(String name, double figA, double figB, double figC) {
        this.name = name;
        this.figA = figA;
        this.figB = figB;
        this.figC = figC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFigA() {
        return figA;
    }

    public void setFigA(double figA) {
        this.figA = figA;
    }

    public double getFigB() {
        return figB;
    }

    public void setFigB(double figB) {
        this.figB = figB;
    }

    public double getFigC() {
        return figC;
    }

    public void setFigC(double figC) {
        this.figC = figC;
    }
}
