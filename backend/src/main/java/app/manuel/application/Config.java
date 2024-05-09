package app.manuel.application;

import app.manuel.domain.interfaces.Encrypt;
import app.manuel.domain.interfaces.GeneratePDF;
import app.manuel.domain.usecase.Auth;
import app.manuel.domain.usecase.DownloadPDF;
import app.manuel.infrastructure.adapter.creatorpdf.UtilPDF;
import app.manuel.infrastructure.adapter.encrypt.UtilEncrypt;
import app.manuel.infrastructure.adapter.postgres.repository.ProductoRepository;
import app.manuel.infrastructure.adapter.postgres.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {

    @Bean
    public Encrypt getUtilEncrypt() {
        return new UtilEncrypt();
    }

    @Bean
    public Auth getAuth(UserRepository userRepository, Encrypt encrypt) {
        return new Auth(userRepository, encrypt);
    }

    @Bean
    public DownloadPDF getDownloadPDF(ProductoRepository productoRepository, GeneratePDF generatePDF) {
        return new DownloadPDF(productoRepository, generatePDF);
    }

    @Primary
    @Bean
    public GeneratePDF getGeneratePDF() {
        return new UtilPDF();
    }
}
