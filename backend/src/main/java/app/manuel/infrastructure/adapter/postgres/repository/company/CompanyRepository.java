package app.manuel.infrastructure.adapter.postgres.repository.company;

import app.manuel.infrastructure.adapter.postgres.entities.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
