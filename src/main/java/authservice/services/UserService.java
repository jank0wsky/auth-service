package authservice.services;

import authservice.services.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import authservice.entities.User;
import authservice.entities.UserMapper;
import authservice.entities.UserRequestDto;
import authservice.entities.UserResponseDto;
import authservice.repositories.UserRepository;
import authservice.utils.exceptions.EntityNotFoundException;
import authservice.utils.exceptions.EntityNotSavedException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public UserResponseDto getById(Long id) {
        return UserMapper.INSTANCE.toDto(getEntity(id));
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        return UserMapper.INSTANCE.toDto(userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("email", email)));
    }

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper.INSTANCE::toDto);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EntityNotSavedException("Email already exists");
        }

        User created = UserMapper.INSTANCE.toEntity(dto);
        created.setPassword(passwordEncoder.encode(dto.password()));

        return UserMapper.INSTANCE.toDto(userRepository.save(created));
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        //we need to track which value has changed
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        userRepository.delete(getEntity(id));
        return true;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
