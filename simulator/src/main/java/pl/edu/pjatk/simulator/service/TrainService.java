package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class TrainService extends CrudService<Train> {
    private CompartmentRepository compartmentRepository;

    @Autowired
    public TrainService(TrainRepository repository, CompartmentRepository compartmentRepository) {
        super(repository);
        this.compartmentRepository = compartmentRepository;
    }

    @Override
    public Train createOrUpdate(Train updateEntity) {
        if (updateEntity.getId() == null) {
            var compartments = updateEntity.getCompartments();
            updateEntity.setCompartments(Collections.emptyList());
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
            dbEntity.setWait_time(fallbackIfNull(updateEntity.getWait_time(), dbEntity.getWait_time()));
            dbEntity.setCurrent_station(fallbackIfNull(updateEntity.getCurrent_station(), dbEntity.getCurrent_station()));

            var insertedTrain = repository.save(dbEntity);

            List<Compartment> compartments = updateEntity.getCompartments();
            compartments.forEach(compartment -> compartment.setTrain(dbEntity));
            compartmentRepository.saveAll(compartments);

            return insertedTrain;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
