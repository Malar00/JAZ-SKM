package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.repository.UserRepository;
import pl.edu.pjatk.simulator.security.User;

import java.util.Optional;

import static pl.edu.pjatk.simulator.util.Utils.fallbackIfNull;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        repository.save( new User("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findAll().stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }

    @Override
    public User createOrUpdate(User updateEntity) {
        if (updateEntity.getId() == null) {
            updateEntity.setPassword(passwordEncoder.encode(updateEntity.getPassword()));
            return repository.save(updateEntity);
        }

        Optional<User> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();

            dbEntity.setUsername(fallbackIfNull(updateEntity.getUsername(), dbEntity.getUsername()));
            dbEntity.setPassword(
                    fallbackIfNull(passwordEncoder.encode(updateEntity.getPassword()),
                            passwordEncoder.encode(dbEntity.getPassword()))
            );
            dbEntity.setAuthority(fallbackIfNull(updateEntity.getAuthority(), dbEntity.getAuthority()));

            return repository.save(dbEntity);
        } else {
            updateEntity = repository.save(updateEntity);

            return updateEntity;
        }
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}