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
    private final List<String> railLine1 = Arrays.asList("Gdansk Srodmiescie", "Gdansk Glowny", "Gdansk Stocznia",
            "Gdansk Politechnika", "Gdansk Wrzeszcz", "Gdansk Zaspa", "Gdansk Oliwa", "Sopot Wiscigi", "Sopot", "Gdynia Redlowo",
            "Gdynia Orlowo", "Gdynia Glowna", "Gdynia Stocznia", "Gdynia Grabowek", "Gdynia Leszczynki", "Gdynia Chylonia");
    private final List<String> names = Arrays.asList("Letty", "Marion", "Bethann", "Piedad", "Tyrell", "Asia", "Shirely",
            "Hyo", "Kimbra", "Zenobia", "Delores", "Noble", "Rosario", "Louella", "Jackie", "Jc", "Charis", "Pamala",
            "Domitila", "Paul");
    private final List<String> lastName = Arrays.asList("Oconnor" + "Thomas", "Coffey", "Escobar", "Conrad", "Erickson",
            "Watson", "Hensley", "Boone", "Heath", "Riggs", "Rivers", "Clayton", "Robbins", "Galloway", "Bell", "Mason",
            "Ingram", "Thompson", "Wallace");

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

            if (train.getFreeSpace() > 0) {
                int max = train.getRailLine().size();
                int min = train.getCurrentStation() + 1;
                if (min != max) {
                    for (int i = 0; i < new Random().nextInt((8 - 2) + 2); i++) {
                        train.addPassenger(new Person(names.get(new Random().nextInt(names.size())),
                                lastName.get(new Random().nextInt(lastName.size())), train.getCurrentStation(),
                                new Random().nextInt((max - min)) + min));
                        train.setFreeSpace(train.getFreeSpace() - 1);
                        if (train.getFreeSpace() == 0) break;
                    }
                }
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
