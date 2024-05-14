package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.interfaces.AuthService;
import app.manuel.infrastructure.receivers.web.dto.AuthResponse;
import app.manuel.infrastructure.receivers.web.payload.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                AuthResponse.builder().token(
                        authService.login(request.getEmail(), request.getPassword()))
                        .build());
    }

    /*
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
    */

}
