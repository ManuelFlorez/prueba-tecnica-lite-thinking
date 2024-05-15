package app.manuel.application;

import app.manuel.infrastructure.adapter.postgres.entities.Role;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.RoleRepository;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class UsersConfig {

    @Bean
    public CommandLineRunner createUsers(RoleRepository roleRepository, UserRepository userRepository,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 2) {
                return;
            }
            String[][]users = {
                    {"ADMIN", "admin@app.com", "pass123"},
                    {"OUTSIDE", "externo@app.com", "pass456"}
            };
            userRepository.findAll().forEach(userRepository::delete);
            roleRepository.findAll().forEach(roleRepository::delete);
            Arrays.stream(users).forEach(u -> {
                final String role = u[0];
                final String username = u[1];
                final String password = u[2];
                User user = new User();
                user.setUserName(username);
                user.setPassword(passwordEncoder.encode(password));
                User userAux = userRepository.save(user);
                Role roleAux = new Role();
                roleAux.setName(role);
                roleAux.setUser(userAux);
                roleRepository.save(roleAux);
            });
        };
    }
}
