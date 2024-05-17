package app.manuel.infrastructure.adapter.logger;

import app.manuel.domain.interfaces.ITraceability;
import org.springframework.stereotype.Service;

@Service
public class TraceabilityImp implements ITraceability {
    @Override
    public void success(String useCase, String method, String data) {
        System.out.println(useCase + " - " + method + ": " + data);
    }

    @Override
    public void failed(String useCase, String method, String data, String error) {
        System.out.println(useCase + " - " + method + ": " + data + " error: " + error);
    }
}
