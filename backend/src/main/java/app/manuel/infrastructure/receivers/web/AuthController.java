package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.usecase.Auth;
import app.manuel.infrastructure.adapter.postgres.entities.Role;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.receivers.web.dto.UserDto;
import app.manuel.infrastructure.receivers.web.payload.LoginPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
                mapper( auth.login(loginPayload.userName(), loginPayload.password()) )
        );
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
