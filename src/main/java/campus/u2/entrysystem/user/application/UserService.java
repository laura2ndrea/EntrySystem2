package campus.u2.entrysystem.user.application;

import campus.u2.entrysystem.Utilities.RegisterUser;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.Utilities.exceptions.UniqueViolationException;
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
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }
        return userRepository.saveUser(user);
    }

    // To create a User for a Porter
    @Transactional
    public User createUser(Porters porter, User user) {
        if (porter == null) {
            throw new InvalidInputException("Porter cannot be null");
        }
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }
        Optional<Porters> existingPorter = porterRepository.getPorterById(porter.getId());
        if (existingPorter.isEmpty()) {
            throw new NotFoundException("Porter with ID " + porter.getId() + " not found in registered porters.");
        }
        Optional<User> existingUser = userRepository.findByPorter(porter);
        if (existingUser.isPresent()) {
            throw new UniqueViolationException("This porter already has a user account.");
        }
        user.setPorter(porter);
        porter.setUser(user);
        return userRepository.saveUser(user);
    }

    // To get a User by Porter
    public User getUserByPorter(Porters porter) {
        if (porter == null) {
            throw new InvalidInputException("Porter cannot be null");
        }
        return userRepository.findByPorter(porter)
                .orElseThrow(() -> new NotFoundException("User not found for the provided Porter"));
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
            throw new InvalidInputException("Porter cannot be null");
        }
        Optional<User> userFound = userRepository.findByPorter(porters);
        if (userFound.isEmpty()) {
            throw new NotFoundException("User not found for the provided Porter");
        }
        userRepository.deleteById(userFound.get().getId());
    }

    // To list all Users
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    // To get a User by ID
     public User getUserById(String idUser) {
        if (idUser == null || idUser.isBlank()) {
            throw new InvalidInputException("User ID cannot be null or empty.");
        }
        try {
            Long id = Long.parseLong(idUser);
            return userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found."));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("idUser", "Long", "Invalid format: " + idUser);
        }
    }

    public boolean verificarUsername(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new InvalidInputException("Password cannot be empty");
        }
        Optional<User> userOptional = userRepository.findByuserName(username);

        if (userOptional.isEmpty()) {
            return false;
        }
        if (!passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return false;
        }
        return true;
    }

    public User getUserByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        return userRepository.findByuserName(name)
                .orElseThrow(() -> new NotFoundException("User with name '" + name + "' not found"));
    }

    public boolean checkUsernameExists(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }

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
