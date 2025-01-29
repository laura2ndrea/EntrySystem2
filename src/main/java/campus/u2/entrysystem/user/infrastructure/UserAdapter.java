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
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final PortersJpaRepository porterJpaRepository;  
    
    @Autowired
    public UserAdapter(UserJpaRepository userJpaRepository, PortersJpaRepository porterJpaRepository){
        this.userJpaRepository = userJpaRepository;
        this.porterJpaRepository= porterJpaRepository;
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
        Optional<User> existingUserOpt = userJpaRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            userJpaRepository.delete(existingUserOpt.get());
        }
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
    public User register(RegisterUser registerUser) {
        Porters porter = porterJpaRepository.findByCedula(registerUser.getCedula());

        Porters Newporter = new Porters();
        Newporter.setName(registerUser.getName());
        Newporter.setCedula(registerUser.getCedula());
        Newporter.setTelefono(registerUser.getTelefono());
        Newporter.setEmploymentDate(registerUser.getEmploymentDate());
        
        porterJpaRepository.save(Newporter);
        User user = new User(registerUser.getUserName(), registerUser.getPassword());
        user.setPorter(Newporter);

        return userJpaRepository.save(user);
    }

}
