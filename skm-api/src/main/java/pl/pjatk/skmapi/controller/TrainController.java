package pl.pjatk.skmapi.controller;

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

    public TrainController() {
        trainCatalogue = new ArrayList<>();
        trainCatalogue.add(new Train(0, 6, 4, railLine1, 0));
        trainCatalogue.add(new Train(1, 8, 4, railLine1, 3));
        trainCatalogue.add(new Train(2, 10, 4, railLine1, 4));
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
            train.setCurrentStation(train.getCurrentStation() + 1);
            train.setNameOfCurrentStation(railLine1.get(train.getCurrentStation()));
            int max = train.getRailLine().size();
            int min = train.getCurrentStation();
            train.addPassenger(new Person("Test1", "Test1",new Random().nextInt((max - min) + 1) + min));
        }
    }
}
