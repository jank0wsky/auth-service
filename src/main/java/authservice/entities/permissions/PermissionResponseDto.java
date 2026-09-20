package authservice.entities.permissions;

public record PermissionResponseDto(
        Long id,
        String name,
        String description
) {
}
