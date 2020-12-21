package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "people")
public class Person implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private int destination;
    @ManyToOne(fetch = FetchType.LAZY)
    private Compartment compartment;


    public Person(String first_name, String last_name, int destination) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.destination = destination;
    }


    public Person() {

    }


    public Map<String, Object> mapToJson() {
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("id", id);
        responseObj.put("first_name", first_name);
        responseObj.put("lastname_name", last_name);
        responseObj.put("destination", destination);
        return responseObj;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public Long getCompartmentId() {
        return compartment.getId();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String firstName) {
        this.first_name = firstName;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String lastName) {
        this.last_name = lastName;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }


    @Override
    public Long getId() {
        return id;
    }
}
