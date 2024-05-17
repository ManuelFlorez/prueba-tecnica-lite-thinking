package app.manuel.application.usecases;

import app.manuel.domain.entities.Company;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;

import java.util.List;

public interface ICompanyDomain {
    Company create(Company company);
    List<Company> findAll();
    Company findById(String nit) throws ResourceNotFoundException;
    Company update(Company company, String nit) throws ResourceNotFoundException;
    Company delete(String nit) throws ResourceNotFoundException;
}
