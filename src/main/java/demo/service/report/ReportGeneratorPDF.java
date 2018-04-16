package demo.service.report;

import demo.model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.util.Date;
import java.util.List;

public class ReportGeneratorPDF implements ReportGenerator{

    private static final String PDF_TITLE = "Books running out of stock";

    @Override
    public void generateReport(List<Book> books) {
        String fileName = "PDFReport-"+new Date().toString()+ ".pdf";
        try {
            PDDocument document = new PDDocument();
            document.save("fileName");
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            document.addPage(page);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
