package demo.service.report;

import demo.model.Book;

import java.util.List;

public interface ReportGenerator {

    void generateReport(List<Book> books);

}
