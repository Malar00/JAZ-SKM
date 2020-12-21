package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.PersonRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    PersonService service;

    @BeforeEach
    public void setup() {
        service = new PersonService(personRepository);
    }

    @Test
    public void createOrUpdateShouldAddPerson() {
        final String f_name = "test_name";
        final String l_name = "test_lname";
        final int destination = 4;
        Person newPerson = new Person(f_name, l_name, destination);

        service.createOrUpdate(newPerson);

        verify(personRepository).save(newPerson);
    }

    @Test
    public void createOrUpdateShouldUpdatePerson() {
        final String f_name = "test_name";
        final String l_name = "test_lname";
        final int destination = 4;
        final Long id = 1L;
        Person newPerson = new Person(f_name, l_name, destination);
        newPerson.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(newPerson));

        service.createOrUpdate(newPerson);

        verify(personRepository).findById(id);
        verify(personRepository).save(newPerson);
    }

    @Test
    public void getById() {
        final Long id = 1L;
        service.getById(id);
        verify(personRepository).findById(id);
    }

    @Test
    public void deleteById() {
        final Long id = 1L;
        Person person = new Person("test", "test", 3);
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        service.delete(id);
        verify(personRepository).delete(person);
    }
}
