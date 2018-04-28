package service;

import demo.model.Book;
import demo.model.builder.BookBuilder;
import demo.repository.book.BookRepository;
import demo.service.book.BookServiceMySQL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceMySQL bookService;

    Book book;

    @Before
    public void init() {
        //MockitoAnnotations.initMocks(this);
        this.book = new BookBuilder()
                .setTitle("Title")
                .setAuthor("author")
                .setGenre("science")
                .setPrice(10.0)
                .setStock(1)
                .build();
    }

    @Test
    public void addBookTest(){
        Assert.assertFalse(bookService.addBook(book.getTitle(),book.getAuthor(),
                book.getGenre(),book.getStock(), book.getPrice()).hasErrors());
    }

    @Test
    public void updateBookTest(){
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Assert.assertFalse(bookService.updateBook(book.getId(),book.getTitle(), book.getAuthor(),
                book.getGenre(), book.getStock(), book.getPrice()).hasErrors());
    }

    @Test
    public void deleteBookTest(){
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Assert.assertTrue(bookService.deleteBook(book.getId()));
    }

    @Test
    public void getAllBooksTest(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<Book>());
        Assert.assertEquals(bookService.getAllBooks().size(),0);
    }

    @Test
    public void getBooksWithStockLessThan(){
        List<Book> bookList = new ArrayList<Book>();
        Book book2= new BookBuilder()
                .setTitle("Book2")
                .setStock(2)
                .build();
        bookList.add(book2);
        bookList.add(book);
        when(bookRepository.findAllByStock(1)).thenReturn(bookList);
        System.out.println(bookList.size());
        Assert.assertEquals(bookService.getBooksWithStockLessThan(2).size(),2);
    }

    @Test
    public void getBookByTitleTest(){
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(book);
        Assert.assertEquals("Title", bookService.getBookByTitle(book.getTitle()).getTitle());
    }

    @Test
    public void searchForBooksTest(){
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(book);
        Assert.assertEquals("Title", bookService.getBookByTitle(book.getTitle()).getTitle());
    }
}
