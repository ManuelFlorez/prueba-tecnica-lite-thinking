package app.manuel.domain.usecase;

import app.manuel.domain.interfaces.Encrypt;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Auth {

    private final UserRepository userRepository;
    private final Encrypt encrypt;

    @Autowired
    public Auth(UserRepository userRepository, Encrypt encrypt) {
        this.userRepository = userRepository;
        this.encrypt = encrypt;
    }

    public User login(String userName, String password) throws GeneralSecurityException, IOException {
        User user = userRepository.findByUserName(userName);
        String passwordEncrypt = encrypt.decrypt(user.getPassword());
        return passwordEncrypt.equals(password) ? user : null;

    }

}

