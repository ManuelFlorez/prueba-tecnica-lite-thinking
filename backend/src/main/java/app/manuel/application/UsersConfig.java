package app.manuel.application;

import app.manuel.domain.interfaces.Encrypt;
import app.manuel.infrastructure.adapter.postgres.entities.Role;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.RoleRepository;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

@Configuration
public class UsersConfig {

    @Bean
    public CommandLineRunner createUsers(RoleRepository roleRepository,
                                         UserRepository userRepository, Encrypt encrypt) {
        return args -> {
            // https://stackoverflow.com/questions/1132567/encrypt-password-in-configuration-files

            String[][]users = {
                    {"ADMIN", "admin", "pass123"},
                    {"OUTSIDE", "externo", "pass456"}
            };
            userRepository.findAll().forEach(userRepository::delete);
            roleRepository.findAll().forEach(roleRepository::delete);

            Arrays.stream(users).forEach(u -> {

                User user = new User();
                user.setUserName(u[1]);

                try {
                    user.setPassword(encrypt.encrypt(u[2]));
                } catch (NoSuchPaddingException | BadPaddingException | InvalidKeyException |
                         IllegalBlockSizeException | UnsupportedEncodingException | InvalidParameterSpecException |
                         NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                User userAux = userRepository.save(user);

                Role roleAux = new Role();
                roleAux.setName(u[0]);
                roleAux.setUser(userAux);

                roleRepository.save(roleAux);

            });

        };
    }

}
