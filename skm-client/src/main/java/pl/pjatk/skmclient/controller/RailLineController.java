package pl.pjatk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.skmclient.model.RailLine;

import java.util.Arrays;
import java.util.List;

@RestController
public class RailLineController {
    private final String URI = "http://skmapi:11111/rail";

    @GetMapping("/readCatalogue")
    public List<RailLine> getCatalogue() {
        RestTemplate template = new RestTemplate();
        RailLine[] catalogue = template.getForObject(URI, RailLine[].class);
        return Arrays.asList(catalogue);

    }
}
