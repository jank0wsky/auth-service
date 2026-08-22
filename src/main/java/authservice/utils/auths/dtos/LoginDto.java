package authservice.utils.auths.dtos;

public record LoginDto(
        String email,
        String password
) {
}
