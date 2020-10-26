package pl.pjatk.skmclient.model;

public class Person {
    private String name;
    private String lastName;
    private int origin;
    private int destination;

    public Person(){}

    public Person(String name, String lastName,int origin, int destination) {
        this.name = name;
        this.lastName = lastName;
        this.origin = origin;
        this.destination = destination;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDestination() {
        return destination;
    }
}
