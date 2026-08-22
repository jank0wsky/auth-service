package authservice.entities;

public record UserRequestDto(
        String email,
        String password
) {
}
