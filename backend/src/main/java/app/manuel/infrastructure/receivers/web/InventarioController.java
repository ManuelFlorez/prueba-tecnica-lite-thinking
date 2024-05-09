package app.manuel.infrastructure.receivers.web;

import app.manuel.domain.usecase.DownloadPDF;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class InventarioController {

    private DownloadPDF downloadPDF;

    @Autowired
    public  InventarioController(DownloadPDF downloadPDF) {
        this.downloadPDF = downloadPDF;
    }

    @CrossOrigin
    @GetMapping("/create-pdf")
    private ResponseEntity<Resource> downloadPDF() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=PDF.pdf");
            return ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(downloadPDF.init());
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException("Download PDF error");
        }
    }

}
