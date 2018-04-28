package demo.service.book;

import demo.model.Book;
import demo.model.validation.Notification;

import java.util.List;

public interface BookService {

    Notification<Boolean> addBook(String title, String author, String genre, Integer stock, Double price);

    Notification<Boolean> updateBook(Long id, String title, String author, String genre, Integer stock, Double price);

    boolean deleteBook(Long id);

    List<Book> getAllBooks();

    Book getBookByTitle(String title);

    List<Book> getBooksWithStockLessThan(int stock);

    List<Book> searchForBooks(String title);
}
