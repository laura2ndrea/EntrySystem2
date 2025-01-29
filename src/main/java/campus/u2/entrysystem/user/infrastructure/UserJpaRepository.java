package campus.u2.entrysystem.user.infrastructure;

import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.user.domain.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByPorter(Porters porter);

    Optional<User> findByuserName(String name);

}
