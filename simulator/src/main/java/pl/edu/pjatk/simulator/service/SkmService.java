package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;
import pl.edu.pjatk.simulator.util.PeopleGen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class SkmService {
    TrainRepository trainRepository;

    CompartmentRepository compartmentRepository;
    PersonRepository personRepository;

    @Autowired
    public SkmService(TrainRepository trainRepository,
                      CompartmentRepository compartmentRepository,
                      PersonRepository personRepository) {
        this.trainRepository = trainRepository;
        this.compartmentRepository = compartmentRepository;
        this.personRepository = personRepository;
    }

    public List<Train> moveTimeForward() {

        List<Train> trains = trainRepository.findAll();
        trains.forEach(t -> {
            if (t.getWait_time() > 0) {
                //Turning around at waiting stations
                if (t.getWait_time() == 2) {
                    t.setGoing_back(!t.getGoing_back());
                }
                t.setWait_time(t.getWait_time() - 1);
            } else {

                List<Compartment> compartments = t.getCompartments();
                compartments.forEach(c -> {
                    List<Person> people = c.getPeople();
                    //People disembarking
                    int currentStation = t.getCurrent_station();
                    List<Person> peopleToRemove = new LinkedList<>();
                    people.forEach(p -> {
                        //Creates a list of people to delete in order to avoid ConcurrentModificationException
                        if (currentStation == p.getDestination()) {
                            peopleToRemove.add(p);
                        }
                    });
                    personRepository.saveAll(people);
                    //Generating people at every station
                    int numberOfPeople = new Random().nextInt(3);
                    while(numberOfPeople>0) {
                        if (c.getCompartment_size() > c.getPeople().size()) {
                            Person person = PeopleGen.genPerson(t);
                            person.setCompartment(c);
                            people.add(person);
                        }
                        else{
                            break;
                        }
                        numberOfPeople--;
                    }
                    people.removeAll(peopleToRemove);
                    c.setPeople(people);
                });
                compartmentRepository.saveAll(compartments);

                //Deciding the direction of the train
                int step = 1;
                if (t.getGoing_back()) {
                    step = -1;
                }
                t.setCompartments(compartments);
                t.setCurrent_station(t.getCurrent_station() + step);

                t.setWait_time(Station.values()[t.getCurrent_station()].getWaitTime());
            }

        });
        trainRepository.saveAll(trains);
        return trains;
    }
}
