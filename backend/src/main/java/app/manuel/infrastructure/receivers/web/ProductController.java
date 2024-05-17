package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.entities.Product;
import app.manuel.infrastructure.receivers.web.dto.ProductDto;
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
public class ProductController {

    private final app.manuel.domain.usecase.products.ProductController productDomain;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok( productDomain.findAll().stream().map(this::mapper).toList() );
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(value = "id") long productId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(mapper(productDomain.findById(productId)));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(mapper(productDomain.create(mapper(productDto))));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(value = "id") long productId,
                                                     @Valid @RequestBody ProductDto product)
            throws  ResourceNotFoundException{
        return ResponseEntity.ok(mapper(productDomain.update(mapper(product))));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable(value = "id") long productId)
            throws ResourceNotFoundException {
        productDomain.delete(productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private ProductDto mapper(Product product) {
        return new ProductDto(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getFeatures(),
                product.getPrice(),
                product.getCompany().getName()
        );
    }

    private Product mapper(ProductDto product) {
        return app.manuel.domain.entities.Product.builder()
                .id(product.id())
                .code(product.code())
                .name(product.name())
                .features(product.feature())
                .price(product.price())
                .company(app.manuel.domain.entities.Company.builder()
                        .nit(product.companyNit())
                        .build())
                .build();
    }
}
