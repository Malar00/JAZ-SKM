package pl.pjatk.skmapi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Train {

    private int ID;
    private int numberOfCompartments;
    private int sizeOfCompartment;
    private List<String> railLine;
    private int currentStation;
    private String nameOfCurrentStation;
    private int freeSpace;
    private List<Person> passengers;
    private boolean wait;

    public Train() {
    }

    public Train(int ID, int numberOfCompartments, int sizeOfCompartment, List<String> railLine, int currentStation) {
        this.ID = ID;
        this.numberOfCompartments = numberOfCompartments;
        this.sizeOfCompartment = sizeOfCompartment;
        this.railLine = railLine;
        this.currentStation = currentStation;
        this.nameOfCurrentStation = railLine.get(currentStation);
        this.freeSpace = numberOfCompartments * sizeOfCompartment;
        this.passengers = new ArrayList<>();
        this.wait=false;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public boolean isWait() {
        return wait;
    }

    private static<T> List<T> reverseList(List<T> list)
    {
        List<T> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);
        return reverse;
    }

    public void turnBack(){
        railLine = reverseList(railLine);
        setWait(true);
    }

    public void removePassengers(int destination){
        passengers.removeIf(person -> person.getDestination() == destination);
    }

    public void setPassengers(List<Person> passengers) {
        this.passengers = passengers;
    }
    public void addPassenger(Person person) {
        this.passengers.add(person);
    }

    public List<Person> getPassengers() {
        return passengers;
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

}