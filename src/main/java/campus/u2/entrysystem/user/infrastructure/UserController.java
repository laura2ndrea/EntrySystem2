package campus.u2.entrysystem.user.infrastructure;

import campus.u2.entrysystem.user.application.UserService;
import campus.u2.entrysystem.user.domain.User;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

 

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

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
