package app.manuel.infrastructure.receivers.web.dto;

import java.util.List;

public record UserDto(long id, String userName, String password, List<String>roles) {
}
