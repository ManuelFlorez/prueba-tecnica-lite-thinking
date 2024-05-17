package app.manuel.infrastructure.receivers.web;

import app.manuel.infrastructure.adapter.postgres.entities.Role;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import app.manuel.infrastructure.receivers.web.dto.Profile;
import app.manuel.infrastructure.receivers.web.dto.UserDto;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import app.manuel.infrastructure.receivers.web.payload.ProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND = "User not found for this id :: ";

    @PostMapping(value = "profile")
    public ResponseEntity<Profile> getProfile(@RequestBody ProfileRequest request) {
        Profile profile = null;
        switch (request.getUsername()) {
            case "admin@app.com":
                profile = new Profile(
                        1,
                        "SA",
                        "Manuel Florez",
                        "jason_alexander",
                        "admin@app.com",
                        "/assets/images/face-6.png",
                        25
                );
                break;
            case "externo@app.com":
                new Profile(
                        1,
                        "SA",
                        "Manuel Florez",
                        "jason_alexander",
                        "externo@app.com",
                        "/assets/images/face-6.png",
                        25
                );
                break;
            default:
                return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users.stream().map(user ->
                new UserDto(
                        user.getId(),
                        user.getUserName(),
                        user.getPassword(),
                        user.getRoles().stream().map(Role::getName).toList()
        )).toList();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetail) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        user.setUserName(userDetail.getUserName());
        user.setPassword(userDetail.getPassword());

        final User updateUser = userRepository.save(user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }

}
