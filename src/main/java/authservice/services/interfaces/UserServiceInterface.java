package authservice.services.interfaces;

import authservice.entities.users.User;
import authservice.entities.users.UserRequestDto;
import authservice.entities.users.UserResponseDto;

/**
 * Methods specific for UserService should be named here
 * Used to separate REAL and MOCK services
 */
public interface UserServiceInterface extends BaseServiceInterface<User, UserRequestDto, UserResponseDto> {

    /**
     * Get a user from database via email
     * @param email email of user
     * @return user as UserResponseDto
     */
    UserResponseDto getByEmail(String email);
}
