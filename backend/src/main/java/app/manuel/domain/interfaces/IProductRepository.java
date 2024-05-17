package app.manuel.domain.interfaces;

import app.manuel.domain.entities.Product;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;

import java.util.List;

public interface IProductRepository {
    Product save(Product product);

    List<Product> findAll();

    Product findById(long id) throws ResourceNotFoundException;

    Product delete(long id) throws ResourceNotFoundException;
}
