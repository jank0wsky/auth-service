package authservice.entities.users;

public record UserRequestDto(
        String email,
        String password
) {
}
