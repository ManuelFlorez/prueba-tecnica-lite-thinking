package app.manuel.domain.usecase;

import app.manuel.domain.interfaces.AuthService;
import app.manuel.infrastructure.adapter.jwt.JwtService;
import app.manuel.infrastructure.adapter.postgres.entities.CustomUserDetail;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Auth implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public Auth(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
                AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUserName(username).orElseThrow();
        UserDetails userDetails = new CustomUserDetail(user);
        return jwtService.getToken(userDetails);
    }

}

