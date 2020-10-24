package pl.pjatk.skmapi.model;

import java.util.List;

public class Train {

    private int ID;
    private int numberOfCompartments;
    private int sizeOfCompartment;
    private String[] railLine;

    public Train(){}

    public Train(int ID, int numberOfCompartments, int sizeOfCompartment, String[] railLine) {
        this.ID = ID;
        this.numberOfCompartments = numberOfCompartments;
        this.sizeOfCompartment = sizeOfCompartment;
        this.railLine = railLine;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNumberOfCompartments(int numberOfCompartments) {
        this.numberOfCompartments = numberOfCompartments;
    }

    public void setSizeOfCompartment(int sizeOfCompartment) {
        this.sizeOfCompartment = sizeOfCompartment;
    }

    public void setRailLine(String[] railLine) {
        this.railLine = railLine;
    }

    public int getID() {
        return ID;
    }

    public int getNumberOfCompartments() {
        return numberOfCompartments;
    }

    public int getSizeOfCompartment() {
        return sizeOfCompartment;
    }

    public String[] getRailLine() {
        return railLine;
    }
}
