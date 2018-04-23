package demo.model.validation;

import demo.database.Constants;
import demo.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookValidator {
    private final Book book;

    public List<String> errors;

    public BookValidator(Book book){
        this.book = book;
        errors = new ArrayList<String>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        if (book!=null) {
            validateTitle(book.getTitle());
            validateAuthor(book.getAuthor());
            validateGenre(book.getGenre());
            validateStock(book.getStock());
            validatePrice(book.getPrice());
        }
        else{
            errors.add("No such book!");
        }
        return  errors.isEmpty();
    }

    private void validateTitle(String title){
        if (title == null || title.trim().length()<1){
            errors.add("Title cannot be empty!");
        }

    }

    private void validateAuthor(String author){
        if (author == null || author.trim().length()<1){
            errors.add("Author cannot be empty!");
        }
    }

    private void validateGenre(String genre){
        if (genre==null || !Arrays.stream(Constants.Genres.GENRES).anyMatch(s->s.toLowerCase().equals(genre.toLowerCase()))){
            errors.add("Invalid genre!");
        }
    }

    private void validateStock(Integer stock){
        if (stock == null){
            errors.add("Stock cannot be null");
        }
        else {
            if (stock < 0) {
                errors.add("The stock cannot be negative!");
            }
        }
    }

    private void validatePrice(Double price){
        if (price == null){
            errors.add("Price cannot be null");
        }
        else {
            if (price < 0) {
                errors.add("The price cannot be negative!");
            }
        }
    }
}
