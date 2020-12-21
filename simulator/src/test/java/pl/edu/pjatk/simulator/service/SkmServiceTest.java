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
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SkmServiceTest {

    @Mock
    TrainRepository trainRepository;
    @Mock
    CompartmentRepository compartmentRepository;
    @Mock
    PersonRepository personRepository;

    SkmService service;

    @BeforeEach
    public void setup() {
        service = new SkmService(trainRepository, compartmentRepository, personRepository);
    }

    @Test
    public void moveTimeForwardShouldAddPerson() {


        List<Person> peopleList = new LinkedList<>();

        Train train = new Train(2, false, 0);
        Compartment compartment = new Compartment(3, train, peopleList);
        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(compartment);

        train.setCompartments(compartmentList);
        List<Train> trainList = new LinkedList<>();
        trainList.add(train);

        when(trainRepository.findAll()).thenReturn(trainList);
        when(personRepository.saveAll(peopleList)).thenReturn(peopleList);
        when(compartmentRepository.saveAll(compartmentList)).thenReturn(compartmentList);

        List<Train> returnedTrains = service.moveTimeForward();

        assertEquals(train, returnedTrains.get(0));
        assertEquals(compartmentList.get(0), returnedTrains.get(0).getCompartments().get(0));
        assertEquals(1, returnedTrains.get(0).getCompartments().get(0).getPeople().size());
    }

    @Test
    public void moveTimeForwardShouldMoveToTheNextStation() {
        service = new SkmService(trainRepository, compartmentRepository, personRepository);
        final int startingStation = 3;

        List<Person> peopleList = new LinkedList<>();

        Train train = new Train(startingStation, false, 0);
        Compartment compartment = new Compartment(3, train, peopleList);
        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(compartment);

        train.setCompartments(compartmentList);
        List<Train> trainList = new LinkedList<>();
        trainList.add(train);

        when(trainRepository.findAll()).thenReturn(trainList);
        when(personRepository.saveAll(peopleList)).thenReturn(peopleList);
        when(compartmentRepository.saveAll(compartmentList)).thenReturn(compartmentList);

        List<Train> returnedTrains = service.moveTimeForward();

        assertEquals(train, returnedTrains.get(0));
        assertEquals(compartmentList.get(0), returnedTrains.get(0).getCompartments().get(0));
        assertEquals(startingStation + 1, returnedTrains.get(0).getCurrent_station());
    }

    @Test
    public void moveTimeForwardShouldWaitFor2() {
        service = new SkmService(trainRepository, compartmentRepository, personRepository);
        final int startingStation = 1;

        List<Person> peopleList = new LinkedList<>();

        Train train = new Train(startingStation, true, 0);
        Compartment compartment = new Compartment(3, train, peopleList);
        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(compartment);

        train.setCompartments(compartmentList);
        List<Train> trainList = new LinkedList<>();
        trainList.add(train);

        when(trainRepository.findAll()).thenReturn(trainList);
        when(personRepository.saveAll(peopleList)).thenReturn(peopleList);
        when(compartmentRepository.saveAll(compartmentList)).thenReturn(compartmentList);

        List<Train> returnedTrains = service.moveTimeForward();

        assertEquals(train, returnedTrains.get(0));
        assertEquals(compartmentList.get(0), returnedTrains.get(0).getCompartments().get(0));
        assertEquals(startingStation - 1, returnedTrains.get(0).getCurrent_station());
        assertEquals(2, returnedTrains.get(0).getWait_time());
    }

    @Test
    public void moveTimeForwardShouldGoBack() {
        service = new SkmService(trainRepository, compartmentRepository, personRepository);
        final int startingStation = 1;

        List<Person> peopleList = new LinkedList<>();

        Train train = new Train(startingStation, true, 0);
        Compartment compartment = new Compartment(3, train, peopleList);
        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(compartment);

        train.setCompartments(compartmentList);
        List<Train> trainList = new LinkedList<>();
        trainList.add(train);

        when(trainRepository.findAll()).thenReturn(trainList);
        when(personRepository.saveAll(peopleList)).thenReturn(peopleList);
        when(compartmentRepository.saveAll(compartmentList)).thenReturn(compartmentList);

        service.moveTimeForward();  //Wait first turn
        service.moveTimeForward();  //Wait second turn
        List<Train> returnedTrains = service.moveTimeForward();

        assertEquals(train, returnedTrains.get(0));
        assertEquals(compartmentList.get(0), returnedTrains.get(0).getCompartments().get(0));
        assertEquals(startingStation - 1, returnedTrains.get(0).getCurrent_station());
        assertEquals(0, returnedTrains.get(0).getWait_time());
        assertFalse(returnedTrains.get(0).getGoing_back());
    }
}
