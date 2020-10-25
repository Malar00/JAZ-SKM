package pl.pjatk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.skmclient.model.Train;

import java.util.Arrays;
import java.util.List;

@RestController
public class TrainClientController {
    private final String URI = "http://skmapi:11111/train";

    @GetMapping("/trains")
    public List<Train> getCatalogue() {
        RestTemplate template = new RestTemplate();
        Train[] catalogue = template.getForObject(URI, Train[].class);
        return Arrays.asList(catalogue);

    }
}
