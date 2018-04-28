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
import java.util.Optional;

@Service
public class BookServiceMySQL  implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Notification<Boolean> addBook(String title, String author, String genre, Integer stock, Double price) {
        Book book = new BookBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .setStock(stock)
                .setPrice(price)
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
    public Notification<Boolean> updateBook(Long id, String title, String author, String genre, Integer stock, Double price) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = validateOptionalBook(title, author, genre, stock, price, optionalBook);
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

    private Book validateOptionalBook(String title, String author, String genre, Integer stock, Double price, Optional<Book> optionalBook) {
        Book book= null;
        if (optionalBook!= null && optionalBook.isPresent()){
            book = optionalBook.get();
        }
        if (book!=null) {
            if (title != null && title.length() > 0) {
                book.setTitle(title);
            }
            if (author != null && author.length() > 0) {
                book.setAuthor(author);
            }
            if (genre != null && genre.length() > 0) {
                book.setGenre(genre);
            }
            if (price != null) {
                book.setPrice(price);
            }
            if (stock != null) {
                book.setStock(stock);
            }
        }
        return book;
    }

    @Override
    public boolean deleteBook(Long id) {
        Book book = null;
        if (id != null) {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (bookOptional != null && bookOptional.isPresent()) {
                book = bookOptional.get();
            }
            if (book != null) {
                bookRepository.delete(book);
                return true;
            }
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

    @Override
    public List<Book> searchForBooks(String title) {
        if (title==null){
            title = "";
        }
        return bookRepository.findAllByQuery(title);

    }
}
