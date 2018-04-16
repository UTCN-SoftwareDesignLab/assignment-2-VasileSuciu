package demo.model.builder;

import demo.model.Book;
import demo.model.Sale;

import java.sql.Date;

public class SaleBuilder {
    private Sale sale;

    public  SaleBuilder(){
        this.sale = new Sale();
    }

    public SaleBuilder setId(Long id){
        sale.setId(id);
        return  this;
    }

    public SaleBuilder setBook(Book book){
        sale.setBook(book);
        return this;
    }

    public SaleBuilder setDate(Date date){
        sale.setDate(date);
        return  this;
    }

    public SaleBuilder setQuantity(int quantity){
        sale.setQuantity(quantity);
        return this;
    }

    public Sale build(){
        return  sale;
    }
}
