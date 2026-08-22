package authservice.utils.auths.dtos;

public record SignInDto(
        String email,
        String password
) {
}
