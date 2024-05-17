package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.entities.Company;
import app.manuel.infrastructure.receivers.web.dto.CompanyDto;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {

    private final app.manuel.domain.usecase.companies.CompanyController companyDomain;

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDto>> getCompanies() {
        return ResponseEntity.ok( companyDomain.findAll().stream().map(this::mapper).toList() );
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDto> getCompaniesById(@PathVariable(value = "id") String companyId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(mapper(companyDomain.findById(companyId)));
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CompanyDto company) {
        return ResponseEntity.ok(mapper(companyDomain.create(mapper(company))));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable(value = "id") String companyId,
                                                    @RequestBody CompanyDto companyDetail)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(mapper(companyDomain.update(mapper(companyDetail), companyId)));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCompany(@PathVariable(value = "id") String companyId)
            throws ResourceNotFoundException {
        companyDomain.delete(companyId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private CompanyDto mapper(Company company) {
        return new CompanyDto(
                company.getNit(),
                company.getName(),
                company.getAddress(),
                company.getTelephone()
        );
    }

    private Company mapper(CompanyDto company) {
        return Company.builder()
                .nit(company.nit())
                .name(company.name())
                .address(company.address())
                .telephone(company.telephone())
                .build();
    }
}