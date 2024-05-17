package app.manuel.domain.interfaces;

import app.manuel.domain.entities.Company;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;

import java.util.List;

public interface ICompanyRepository {
    Company save(Company company);

    List<Company> findAll();

    Company findById(String nit) throws ResourceNotFoundException;

    Company delete(String nit) throws ResourceNotFoundException;
}
