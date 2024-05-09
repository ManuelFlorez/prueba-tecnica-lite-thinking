package app.manuel.infrastructure.adapter.postgres.repository;

import app.manuel.infrastructure.adapter.postgres.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
