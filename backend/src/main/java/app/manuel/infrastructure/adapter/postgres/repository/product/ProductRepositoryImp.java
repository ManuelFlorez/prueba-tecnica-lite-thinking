package app.manuel.infrastructure.adapter.postgres.repository.product;

import app.manuel.domain.entities.Company;
import app.manuel.domain.entities.Product;
import app.manuel.domain.interfaces.IProductRepository;
import app.manuel.infrastructure.receivers.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRepositoryImp implements IProductRepository {

    private static final String PRODUCT_NOT_FOUNT = "Product not found for this id :: ";
    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        app.manuel.infrastructure.adapter.postgres.entities.Product productModel = mapper(product);
        return mapper(productRepository.save(productModel));
    }

    @Override
    public List<Product> findAll() {
        List<app.manuel.infrastructure.adapter.postgres.entities.Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products.stream().map(this::mapper).toList();
    }

    @Override
    public Product findById(long id) throws ResourceNotFoundException {
        app.manuel.infrastructure.adapter.postgres.entities.Product productModel = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUNT + id));
        return mapper(productModel);
    }

    @Override
    public Product delete(long id) throws ResourceNotFoundException {
        app.manuel.infrastructure.adapter.postgres.entities.Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUNT + id));
        productRepository.delete(product);
        return mapper(product);
    }

    private Product mapper(app.manuel.infrastructure.adapter.postgres.entities.Product product) {
        return Product.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .features(product.getFeatures())
                .price(product.getPrice())
                .company(mapper(product.getCompany()))
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


    private app.manuel.infrastructure.adapter.postgres.entities.Product mapper(Product product) {
        return app.manuel.infrastructure.adapter.postgres.entities.Product.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .features(product.getFeatures())
                .company(mapper(product.getCompany()))
                .build();
    }

    private app.manuel.infrastructure.adapter.postgres.entities.Company mapper(Company company) {
        return app.manuel.infrastructure.adapter.postgres.entities.Company.builder()
                .nit(company.getNit())
                .name(company.getName())
                .address(company.getAddress())
                .telephone(company.getTelephone())
                .build();
    }
}
