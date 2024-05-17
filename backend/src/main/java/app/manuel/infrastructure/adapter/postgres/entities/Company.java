package app.manuel.infrastructure.adapter.postgres.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column
    private String nit;
    @Column(name = "company_name", nullable = false)
    private String name;
    @Column(name = "company_address", nullable = false)
    private String address;
    @Column(name = "company_telephone", nullable = false)
    private String telephone;
}