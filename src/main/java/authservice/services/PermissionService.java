package authservice.services;

import authservice.entities.permissions.Permission;
import authservice.entities.permissions.PermissionMapper;
import authservice.entities.permissions.PermissionRequestDto;
import authservice.entities.permissions.PermissionResponseDto;
import authservice.repositories.PermissionRepository;
import authservice.services.interfaces.BaseServiceInterface;
import authservice.utils.exceptions.EntityNotFoundException;
import authservice.utils.exceptions.EntityNotSavedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionService implements BaseServiceInterface<Permission, PermissionRequestDto, PermissionResponseDto> {
    private final PermissionRepository permissionRepository;

    @Override
    public Permission getEntity(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public PermissionResponseDto getById(Long id) {
        return PermissionMapper.INSTANCE.toDto(getEntity(id));
    }

    @Override
    public Page<PermissionResponseDto> getAll(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(PermissionMapper.INSTANCE::toDto);
    }

    @Override
    public PermissionResponseDto create(PermissionRequestDto permissionRequestDto) {
        if (permissionRepository.existsByName(permissionRequestDto.name())){
            throw new EntityNotSavedException("Name already exists");
        }

        Permission permission = PermissionMapper.INSTANCE.toEntity(permissionRequestDto);

        return PermissionMapper.INSTANCE.toDto(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponseDto update(Long id, PermissionRequestDto permissionRequestDto) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        permissionRepository.deleteById(id);
        return true;
    }

    @Override
    public void deleteAll() {
        permissionRepository.deleteAll();
    }
}
