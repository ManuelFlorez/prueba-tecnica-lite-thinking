package app.manuel.application;

import app.manuel.domain.interfaces.GeneratePDF;
import app.manuel.domain.interfaces.ICompanyRepository;
import app.manuel.domain.interfaces.IProductRepository;
import app.manuel.domain.interfaces.ITraceability;
import app.manuel.domain.usecase.Auth;
import app.manuel.domain.usecase.DownloadPDF;
import app.manuel.domain.usecase.companies.CompanyController;
import app.manuel.domain.usecase.products.ProductController;
import app.manuel.infrastructure.adapter.jwt.JwtService;
import app.manuel.infrastructure.adapter.logger.TraceabilityImp;
import app.manuel.infrastructure.adapter.postgres.entities.CustomUserDetail;
import app.manuel.infrastructure.adapter.postgres.entities.User;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import app.manuel.infrastructure.adapter.postgres.repository.company.CompanyRepository;
import app.manuel.infrastructure.adapter.postgres.repository.company.CompanyRepositoryImpl;
import app.manuel.infrastructure.adapter.postgres.repository.product.ProductRepository;
import app.manuel.infrastructure.adapter.postgres.repository.product.ProductRepositoryImp;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService() {
        return username -> {
            User user = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetail(user);
        };
    }

    @Bean
    public Auth getAuth(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager) {
        return new Auth(userRepository, jwtService, passwordEncoder, authenticationManager);
    }

    @Bean
    @Primary
    public ITraceability getITraceability() {
        return new TraceabilityImp();
    }

    @Bean
    @Primary
    public ICompanyRepository getICompanyRepository(@Lazy CompanyRepository companyRepository) {
        return new CompanyRepositoryImpl(companyRepository);
    }

    @Bean
    @Primary
    public IProductRepository getIProductRepository(ProductRepository productRepository) {
        return new ProductRepositoryImp((productRepository));
    }

    @Bean
    public CompanyController getCompanyController(@Lazy ICompanyRepository companyRepository, ITraceability traceability) {
        return new CompanyController(companyRepository, traceability);
    }

    @Bean
    public ProductController getProductController(@Lazy IProductRepository productRepository, ITraceability traceability) {
        return new ProductController(productRepository, traceability);
    }

    @Bean
    public DownloadPDF getDownloadPDF(IProductRepository productRepository, GeneratePDF generatePDF) {
        return new DownloadPDF(productRepository, generatePDF);
    }
}