package app.manuel.domain.usecase;

import app.manuel.domain.entities.Product;
import app.manuel.domain.interfaces.GeneratePDF;
import app.manuel.domain.interfaces.IProductRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadPDF {

    private final IProductRepository productRepository;
    private final GeneratePDF generatePDF;

    @Autowired
    public DownloadPDF(IProductRepository productRepository, GeneratePDF generatePDF) {
        this.productRepository = productRepository;
        this.generatePDF = generatePDF;
    }

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
