package pl.edu.pjatk.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.service.PersonService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/people")
public class PersonController extends CrudController<Person> {
    @Autowired
    public PersonController(PersonService service) {
        super(service);
    }

    @Override
    public Function<Person, Map<String, Object>> transformToDTO() {
        return person -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", person.getId());
            payload.put("first_name", person.getFirst_name());
            payload.put("last_name", person.getLast_name());
            payload.put("compartment", person.getCompartmentId());

            return payload;
        };
    }
}
