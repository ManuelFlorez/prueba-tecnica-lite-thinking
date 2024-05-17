package app.manuel.infrastructure.receivers.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneratePDFException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public GeneratePDFException(String message) {
        super(message);
    }
}
