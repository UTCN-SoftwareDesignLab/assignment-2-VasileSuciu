package demo.model.validation;

import demo.model.Book;
import demo.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import demo.repository.book.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class SaleValidator {
    public Sale sale;
    public List<String> errors;

    @Autowired
    private BookRepository bookRepository;

    public SaleValidator(Sale sale) {
        this.sale=sale;
        errors = new ArrayList<String>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        Book book =sale.getBook();
        if (book == null){
            errors.add("Book not available!");
        }
        else {
            if (book.getStock() < sale.getQuantity()){
                errors.add("Not enough copies available!");
            }
        }
        return errors.isEmpty();
    }
}
