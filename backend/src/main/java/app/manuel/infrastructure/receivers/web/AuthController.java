package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.usecase.Auth;
import app.manuel.infrastructure.adapter.postgres.entities.Role;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.receivers.web.dto.Profile;
import app.manuel.infrastructure.receivers.web.dto.UserDto;
import app.manuel.infrastructure.receivers.web.payload.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final Auth auth;

    @Autowired
    public AuthController(Auth auth) {
        this.auth = auth;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginPayload loginPayload) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(
                mapper( auth.login(loginPayload.email(), loginPayload.password()) )
        );
    }

    @CrossOrigin
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {

        boolean authorization = false;

        if(!authorization) {
            Map<String, String> result = new HashMap<>();
            result.put("message", "Invalid Authorization token");
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok( new Profile(1,
                "SA",
                "Manuel Florez",
                "jason_alexander",
                "jason@ui-lib.com",
                "/assets/images/face-6.png",
                25
        ));
    }

    private UserDto mapper(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                "",
                user.getRoles().stream().map(Role::getName).toList()
        );
    }

}
