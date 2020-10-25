package pl.pjatk.skmapi.model;

public class Person {
    private String name;
    private String lastName;
    private int destination;

    public Person(String name, String lastName, int destination) {
        this.name = name;
        this.lastName = lastName;
        this.destination = destination;
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
