package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class CompartmentService extends CrudService<Compartment> {
    private PersonRepository personRepository;

    @Autowired
    public CompartmentService(CompartmentRepository repository, PersonRepository personRepository) {
        super(repository);
        this.personRepository = personRepository;
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        if (updateEntity.getId() == null) {
            var people = updateEntity.getPeople();
            updateEntity.setPeople(Collections.emptyList());
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

            List<Person> people = updateEntity.getPeople();
            people.forEach(p->p.setCompartment(dbEntity));
            personRepository.saveAll(people);

            return insertedCompartments;
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
