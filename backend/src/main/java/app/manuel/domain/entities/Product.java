package app.manuel.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Product {
    private long id;
    private String code;
    private String name;
    private String features;
    private String price;
    private Company company;
}
