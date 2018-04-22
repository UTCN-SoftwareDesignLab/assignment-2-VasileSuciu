package demo.service.report;

import demo.database.Constants;
import demo.model.Book;
import demo.service.book.BookServiceMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportGeneratorService{
    private ReportGenerator reportGenerator;
    @Autowired
    private BookServiceMySQL bookServiceMySQL;

    public ReportGeneratorService(){

    }

    public void setReportGenerator(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
        System.out.println("hello from report generator set generator");
    }

    public void generateReport() {

        System.out.println("hello from report generator report");
        if (this.reportGenerator != null) {
            System.out.println("hello from report generator service");
            List<Book> books = bookServiceMySQL.getBooksWithStockLessThan(Constants.LOW_STOCK);
            reportGenerator.generateReport(books);
        }
    }
}
