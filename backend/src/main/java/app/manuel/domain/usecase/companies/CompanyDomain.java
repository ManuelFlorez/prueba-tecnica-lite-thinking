package app.manuel.domain.usecase.companies;

import app.manuel.application.usecases.ICompanyDomain;
import app.manuel.domain.entities.Company;
import app.manuel.domain.interfaces.ICompanyRepository;
import app.manuel.domain.interfaces.ITraceability;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class CompanyDomain implements ICompanyDomain {

    private final ICompanyRepository companyRepository;
    private final ITraceability traceability;

    public CompanyDomain(ICompanyRepository companyRepository, ITraceability traceability) {
        this.companyRepository = companyRepository;
        this.traceability = traceability;
    }

    @Override
    public Company create(Company company) {
        Company companySave = companyRepository.save(company);
        traceabilitySuccess("create", companySave.toString());
        return companySave;
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = companyRepository.findAll();
        traceabilitySuccess("findAll", companies.stream().map(Company::toString).toList().toString());
        return companies;
    }

    @Override
    public Company findById(String nit) throws ResourceNotFoundException {
        final String method = "findById";
        try {
            Company company = companyRepository.findById(nit);
            traceabilitySuccess(method, company.toString());
            return company;
        } catch (ResourceNotFoundException e) {
            traceabilityFailed(method, nit, e.toString());
            throw e;
        }
    }

    @Override
    public Company update(Company company, String nit) throws ResourceNotFoundException {
        final String method = "update";
        company.setNit(nit);
        try {
            companyRepository.findById(nit);
            final Company companyUpdate = companyRepository.save(company);
            traceabilitySuccess(method, companyUpdate.toString());
            return companyUpdate;
        } catch (ResourceNotFoundException e) {
            traceabilityFailed(method, nit, e.toString());
            throw e;
        }
    }

    @Override
    public Company delete(String nit) throws ResourceNotFoundException {
        final String method = "delete";
        try {
            Company company = companyRepository.delete(nit);
            traceabilitySuccess(method, company.toString());
            return company;
        } catch (ResourceNotFoundException e) {
            traceabilityFailed(method, nit, e.toString());
            throw e;
        }
    }

    private void traceabilitySuccess(String method, String data) {
        traceability.success(
                getClass().getName(),
                method,
                data);
    }

    private void traceabilityFailed(String method, String data, String error){
        traceability.failed(
                getClass().getName(),
                method,
                data,
                error);
    }

}
