package pl.edu.pjatk.simulator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.controller.CrudController;
import pl.edu.pjatk.simulator.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<User> {
    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @Override
    public Function<User, Map<String, Object>> transformToDTO() {
        return user -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("password", user.getPassword());
            payload.put("authority", user.getAuthority());
            return payload;
        };
    }
}
