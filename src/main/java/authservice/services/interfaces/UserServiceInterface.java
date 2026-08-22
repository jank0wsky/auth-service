package authservice.services.interfaces;

import authservice.entities.User;
import authservice.entities.UserRequestDto;
import authservice.entities.UserResponseDto;

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
