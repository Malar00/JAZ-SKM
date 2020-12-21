package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TrainServiceTest {

    @Mock
    CompartmentRepository compartmentRepository;

    @Mock
    TrainRepository trainRepository;

    TrainService service;

    @BeforeEach
    public void setup() {
        service = new TrainService(trainRepository, compartmentRepository);
    }

    @Test
    public void createOrUpdateShouldAddTrain() {
        Train train = new Train();

        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(new Compartment());

        train.setCompartments(compartmentList);

        service.createOrUpdate(train);

        verify(trainRepository).save(train);
    }

    @Test
    public void createOrUpdateShouldUpdateCompartment() {
        final Long id = 1L;
        Train train = new Train();
        train.setId(id);

        List<Compartment> compartmentList = new LinkedList<>();
        compartmentList.add(new Compartment());

        train.setCompartments(compartmentList);

        when(trainRepository.findById(id)).thenReturn(Optional.of(train));

        service.createOrUpdate(train);

        verify(trainRepository).findById(id);
        verify(trainRepository).save(train);
    }

    @Test
    public void getById() {
        final Long id = 1L;
        service.getById(id);
        verify(trainRepository).findById(id);
    }

    @Test
    public void deleteById() {
        final Long id = 1L;
        Train train = new Train();
        train.setId(id);

        when(trainRepository.findById(id)).thenReturn(Optional.of(train));

        service.delete(id);
        verify(trainRepository).delete(train);
    }
}
