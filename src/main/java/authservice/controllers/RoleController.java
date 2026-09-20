package authservice.controllers;

import authservice.configs.Constants;
import authservice.entities.roles.RoleRequestDto;
import authservice.entities.roles.RoleResponseDto;
import authservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.API_ROUTE + "/roles")
public class RoleController implements BaseControllerInterface<RoleRequestDto, RoleResponseDto>{
    private final RoleService roleService;

    @Override
    public ResponseEntity<RoleResponseDto> get(Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Override
    public ResponseEntity<Page<RoleResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAll(pageable));
    }

    @Override
    public ResponseEntity<RoleResponseDto> create(RoleRequestDto roleRequestDto) {
        return ResponseEntity.ok(roleService.create(roleRequestDto));
    }

    @Override
    public ResponseEntity<RoleResponseDto> update(Long id, RoleRequestDto roleRequestDto) {
        return ResponseEntity.ok(roleService.update(id, roleRequestDto));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        roleService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
