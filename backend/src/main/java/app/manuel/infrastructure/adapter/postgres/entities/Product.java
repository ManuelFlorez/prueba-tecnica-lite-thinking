package app.manuel.infrastructure.adapter.postgres.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "product_code", nullable = false)
    private String code;
    @Column(name = "product_name", nullable = false)
    private String name;
    @Column(name = "product_features", nullable = false)
    private String features;
    @Column(name = "product_price", nullable = false)
    private String price;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "companies_nit", nullable = false)
    private Company company;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", features='" + features + '\'' +
                ", price='" + price + '\'' +
                ", company=" + company +
                '}';
    }
}