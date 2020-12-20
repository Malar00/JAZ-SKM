package pl.edu.pjatk.simulator.service;

import liquibase.pro.packaged.A;
import liquibase.pro.packaged.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.PersonRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.util.PeopleGen;

import java.util.*;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class TrainService extends CrudService<Train> {
    @Autowired
    private CompartmentRepository compartmentRepository;

    public TrainService(TrainRepository repository) {
        super(repository);
    }

    public void moveTimeForward() {
        System.out.println("train move start");

        List<Train> trains = repository.findAll();
        trains.forEach(t -> {

            // TODO:Waiting system
            //Turning around at waiting stations
            if (Station.values()[t.getCurrent_station()].getWaitTime() == 2) {
                t.setGoing_back(!t.getGoing_back());
            }

            Set<Compartment> compartments = t.getCompartments();
            compartments.forEach(c -> {

                //Generating people at every station
                if (c.getCompartment_size() > c.getPeople().size()) {
                    Person person = PeopleGen.genPerson(t);

                    Set<Person> people = c.getPeople();
                    person.setCompartment(c);
                    people.add(person);
                    c.setPeople(people);
                }
            });
            
            //Deciding the direction of the train
            int step = 1;
            if (t.getGoing_back()) {
                step = -1;
            }
            t.setCompartments(compartments);
            t.setCurrent_station(t.getCurrent_station() + step);

            createOrUpdate(t);
        });
        System.out.println("train moved");
    }

    @Override
    public Train createOrUpdate(Train updateEntity) {
        if (updateEntity.getId() == null) {
            var compartments = updateEntity.getCompartments();
            updateEntity.setCompartments(Collections.emptySet());
            Train insertedTrain = repository.save(updateEntity);

            compartments.forEach(compartment -> compartment.setTrain(insertedTrain));
            compartmentRepository.saveAll(compartments);

            return insertedTrain;
        }

        Optional<Train> TrainInDb = repository.findById(updateEntity.getId());

        if (TrainInDb.isPresent()) {
            var dbEntity = TrainInDb.get();

            dbEntity.setCompartments(fallbackIfNull(updateEntity.getCompartments(), dbEntity.getCompartments()));
            dbEntity.setCompartments(fallbackIfNull(updateEntity.getCompartments(), dbEntity.getCompartments()));

            dbEntity.setGoing_back(fallbackIfNull(updateEntity.getGoing_back(), dbEntity.getGoing_back()));
            dbEntity.setCurrent_station(fallbackIfNull(updateEntity.getCurrent_station(), dbEntity.getCurrent_station()));

            var insertedTrain = repository.save(dbEntity);

            Set<Compartment> compartments = updateEntity.getCompartments();
            compartments.forEach(compartment -> compartment.setTrain(dbEntity));
            compartmentRepository.saveAll(compartments);

            return insertedTrain;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
