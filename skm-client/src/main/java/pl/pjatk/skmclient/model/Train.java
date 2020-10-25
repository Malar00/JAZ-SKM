package pl.pjatk.skmclient.model;

import java.util.List;

public class Train {

    private int ID;
    private int numberOfCompartments;
    private int sizeOfCompartment;
    private List<String> railLine;
    private int currentStation;
    private String nameOfCurrentStation;
    private int freeSpace;
    private int takenSpace;

    public Train() {
    }

    public Train(int ID, int numberOfCompartments, int sizeOfCompartment, List<String> railLine, int currentStation, String nameOfCurrentStation, int freeSpace, int takenSpace) {
        this.ID = ID;
        this.numberOfCompartments = numberOfCompartments;
        this.sizeOfCompartment = sizeOfCompartment;
        this.railLine = railLine;
        this.currentStation = currentStation;
        this.nameOfCurrentStation = nameOfCurrentStation;
        this.freeSpace = freeSpace;
        this.takenSpace = takenSpace;
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

    public List<String> getRailLine() {
        return railLine;
    }

    public int getCurrentStation() {
        return currentStation;
    }

    public String getNameOfCurrentStation() {
        return nameOfCurrentStation;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getTakenSpace() {
        return takenSpace;
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

    public void setRailLine(List<String> railLine) {
        this.railLine = railLine;
    }

    public void setCurrentStation(int currentStation) {
        this.currentStation = currentStation;
    }

    public void setNameOfCurrentStation(String nameOfCurrentStation) {
        this.nameOfCurrentStation = nameOfCurrentStation;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public void setTakenSpace(int takenSpace) {
        this.takenSpace = takenSpace;
    }
}