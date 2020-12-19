package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrainServiceTest {

    @Mock
    TrainRepository trainRepository;
    @Mock
    Compartment compartment;

    TrainService trainService = new TrainService(trainRepository);

   /* @Test
    public void moveTimeForwardShouldAddPerson() {
        Train train = new Train();

        Person newPerson = new Person("test", "test", 1);
        Set<Person> personList = new HashSet<>();
        personList.add(newPerson);

        Compartment compartment = new Compartment(personList);
        Set<Compartment> compartmentSet = new HashSet<>();
        compartmentSet.add(compartment);

        train.setCompartments(compartmentSet);
        List<Train> trainList = new LinkedList<>();
        trainList.add(train);

        when(trainRepository.findAll()).thenReturn(trainList);
        trainService.moveTimeForward();
        when(compartment.addPerson(newPerson)).thenReturn(newPerson);
        compartment.addPerson(newPerson);

        assertTrue(compartment.getPeople().contains(newPerson));
    }*/
}
