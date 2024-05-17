package app.manuel.infrastructure.receivers.web.dto;

public record CompanyDto(
        String nit,
        String name,
        String address,
        String telephone
) {}
