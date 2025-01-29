package campus.u2.entrysystem.user.infrastructure;

import campus.u2.entrysystem.Utilities.LoginUser;
import campus.u2.entrysystem.Utilities.SignInRequest;
import campus.u2.entrysystem.Utilities.security.JWTAuthtenticationConfig;
import campus.u2.entrysystem.porters.application.PortersService;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.user.application.UserService;
import campus.u2.entrysystem.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private PortersService portersService;

    @Autowired
    private UserService imp;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("userName") String username,
            @RequestParam("password") String password) {

        if (imp.verificarUsername(username, password)) {
            String token = jwtAuthtenticationConfig.getJWTToken(username);

            LoginUser user = new LoginUser(username, password, token);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

    }

   
}
