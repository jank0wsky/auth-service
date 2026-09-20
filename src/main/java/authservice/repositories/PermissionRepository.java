package authservice.repositories;

import authservice.entities.permissions.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);
}
