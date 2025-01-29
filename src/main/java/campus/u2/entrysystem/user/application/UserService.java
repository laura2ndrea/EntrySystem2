package campus.u2.entrysystem.user.application;

import campus.u2.entrysystem.Utilities.RegisterUser;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.porters.application.PortersRepository;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.user.domain.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PortersRepository porterRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PortersRepository porterRepository) {
        this.userRepository = userRepository;
        this.porterRepository = porterRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // To save a User
    @Transactional
    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }

    // To create a User for a Porter
    @Transactional
    public User createUser(Porters porter, User user) {
        if (porter == null) {
            throw new GlobalException("Porter cannot be empty");
        }
        if (user == null) {
            throw new GlobalException("User cannot be empty");
        }

        user.setPorter(porter);
        porter.setUser(user);
        return userRepository.saveUser(user);
    }

    // To get a User by Porter
    public User getUserByPorter(Porters porter) {
        if (porter == null) {
            throw new GlobalException("Porter cannot be empty");
        }
        return userRepository.findByPorter(porter)
                .orElseThrow(() -> new GlobalException("User not found for the provided Porter"));
    }

//    // To update User
//    @Transactional
//    public User updateUser(Porters porters, User updatedUser) {
//        if (porters == null) {
//            throw new GlobalException("Porter cannot be empty");
//        }
//        if (updatedUser == null) {
//            throw new GlobalException("User cannot be empty");
//        }
//
//        User userFound = userRepository.findByPorter(porters);
//        if (userFound == null) {
//            throw new GlobalException("User not found for the provided Porter");
//        }
//        userFound.setUserName(updatedUser.getUserName());
//        userFound.setPassword(updatedUser.getPassword());
//        return userRepository.saveUser(userFound);
//    }
    // To delete a User
    @Transactional
    public void deleteUser(Porters porters) {
        if (porters == null) {
            throw new GlobalException("Porter cannot be empty");
        }

        Optional<User> userFound = userRepository.findByPorter(porters);
        userFound.ifPresent(user -> userRepository.deleteById(user.getId()));
        if (userFound.isEmpty()) {
            throw new GlobalException("User not found for the provided Porter");
        }
    }

    // To list all Users
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    // To get a User by ID
    public User getUserById(Long id) {
        if (id == null) {
            throw new GlobalException("ID cannot be empty");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new GlobalException("User with ID " + id + " not found"));
    }

    public boolean verificarUsername(String username, String password) {
        Optional<User> userOptional = userRepository.findByuserName(username);

        if (userOptional.isEmpty()) {
            return false;
        }

        if (!passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return false;
        }

        return true;
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findByuserName(name);
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.findByuserName(username).isPresent();
    }

    public User register(RegisterUser registerUser) {
        Porters porter = porterRepository.findByCedula(registerUser.getCedula());

        Porters Newporter = new Porters();
        Newporter.setName(registerUser.getName());
        Newporter.setCedula(registerUser.getCedula());
        Newporter.setTelefono(registerUser.getTelefono());
        Newporter.setEmploymentDate(registerUser.getEmploymentDate());
        porterRepository.savePorter(Newporter);

        String encryptedPassword = passwordEncoder.encode(registerUser.getPassword());

        User user = new User(registerUser.getUserName(), encryptedPassword);
        user.setPorter(Newporter);

        return userRepository.saveUser(user);
    }

}
