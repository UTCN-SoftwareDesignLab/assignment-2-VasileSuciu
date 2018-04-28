package demo.service.report;

import demo.model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

import java.util.Date;
import java.util.List;
import static java.nio.charset.StandardCharsets.*;

public class ReportGeneratorPDF implements ReportGenerator{

    private static final String PDF_TITLE = "Books running out of stock";
    private static final int ROWS_PER_PAGE = 20;

    @Override
    public void generateReport(List<Book> books) {
        String fileName ="PDFreport.pdf";
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25,725);
            int currentRow = 0;
            contentStream.showText(PDF_TITLE);
            contentStream.newLine();
            contentStream.newLine();
            currentRow = currentRow + 2;
            int currentBook = 0;
            while (currentBook<books.size()) {
                while (currentRow < ROWS_PER_PAGE && currentBook < books.size()) {
                    Book book = books.get(currentBook);
                    String text = book.getId() + ", " +
                            book.getTitle() + ", " +
                            book.getAuthor() + ", " +
                            book.getGenre() + ", " +
                            book.getPrice() + ", " +
                            book.getStock();
                    byte[] ptext = text.getBytes(ISO_8859_1);
                    String value = new String(ptext, "Windows-1252");
                    contentStream.showText(value);
                    contentStream.newLine();
                    currentBook++;
                    currentRow++;
                }
                contentStream.endText();
                contentStream.close();
                document.addPage(page);
                if (currentBook < books.size()) {
                    page = new PDPage();
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25,725);
                }
                currentRow = 0;
            }
            if (document.getNumberOfPages() == 0){
                document.addPage(page);
            }
            document.save(fileName);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
