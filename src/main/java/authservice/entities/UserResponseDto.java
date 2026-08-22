package authservice.entities;


public record UserResponseDto(
        Long id,
        String email,
        String password
) {
}
