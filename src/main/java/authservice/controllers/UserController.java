package authservice.controllers;

import authservice.configs.Constants;
import authservice.entities.users.UserRequestDto;
import authservice.entities.users.UserResponseDto;
import authservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.API_ROUTE + "/users")
public class UserController implements BaseControllerInterface<UserRequestDto, UserResponseDto> {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> get(Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @Override
    public ResponseEntity<UserResponseDto> create(UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.create(userRequestDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> update(Long id, UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.update(id, userRequestDto));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
