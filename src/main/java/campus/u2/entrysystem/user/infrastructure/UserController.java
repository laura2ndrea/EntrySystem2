package campus.u2.entrysystem.user.infrastructure;

import campus.u2.entrysystem.user.application.UserService;
import campus.u2.entrysystem.user.domain.User;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.porters.application.PortersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PortersService portersService;

    @Autowired
    public UserController(UserService userService, PortersService portersService) {
        this.userService = userService;
        this.portersService = portersService;
    }

    @PostMapping("/{idPorter}")
    public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable Long idPorter) {
        Porters porterToUser = portersService.getPorterById(idPorter);
        if (porterToUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPorter(porterToUser);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/porter")
    public ResponseEntity<User> getUserByPorter(@RequestBody Porters porter) {
        User user = userService.getUserByPorter(porter);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody Porters porter) {
        userService.deleteUser(porter);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
