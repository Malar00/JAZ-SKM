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
        Person person = new Person();
        person.setFirst_name("name");
        person.setLast_name("lname");
        person.setDestination(4);

        Train train = repository.getOne(1L);
        Set<Compartment> compartments = train.getCompartments();
        compartments.forEach(c -> {
            Set<Person> people = c.getPeople();
            person.setCompartment(c);
            people.add(person);
            c.setPeople(people);
        });
        train.setCompartments(compartments);
        createOrUpdate(train);

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
