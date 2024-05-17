package app.manuel.infrastructure.adapter.postgres.repository.company;

import app.manuel.domain.entities.Company;
import app.manuel.domain.interfaces.ICompanyRepository;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostgresCompanyRepository implements ICompanyRepository {

    private static final String COMPANY_NOT_FOUNT = "Company not found for this id :: ";
    private final CompanyRepository companyRepository;

    @Autowired
    public PostgresCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        app.manuel.infrastructure.adapter.postgres.entities.Company companyModel = mapper(company);
        return mapper(companyRepository.save(companyModel));
    }

    @Override
    public List<Company> findAll() {
        List<app.manuel.infrastructure.adapter.postgres.entities.Company> companies = new ArrayList<>();
        companyRepository.findAll().forEach(companies::add);
        return companies.stream().map(this::mapper).toList();
    }

    @Override
    public Company findById(String nit) throws ResourceNotFoundException {
        app.manuel.infrastructure.adapter.postgres.entities.Company companyE = companyRepository.findById(nit)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUNT + nit));
        return mapper(companyE);
    }

    @Override
    public Company delete(String nit) throws ResourceNotFoundException {
        app.manuel.infrastructure.adapter.postgres.entities.Company company = companyRepository.findById(nit)
                .orElseThrow(() -> new ResourceNotFoundException(COMPANY_NOT_FOUNT + nit));
        companyRepository.delete(company);
        return mapper(company);
    }

    private app.manuel.infrastructure.adapter.postgres.entities.Company mapper(Company company) {
        return app.manuel.infrastructure.adapter.postgres.entities.Company.builder()
                .nit(company.getNit())
                .name(company.getName())
                .address(company.getAddress())
                .telephone(company.getTelephone())
                .build();
    }

    private Company mapper(app.manuel.infrastructure.adapter.postgres.entities.Company company) {
        return Company.builder()
                .nit(company.getNit())
                .name(company.getName())
                .address(company.getAddress())
                .telephone(company.getTelephone())
                .build();
    }

}
