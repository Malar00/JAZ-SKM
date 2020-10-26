package pl.pjatk.skmapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.skmapi.model.Person;
import pl.pjatk.skmapi.model.Train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class TrainController {
    private List<Train> trainCatalogue;
    private List<String> railLine1 = Arrays.asList("Gdansk Glowny", "Gdansk Wrzeszcz", "Gdansk Oliwa", "Sopot",
            "Gdynia Orlowo", "Gdynia Glowna", "Gdynia Chylonia");


    public TrainController(@Value("${train.numberOfTrains}") final Integer numberOfTrains,
                           @Value("${train.numberOfCompartments}") final Integer numberOfCompartments,
                           @Value("${train.sizeOfCompartment}") final Integer sizeOfCompartment) {
        trainCatalogue = new ArrayList<>();
        for (int i = 0; i < numberOfTrains; i++) {
            trainCatalogue.add(new Train(i, numberOfCompartments, sizeOfCompartment, railLine1, new Random().nextInt((railLine1.size()))));
        }
    }

    @GetMapping("/train")
    public List<Train> getTrainCatalogue() {
        return trainCatalogue;
    }

    @GetMapping(value = "/train/{id}")
    public Train getTrainInfo(@PathVariable Integer id) {
        return trainCatalogue.get(id);
    }

    @GetMapping("/tick")
    public void timeTick() {
        for (Train train : trainCatalogue) {
            train.removePassengers(train.getCurrentStation());

            if (train.isWait()) {
                train.setWait(false);
                break;
            }

            int max = train.getRailLine().size();
            int min = train.getCurrentStation() + 1;
            if (min != max) {
                train.addPassenger(new Person("" + train.getCurrentStation(), "Test1", new Random().nextInt((max - min)) + min));
            }
            if (train.getCurrentStation() == train.getRailLine().size() - 1) {
                train.turnBack();
                train.setCurrentStation(0);
            } else {
                train.setCurrentStation(train.getCurrentStation() + 1);
                train.setNameOfCurrentStation(train.getRailLine().get(train.getCurrentStation()));
            }
        }
    }
}
