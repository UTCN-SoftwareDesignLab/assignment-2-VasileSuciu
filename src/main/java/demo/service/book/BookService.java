package demo.service.book;

import demo.model.Book;
import demo.model.validation.Notification;

import java.util.List;

public interface BookService {

    Notification<Boolean> addBook(String title, String author, String genre, int stock);

    Notification<Boolean> updateBook(String title, String author, String genre, int stock);

    boolean deleteBook(String title);

    List<Book> getAllBooks();

    Book getBookByTitle(String title);

    List<Book> getBooksWithStockLessThan(int stock);
}
