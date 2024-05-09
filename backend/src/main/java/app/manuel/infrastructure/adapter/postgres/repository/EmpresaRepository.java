package app.manuel.infrastructure.adapter.postgres.repository;

import app.manuel.infrastructure.adapter.postgres.entities.Empresa;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {

}
