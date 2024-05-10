package app.manuel.infrastructure.receivers.web.dto;

public record Profile(
        long id,
        String role,
        String name,
        String username,
        String email,
        String avatar,
        long age
) { }
