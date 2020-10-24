package pl.pjatk.skmapi.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pjatk.skmapi.model.Train;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrainController {
    private List<Train> trainCatalogue;
    private String[] railLine1 = new String[]{"Gdansk Glowny", "Gdansk Wrzeszcz", "Gdansk Oliwa", "Sopot",
            "Gdynia Orlowo", "Gdynia Glowna", "Gdynia Chylonia"};

    public TrainController() {
        trainCatalogue = new ArrayList<>();
        trainCatalogue.add(new Train(0, 3, 4, railLine1));
        trainCatalogue.add(new Train(1, 4, 4, railLine1));
        trainCatalogue.add(new Train(2, 5, 4, railLine1));
    }

    @GetMapping("/train")
    public List<Train> getTrainCatalogue() {
        return trainCatalogue;
    }

    @GetMapping(value = "/train/{id}")
    public Train getTrainInfo(@PathVariable Integer id) {
        return trainCatalogue.get(id);
    }
}
