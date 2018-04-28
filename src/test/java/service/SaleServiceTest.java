package service;

import demo.model.Book;
import demo.model.builder.BookBuilder;
import demo.repository.book.BookRepository;
import demo.repository.sale.SaleRepository;
import demo.service.sale.SaleServiceMySQL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.constraints.AssertFalse;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    SaleRepository saleRepository;

    @InjectMocks
    SaleServiceMySQL saleServiceMySQL;


    @Test
    public void makeSaleTest(){
        Book book = new BookBuilder().setId(1L).setStock(10).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Assert.assertFalse(saleServiceMySQL.makeSale(1L,5).hasErrors());
    }
}
