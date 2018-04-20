package demo.service.sale;

import demo.model.Book;
import demo.model.Sale;
import demo.model.builder.SaleBuilder;
import demo.model.validation.Notification;
import demo.model.validation.SaleValidator;
import demo.repository.book.BookRepository;
import demo.repository.sale.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class SaleServiceMySQL implements SaleService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Notification<Boolean> makeSale(Long id, int quantity) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = null;
        if (bookOptional!=null){
            book = bookOptional.get();
        }
        Sale sale= new SaleBuilder()
                .setBook(book)
                .setQuantity(quantity)
                .setDate(new Date(System.currentTimeMillis()))
                .build();
        SaleValidator saleValidator = new SaleValidator(sale);
        boolean saleValid = saleValidator.validate();
        Notification<Boolean> notification = new Notification<Boolean>();
        if (saleValid){
            saleRepository.save(sale);
            book.setStock(book.getStock()-quantity);
            bookRepository.save(book);
            notification.setResult(Boolean.TRUE);
        }
        else {
            saleValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }
        return notification;
    }
}
