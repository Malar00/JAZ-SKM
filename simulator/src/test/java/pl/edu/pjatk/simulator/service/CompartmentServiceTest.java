package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
public class CompartmentServiceTest {

    @Mock
    CompartmentRepository compartmentRepository;

    @Mock
    PersonRepository personRepository;

    CompartmentService service;

    @BeforeEach
    public void setup() {
        service = new CompartmentService(compartmentRepository, personRepository);
    }

    @Test
    public void createOrUpdateShouldAddCompartment() {
        Train train = new Train();

        List<Person> peopleList = new LinkedList<>();
        peopleList.add(new Person("test", "test", 4));

        Compartment compartment = new Compartment();
        compartment.setPeople(peopleList);
        compartment.setTrain(train);

        when(personRepository.saveAll(peopleList)).thenReturn(peopleList);

        service.createOrUpdate(compartment);

        verify(compartmentRepository, times(2)).save(compartment);
        verify(personRepository).saveAll(peopleList);
    }

    @Test
    public void createOrUpdateShouldUpdateCompartment() {
        final Long id = 1L;
        List<Person> peopleList = new LinkedList<>();
        peopleList.add(new Person("test", "test", 4));

        Compartment compartment = new Compartment();
        compartment.setId(id);
        compartment.setPeople(peopleList);

        when(compartmentRepository.findById(id)).thenReturn(Optional.of(compartment));

        service.createOrUpdate(compartment);

        verify(compartmentRepository).findById(id);
        verify(compartmentRepository).save(compartment);
    }

    @Test
    public void getById() {
        final Long id = 1L;
        service.getById(id);
        verify(compartmentRepository).findById(id);
    }

    @Test
    public void deleteById() {
        final Long id = 1L;
        Compartment compartment = new Compartment();
        compartment.setId(id);

        when(compartmentRepository.findById(id)).thenReturn(Optional.of(compartment));

        service.delete(id);
        verify(compartmentRepository).delete(compartment);
    }
}
