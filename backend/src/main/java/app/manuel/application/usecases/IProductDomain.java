package app.manuel.application.usecases;

import app.manuel.domain.entities.Product;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;

import java.util.List;

public interface IProductDomain {
    Product create(Product product);
    List<Product> findAll();
    Product findById(long id) throws ResourceNotFoundException;
    Product update(Product product) throws ResourceNotFoundException;
    Product delete(long id) throws ResourceNotFoundException;
}
