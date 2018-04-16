package demo.service.book;

import demo.model.Book;
import demo.model.builder.BookBuilder;
import demo.model.validation.BookValidator;
import demo.model.validation.Notification;
import demo.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceMySQL  implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Notification<Boolean> addBook(String title, String author, String genre, int stock) {
        Book book = new BookBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .setStock(stock)
                .build();
        BookValidator bookValidator = new BookValidator(book);
        boolean bookValid = bookValidator.validate();
        Notification<Boolean> notification = new Notification<Boolean>();
        if (!bookValid){
            bookValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        else {
            bookRepository.save(book);
            notification.setResult(Boolean.TRUE);
        }
        return notification;
    }

    @Override
    public Notification<Boolean> updateBook(String title, String author, String genre, int stock) {
        Book book = bookRepository.findByTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setStock(stock);
        BookValidator bookValidator = new BookValidator(book);
        boolean bookValid = bookValidator.validate();
        Notification<Boolean> notification = new Notification<Boolean>();
        if (!bookValid){
            bookValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        else {
            bookRepository.save(book);
            notification.setResult(Boolean.TRUE);
        }
        return notification;
    }

    @Override
    public boolean deleteBook(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            bookRepository.delete(book);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBooksWithStockLessThan(int stock) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i<stock; i++){
            books.addAll(bookRepository.findAllByStock(i));
        }
        return books;
    }
}
