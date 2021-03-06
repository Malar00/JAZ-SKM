package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.PersonRepository;

import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class PersonService extends CrudService<Person> {

    public PersonService(PersonRepository repository) {
        super(repository);
    }


    @Override
    public Person createOrUpdate(Person updateEntity) {
        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Person> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();

            dbEntity.setFirst_name(fallbackIfNull(updateEntity.getFirst_name(), dbEntity.getFirst_name()));
            dbEntity.setLast_name(fallbackIfNull(updateEntity.getLast_name(), dbEntity.getLast_name()));
            dbEntity.setDestination(fallbackIfNull(updateEntity.getDestination(), dbEntity.getDestination()));
            dbEntity.setDestination(fallbackIfNull(updateEntity.getDestination(), dbEntity.getDestination()));

            return repository.save(dbEntity);
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }
}
