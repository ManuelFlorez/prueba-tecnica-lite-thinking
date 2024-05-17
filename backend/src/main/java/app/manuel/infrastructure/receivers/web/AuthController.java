package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.interfaces.AuthService;
import app.manuel.infrastructure.receivers.web.dto.AuthResponse;
import app.manuel.infrastructure.receivers.web.payload.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                AuthResponse.builder().token(
                        authService.login(request.getEmail(), request.getPassword()))
                        .build());
    }
}
