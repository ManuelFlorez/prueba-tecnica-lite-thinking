package app.manuel.domain.usecase;

import app.manuel.domain.interfaces.GeneratePDF;
import app.manuel.infrastructure.adapter.postgres.entities.Producto;
import app.manuel.infrastructure.adapter.postgres.repository.ProductoRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DownloadPDF {

    private ProductoRepository productoRepository;
    private GeneratePDF generatePDF;

    public DownloadPDF(ProductoRepository productoRepository, GeneratePDF generatePDF) {
        this.productoRepository = productoRepository;
        this.generatePDF = generatePDF;
    }

    public Resource init() throws DocumentException, FileNotFoundException {
        List<Producto> products = new ArrayList<>();
        this.productoRepository.findAll().forEach(products::add);
        return generatePDF.createPDF(
                products.stream().map(producto ->
                        new String[] {
                                producto.getNombre(),
                                producto.getCodigo(),
                                producto.getPrecio(),
                                producto.getEmpresa().getNombre()
                        }
                ).toList()
        );
    }

}
