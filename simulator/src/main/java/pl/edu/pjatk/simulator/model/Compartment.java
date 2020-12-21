package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "compartments")
public class Compartment implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "compartment_size")
    private int compartment_size;
    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;
    @OneToMany(mappedBy = "compartment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Person> people;

    public Compartment() {
    }

    public Compartment(int compartment_size, Train train, List<Person> people) {
        this.compartment_size = compartment_size;
        this.train = train;
        this.people = people;
    }

    public Map<String, Object> mapToJson() {
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("id", id);
        responseObj.put("compartment_size", compartment_size);
        responseObj.put("free_space", compartment_size - people.size());
        responseObj.put("people", people.stream().map(Person::mapToJson));
        return responseObj;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCompartment_size() {
        return compartment_size;
    }

    public void setCompartment_size(int compartment_size) {
        this.compartment_size = compartment_size;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public Long getId() {
        return id;
    }
}
