package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "trains")
public class Train implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_station")
    private int current_station;
    private boolean going_back;
    private int wait_time;
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compartment> compartments;


    public Train() {
    }

    public Train(int current_station, boolean going_back, int wait_time) {
        this.current_station = current_station;
        this.going_back = going_back;
        this.wait_time = wait_time;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
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

    public List<Compartment> getCompartments() {
        return compartments;
    }

    public void setCompartments(List<Compartment> compartments) {
        this.compartments = compartments;
    }

    @Override
    public Long getId() {
        return id;
    }
}