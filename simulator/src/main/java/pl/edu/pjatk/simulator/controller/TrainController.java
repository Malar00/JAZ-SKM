package pl.edu.pjatk.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.service.TrainService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/trains")
public class TrainController extends CrudController<Train> {

    @Autowired
    public TrainController(TrainService service) {
        super(service);
    }

    public Function<Train, Map<String, Object>> transformToDTO() {
        return train -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", train.getId());
            payload.put("rail_line", Station.values()[train.getCurrent_station()]);
            payload.put("current_station", train.getCurrent_station());
            payload.put("going_back", train.getGoing_back());
            payload.put("waiting", train.getWait_time());
            payload.put("compartments", train.getCompartments().stream().map(Compartment::mapToJson));

            return payload;
        };
    }
}
