package campus.u2.entrysystem.user.infrastructure;

import campus.u2.entrysystem.Utilities.RegisterUser;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.porters.infrastructure.PortersJpaRepository;
import campus.u2.entrysystem.user.application.UserRepository;
import campus.u2.entrysystem.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final PortersJpaRepository porterJpaRepository;  
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAdapter(UserJpaRepository userJpaRepository, PortersJpaRepository porterJpaRepository, PasswordEncoder passwordEncoder){
        this.userJpaRepository = userJpaRepository;
        this.porterJpaRepository = porterJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public User createUser(Porters porter, User user) {        
        user.setPorter(porter);
        porter.setUser(user);
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByPorter(Porters porter) {
        return userJpaRepository.findByPorter(porter);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id); 
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByuserName(String name) {
        return userJpaRepository.findByuserName(name);
    }

    @Override
    @Transactional
    public User register(RegisterUser registerUser) {
        Porters porter = porterJpaRepository.findByCedula(registerUser.getCedula());
        if (porter == null) {
            porter = new Porters();
            porter.setName(registerUser.getName());
            porter.setCedula(registerUser.getCedula());
            porter.setTelefono(registerUser.getTelefono());
            porter.setEmploymentDate(registerUser.getEmploymentDate());
            porterJpaRepository.save(porter);
        }

        String encryptedPassword = passwordEncoder.encode(registerUser.getPassword());

        User user = new User(registerUser.getUserName(), encryptedPassword);
        user.setPorter(porter);

        return userJpaRepository.save(user);
    }
}
