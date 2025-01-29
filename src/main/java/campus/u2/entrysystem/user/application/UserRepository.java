package campus.u2.entrysystem.user.application;

import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    User createUser(Porters porter, User user);


    Optional<User> findByPorter(Porters porter);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByuserName(String name);

}
