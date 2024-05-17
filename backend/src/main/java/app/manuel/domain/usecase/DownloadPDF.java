package app.manuel.domain.usecase;

import app.manuel.domain.entities.Product;
import app.manuel.domain.interfaces.GeneratePDF;
import app.manuel.domain.interfaces.IProductRepository;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadPDF {

    private final IProductRepository productRepository;
    private final GeneratePDF generatePDF;

    public Resource init() throws DocumentException, FileNotFoundException {
        List<Product> products = new ArrayList<>(this.productRepository.findAll());
        return generatePDF.createPDF(
                products.stream().map(product ->
                        new String[] {
                                product.getName(),
                                product.getCode(),
                                product.getPrice(),
                                product.getCompany().getName()
                        }
                ).toList()
        );
    }
}
