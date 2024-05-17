package app.manuel.domain.usecase.products;

import app.manuel.application.usecases.IProductDomain;
import app.manuel.domain.entities.Product;
import app.manuel.domain.interfaces.IProductRepository;
import app.manuel.domain.interfaces.ITraceability;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class ProductDomain implements IProductDomain {

    private final IProductRepository productRepository;
    private final ITraceability traceability;

    public ProductDomain(IProductRepository productRepository, ITraceability traceability) {
        this.productRepository = productRepository;
        this.traceability = traceability;
    }

    public Product create(Product product) {
        Product productSave = productRepository.save(product);
        traceabilitySuccess("create", productSave.toString());
        return product;
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        traceabilitySuccess("findAll", products.stream().map(Product::toString).toList().toString());
        return List.of();
    }

    public Product findById(long id) throws ResourceNotFoundException {
        final String method = "findById";
        try {
            Product product = productRepository.findById(id);
            traceabilitySuccess(method, product.toString());
            return product;
        }catch (ResourceNotFoundException e) {
            traceabilityFailed(method, String.valueOf(id), e.toString());
            throw e;
        }
    }

    public Product update(Product product) throws ResourceNotFoundException {
        final String method = "update";
        try {
            productRepository.findById(product.getId());
            final Product productUpdate = productRepository.save(product);
            traceabilitySuccess(method, productUpdate.toString());
            return product;
        }catch (ResourceNotFoundException e) {
            traceabilityFailed(method, String.valueOf(product.getId()), e.toString());
            throw e;
        }
    }

    public Product delete(long id) throws ResourceNotFoundException {
        final String method = "delete";
        Product product = null;
        try {
            product = productRepository.delete(id);
            traceabilitySuccess(method, product.toString());
            return product;
        } catch (ResourceNotFoundException e) {
            traceabilityFailed(method, String.valueOf(id), e.toString());
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
