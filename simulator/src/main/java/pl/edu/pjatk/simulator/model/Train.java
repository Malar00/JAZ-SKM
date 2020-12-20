package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "trains")
public class Train implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_station")
    private int current_station;
    private boolean going_back;
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Compartment> compartments;
    //@Transient
    //private Station railLine;

    public Train() {
        //this.railLine = Station.values()[current_station];
    }

    public boolean getGoing_back() {
        return going_back;
    }

    public void setGoing_back(boolean going_back) {
        this.going_back = going_back;
    }

    public int getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(int currentStation) {
        this.current_station = currentStation;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Set<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(Set<Compartment> compartments) {
        this.compartments = compartments;
    }

    @Override
    public Long getId() {
        return id;
    }
}