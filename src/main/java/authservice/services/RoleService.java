package authservice.services;

import authservice.entities.roles.Role;
import authservice.entities.roles.RoleMapper;
import authservice.entities.roles.RoleRequestDto;
import authservice.entities.roles.RoleResponseDto;
import authservice.repositories.RoleRepository;
import authservice.services.interfaces.BaseServiceInterface;
import authservice.utils.exceptions.EntityNotFoundException;
import authservice.utils.exceptions.EntityNotSavedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService implements BaseServiceInterface<Role, RoleRequestDto, RoleResponseDto> {
    private final RoleRepository roleRepository;

    @Override
    public Role getEntity(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public RoleResponseDto getById(Long id) {
        return RoleMapper.INSTANCE.toDto(getEntity(id));
    }

    @Override
    public Page<RoleResponseDto> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleMapper.INSTANCE::toDto);
    }

    @Override
    public RoleResponseDto create(RoleRequestDto roleRequestDto) {
        if (roleRepository.existsByName(roleRequestDto.name())){
            throw new EntityNotSavedException("Name already exists");
        }

        Role role = RoleMapper.INSTANCE.toEntity(roleRequestDto);

        return RoleMapper.INSTANCE.toDto(roleRepository.save(role));
    }

    @Override
    public RoleResponseDto update(Long id, RoleRequestDto roleRequestDto) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        roleRepository.delete(getEntity(id));
        return true;
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }
}
