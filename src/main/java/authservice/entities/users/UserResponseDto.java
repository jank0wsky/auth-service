package authservice.entities.users;

import authservice.entities.permissions.Permission;
import authservice.entities.roles.Role;

import java.util.Set;

public record UserResponseDto(
        Long id,
        String email,
        String password,
        Role role,
        Set<Permission> permissionSet
) {
}
