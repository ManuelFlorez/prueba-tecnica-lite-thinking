package app.manuel.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Company {
    private String nit;
    private String name;
    private String address;
    private String telephone;
}
