package app.manuel.infrastructure.receivers.web.dto;

public record ProductDto(
        long id,
        String code,
        String name,
        String feature,
        String price,
        String companyNit
) {}
