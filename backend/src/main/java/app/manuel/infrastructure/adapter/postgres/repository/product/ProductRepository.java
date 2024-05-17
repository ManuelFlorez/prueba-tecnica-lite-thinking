package app.manuel.infrastructure.adapter.postgres.repository.product;

import app.manuel.infrastructure.adapter.postgres.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {

}