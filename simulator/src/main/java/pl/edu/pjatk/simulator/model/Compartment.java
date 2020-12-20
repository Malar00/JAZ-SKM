package pl.edu.pjatk.simulator.model;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.service.CompartmentService;
import pl.edu.pjatk.simulator.service.DbEntity;
import pl.edu.pjatk.simulator.service.PersonService;
import pl.edu.pjatk.simulator.util.PeopleGen;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Set<Person> people;

    public Compartment() {
    }

    public Map<String, Object> mapToJson() {
        Map<String, Object> responseObj = new HashMap<String, Object>();
        responseObj.put("id", id);
        responseObj.put("compartment_size", compartment_size);
        responseObj.put("free_space", compartment_size - people.size());
        responseObj.put("people", people.stream().map(Person::mapToJson));
        return responseObj;
    }

    public void deletePerson(Person person){
        people.remove(person);
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

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    @Override
    public Long getId() {
        return id;
    }
}
