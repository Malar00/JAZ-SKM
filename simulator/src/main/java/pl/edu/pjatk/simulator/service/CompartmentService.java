package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class CompartmentService extends CrudService<Compartment> {
    @Autowired
    private CompartmentRepository compartmentRepository;
    @Autowired
    private PersonRepository personRepository;

    public CompartmentService(CompartmentRepository repository) {
        super(repository);
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        if (updateEntity.getId() == null) {
            var people = updateEntity.getPeople();
            updateEntity.setPeople(Collections.emptySet());
            Compartment insertedCompartment = repository.save(updateEntity);

            people.forEach(p->p.setCompartment(insertedCompartment));
            personRepository.saveAll(people);
        }

        Optional<Compartment> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();

            dbEntity.setCompartment_size(fallbackIfNull(updateEntity.getCompartment_size(), dbEntity.getCompartment_size()));
            dbEntity.setPeople(fallbackIfNull(updateEntity.getPeople(), dbEntity.getPeople()));
            dbEntity.setTrain(fallbackIfNull(updateEntity.getTrain(), dbEntity.getTrain()));
            var insertedCompartments = repository.save(dbEntity);

            Set<Person> people = updateEntity.getPeople();
            people.forEach(p->p.setCompartment(dbEntity));
            personRepository.saveAll(people);

            return insertedCompartments;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
