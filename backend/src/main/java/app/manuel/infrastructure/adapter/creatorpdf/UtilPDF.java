package app.manuel.infrastructure.adapter.creatorpdf;

import app.manuel.domain.interfaces.GeneratePDF;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class UtilPDF implements GeneratePDF {

    private static final String NAME = "inventario.pdf";
    private static final String[] HEADER = { "Nombre", "Codigo", "Precio", "Empresa" };

    @Override
    public Resource createPDF(List<String[]> products) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(NAME));
        document.newPage();
        document.open();
        createTablePDF(document, products);
        document.close();
        return new InputStreamResource(new FileInputStream(NAME));
    }

    private void createTablePDF(Document document, List<String[]> products) throws DocumentException {
        PdfPTable table = new PdfPTable(HEADER.length);
        Arrays.stream(HEADER).forEach(s -> {
            PdfPCell c1 = new PdfPCell(new Phrase(s));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        });
        for (String[]product: products) {
            for (String cell: product) {
                table.addCell(cell);
            }
        }
        document.add(table);
    }
}
