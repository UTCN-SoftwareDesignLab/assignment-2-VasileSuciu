package demo.service.report;

import demo.model.Book;

import java.io.FileWriter;
import java.util.Date;
import java.util.List;

public class ReportGeneratorCSV implements ReportGenerator {

    private static final String HEADER = "Title,Author,Genre,Stock";
    private static final String DELIMITER = ",";
    private static final String NEW_LINE = "\n";

    @Override
    public void generateReport(List<Book> books) {
        String fileName = "CSVReport" + new Date().toString() + ".csv";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.append(HEADER + NEW_LINE);
            for (Book book : books) {
                fileWriter.append(book.getTitle());
                fileWriter.append(DELIMITER);
                fileWriter.append(book.getAuthor());
                fileWriter.append(DELIMITER);
                fileWriter.append(book.getGenre());
                fileWriter.append(DELIMITER);
                fileWriter.append(String.valueOf(book.getStock()));
                fileWriter.append(NEW_LINE);
            }
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
