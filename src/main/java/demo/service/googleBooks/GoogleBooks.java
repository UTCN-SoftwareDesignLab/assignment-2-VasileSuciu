package demo.service.googleBooks;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import demo.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooks{
    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "MyApp-Assignment2/1.0";

    public List<Book> getBooksByTitle(String title){
        try {
            ClientCredentials.errorIfNotSpecified();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            // Set up Books client.
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                    .build();


            Books.Volumes.List volumesListByTitle = books.volumes().list("intitle:" + title);
            Volumes volumes = volumesListByTitle.execute();
            return getBooks(volumes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

    public List<Book> getBooksByAuthor(String author){
        try {
            ClientCredentials.errorIfNotSpecified();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            // Set up Books client.
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                    .build();

            Books.Volumes.List volumesListByAuthor = books.volumes().list("inauthor:" + author);
            Volumes volumes = volumesListByAuthor.execute();
            return getBooks(volumes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

    public List<Book> getBooksBySubject(String subject){
        try {
        ClientCredentials.errorIfNotSpecified();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            // Set up Books client.
            final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                    .build();

            Books.Volumes.List volumesListBySubject = books.volumes().list("subject:" + subject);

            Volumes volumes = volumesListBySubject.execute();

            return getBooks(volumes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }



    private List<Book> getBooks(Volumes volumes){
        List<Book> bookList = new ArrayList<>();
        List<Volume> volumeList = volumes.getItems();
        if (volumeList != null) {
            for (int i = 0; i < 5 && i < volumeList.size(); i++) {
                Book book = new Book();

                Volume.VolumeInfo volumeInfo = volumeList.get(i).getVolumeInfo();
                Volume.SaleInfo saleInfo = volumeList.get(i).getSaleInfo();
                if (volumeInfo.getTitle() != null) {
                    book.setTitle(volumeInfo.getTitle());
                } else {
                    book.setTitle("Unknown");
                }
                if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
                    book.setAuthor(volumeInfo.getAuthors().get(0));
                } else {
                    book.setAuthor("Unknown");
                }
                if (volumeInfo.getCategories() != null && !volumeInfo.getCategories().isEmpty()) {
                    book.setGenre(volumeInfo.getCategories().get(0));
                } else {
                    book.setGenre("Unknown");
                }
                if (saleInfo != null && saleInfo.getListPrice() !=null){
                    book.setPrice(saleInfo.getListPrice().getAmount());
                }
                else {
                    book.setPrice(0.0);
                }
                book.setStock(0);
                book.setId(i+1L);
                bookList.add(book);
            }
        }
        return bookList;
    }
}