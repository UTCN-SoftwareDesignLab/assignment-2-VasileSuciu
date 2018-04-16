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
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
        validateGenre(book.getGenre());
        validateStock(book.getStock());
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
        if (!Arrays.stream(Constants.Genres.GENRES).anyMatch(s->s.toLowerCase().equals(genre.toLowerCase()))){
            errors.add("Invalid genre!");
        }
    }

    private void validateStock(int stock){
        if (stock < 0) {
            errors.add("The stock cannot be negative!");
        }
    }
}
