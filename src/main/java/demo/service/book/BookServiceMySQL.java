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
    public Notification<Boolean> addBook(String title, String author, String genre, int stock, double price) {
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
    public Notification<Boolean> updateBook(Long id, String title, String author, String genre, int stock, double price) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book= null;
        if (optionalBook!= null){
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
            if (price >= 0) {
                book.setPrice(price);
            }
            book.setStock(stock);
        }
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
    public boolean deleteBook(Long id) {
        Book book = null;
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional != null) {
            book= bookOptional.get();
        }
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

    @Override
    public List<Book> searchForBooks(String title, String author, String genre) {
        if (title==null){
            title = "";
        }
        if (author==null){
            author = "";
        }
        if (genre==null){
            genre = "";
        }
        return bookRepository.findAllByTitleOrAuthorOrGenre(title,author,genre);

    }
}
