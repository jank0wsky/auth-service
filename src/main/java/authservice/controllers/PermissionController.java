package authservice.controllers;

import authservice.configs.Constants;
import authservice.entities.permissions.PermissionRequestDto;
import authservice.entities.permissions.PermissionResponseDto;
import authservice.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.API_ROUTE + "/permissions")
public class PermissionController implements BaseControllerInterface<PermissionRequestDto, PermissionResponseDto> {
    private final PermissionService permissionService;

    @Override
    public ResponseEntity<PermissionResponseDto> get(Long id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @Override
    public ResponseEntity<Page<PermissionResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAll(pageable));
    }

    @Override
    public ResponseEntity<PermissionResponseDto> create(PermissionRequestDto permissionRequestDto) {
        return ResponseEntity.ok(permissionService.create(permissionRequestDto));
    }

    @Override
    public ResponseEntity<PermissionResponseDto> update(Long id, PermissionRequestDto permissionRequestDto) {
        return ResponseEntity.ok(permissionService.update(id, permissionRequestDto));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        permissionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        permissionService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
