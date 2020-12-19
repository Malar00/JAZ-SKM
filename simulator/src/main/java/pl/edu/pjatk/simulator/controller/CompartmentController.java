package pl.edu.pjatk.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.service.CompartmentService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/compartments")
public class CompartmentController extends CrudController<Compartment> {
    @Autowired
    public CompartmentController(CompartmentService service) {
        super(service);
    }

    @Override
    public Function<Compartment, Map<String, Object>> transformToDTO() {
        return compartments -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", compartments.getId());
            payload.put("size", compartments.getCompartment_size());
            payload.put("people", compartments.getPeople().stream().map(Person::mapToJson));
            payload.put("train_id", compartments.getTrain().getId());

            return payload;
        };
    }
}
