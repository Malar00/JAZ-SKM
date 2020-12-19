package pl.edu.pjatk.simulator.service;

import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.TrainRepository;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class TrainService extends CrudService<Train> {
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private CompartmentRepository compartmentRepository;

    public TrainService(TrainRepository repository) {
        super(repository);
    }

    public void moveTimeForward() {
        //System.out.println("train move start");
        trainRepository.findAll().forEach(t -> {
            //Person person = new Person("aa","bb",1);
            //t.getCompartments().forEach(c -> {
              //  compartmentRepository.getOne(c.getId()).addPerson(person);
            //});
        });
        //System.out.println("train moved");
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
