package app.manuel.domain.interfaces;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.util.List;

public interface GeneratePDF {
    Resource createPDF(List<String[]> products) throws FileNotFoundException, DocumentException;
}
