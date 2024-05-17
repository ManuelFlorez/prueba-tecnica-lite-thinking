package app.manuel.domain.interfaces;

public interface ITraceability {
    void success(String useCase, String method, String data);
    void failed(String useCase, String method, String data, String error);
}
